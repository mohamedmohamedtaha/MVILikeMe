package tech.abd3lraouf.learn.mvi.like.core

import io.reactivex.rxjava3.core.Observable

interface ViewEventObservable<E> {
    fun viewEvents(): Observable<E>
}