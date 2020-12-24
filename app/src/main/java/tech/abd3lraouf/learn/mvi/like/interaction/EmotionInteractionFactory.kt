package tech.abd3lraouf.learn.mvi.like.interaction

import tech.abd3lraouf.learn.mvi.like.model.EmotionModel
import tech.abd3lraouf.learn.mvi.like.model.EmotionModelStore
import tech.abd3lraouf.learn.mvi.like.view.EmotionEvent

object EmotionInteractionFactory : InteractionFactory<EmotionEvent> {
    override fun process(viewEvent: EmotionEvent) = EmotionModelStore.process(viewEvent.asInteraction())

    private fun EmotionEvent.asInteraction(): Interaction<EmotionModel> = when (this) {
        EmotionEvent.Like -> interaction { copy(likes = likes + 1) }
        EmotionEvent.Dislike -> interaction { copy(dislikes = dislikes + 1) }
        EmotionEvent.Love -> interaction { copy(loves = loves + 1) }
        EmotionEvent.Skipped -> sideEffect { }
    }
}