package tech.abd3lraouf.learn.mvi.like.interaction

interface Interaction<T> {
    fun reduce(oldState: T): T
}

/**
 * DSL function to help build interactions from code blocks.
 *
 * NOTE: Magic of extension functions, (T)->T and T.()->T interchangeable.
 */
/*
fun <T> interaction(block: T.() -> T) : Interaction<T> = object : Interaction<T> {
    override fun reduce(oldState: T): T = block(oldState)
}
*/

fun <T> interaction(block: T.() -> T) = BlockInteraction(block)

class BlockInteraction<T>(val block:T.()->T) : Interaction<T> {
    override fun reduce(oldState: T): T = block(oldState)
}

/**
 * By delegating work to other models, repositories or services, we
 * end up with situations where we don't need to update our ModelStore
 * state until the delegated work completes.
 *
 * Use the `sideEffect {}` DSL function for those situations.
 */
fun <T> sideEffect(block: T.() -> Unit) : Interaction<T> = object :
    Interaction<T> {
    override fun reduce(oldState: T): T = oldState.apply(block)
}
