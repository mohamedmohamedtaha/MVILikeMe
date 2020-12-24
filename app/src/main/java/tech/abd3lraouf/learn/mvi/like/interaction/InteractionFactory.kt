package tech.abd3lraouf.learn.mvi.like.interaction

interface InteractionFactory<E> {
    fun process(viewEvent:E)
}