package tech.abd3lraouf.learn.mvi.like.view

sealed class EmotionEvent {
    object Love : EmotionEvent()
    object Like : EmotionEvent()
    object Dislike : EmotionEvent()
    object Skipped : EmotionEvent()
}