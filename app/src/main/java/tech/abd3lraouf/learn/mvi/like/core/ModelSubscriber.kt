package tech.abd3lraouf.learn.mvi.like.core

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

interface ModelSubscriber<S> {
    fun Observable<S>.subscribeToModel(): Disposable
}