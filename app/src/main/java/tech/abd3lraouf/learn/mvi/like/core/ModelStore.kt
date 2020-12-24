package tech.abd3lraouf.learn.mvi.like.core

import io.reactivex.rxjava3.core.Observable
import tech.abd3lraouf.learn.mvi.like.interaction.Interaction

interface ModelStore<S> {
    /**
     * Model will receive interactions to be processed via this function.
     *
     * ModelState is immutable. Processed interactions will work much like `copy()`
     * and create a new (modified) modelState from an old one.
     */
    fun process(interaction: Interaction<S>)

    /**
     * Observable stream of changes to ModelState
     *
     * Every time a modelState is replaced by a new one, this observable will
     * fire.
     *
     * This is what views will subscribe to.
     */
    fun modelState(): Observable<S>
}