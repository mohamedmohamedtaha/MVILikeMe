package tech.abd3lraouf.learn.mvi.like.model

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observables.ConnectableObservable
import tech.abd3lraouf.learn.mvi.like.core.ModelStore
import tech.abd3lraouf.learn.mvi.like.interaction.Interaction
import timber.log.Timber

object EmotionModelStore : RxModelStore<EmotionModel>(EmotionModel(0, 0, 0))

abstract class RxModelStore<S>(startingState: S) : ModelStore<S> {

    private val interactions = PublishRelay.create<Interaction<S>>()

    private val store: ConnectableObservable<S> = interactions
        .observeOn(AndroidSchedulers.mainThread())
        .scan(startingState) { oldState, interaction -> interaction.reduce(oldState) }
        .replay(1)
        .apply { connect() }

    /**
     * Model will receive interactions to be processed via this function.
     *
     * ModelState is immutable. Processed interactions will work much like `copy()`
     * and create a new (modified) modelState from an old one.
     */
    override fun process(interaction: Interaction<S>) = interactions.accept(interaction)

    /**
     * Observable stream of changes to ModelState
     *
     * Every time a modelState is replaced by a new one, this observable will
     * fire.
     *
     * This is what views will subscribe to.
     */
    override fun modelState(): Observable<S> = store

    /**
     * Allows us to react to problems within the ModelStore.
     */
    private val internalDisposable = store.subscribe(::internalLogger, ::crashHandler)

    private fun internalLogger(state: S) = Timber.i("$state")

    private fun crashHandler(throwable: Throwable): Unit = throw throwable

}