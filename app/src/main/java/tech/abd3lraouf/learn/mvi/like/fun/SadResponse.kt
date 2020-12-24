package tech.abd3lraouf.learn.mvi.like.`fun`

object SadResponse {
    private var index = 0
    val shouldDislike get() = index % responses.size == 0

    private val responses = arrayOf(
        "Do you even know me ?",
        "Have you ever dealt with me?",
        "So you haven't!!",
        "Do you think android development is EASY?",
        "Do you know, how many times I got sick? because of sitting too long",
        "How many nights I stayed up at?",
        "Those were cold! and STILL are",
        "It wasn't easy, you know!",
        "I promise you I'll like you if you just told me",
        "But I'll be kind and let you dislike me"
    )

    fun getSadResponse() = responses[index++ % responses.size]
}