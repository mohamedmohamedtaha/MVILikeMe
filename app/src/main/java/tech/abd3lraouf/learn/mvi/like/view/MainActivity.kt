package tech.abd3lraouf.learn.mvi.like.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.strikeThrough
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.plusAssign
import tech.abd3lraouf.learn.mvi.like.`fun`.SadResponse
import tech.abd3lraouf.learn.mvi.like.`fun`.SadResponse.shouldDislike
import tech.abd3lraouf.learn.mvi.like.core.ModelSubscriber
import tech.abd3lraouf.learn.mvi.like.core.ViewEventObservable
import tech.abd3lraouf.learn.mvi.like.databinding.ActivityMainBinding
import tech.abd3lraouf.learn.mvi.like.interaction.EmotionInteractionFactory
import tech.abd3lraouf.learn.mvi.like.model.EmotionModel
import tech.abd3lraouf.learn.mvi.like.model.EmotionModelStore


class MainActivity : AppCompatActivity(), ViewEventObservable<EmotionEvent>, ModelSubscriber<EmotionModel> {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mvi.text = SpannableStringBuilder()
            .bold { append("MVI\n") }
            .append("(")
            .bold { append("M") }
            .append("odel ")
            .bold { append("V") }
            .append("iew ")
            .bold { strikeThrough { append("I") } }
            .strikeThrough { append("ntent") }
            .bold { append(" I") }
            .append("nteraction)")
            .append("\n\n")
            .append("PS: there is an easter egg can you find it ?")

        binding.sayHi.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("abdelraoufsabri@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "From Do You like")
            intent.setPackage("com.google.android.gm")

            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        disposables += viewEvents().subscribe(EmotionInteractionFactory::process) // Output
        disposables += EmotionModelStore.modelState().subscribeToModel() // Input
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    override fun viewEvents(): Observable<EmotionEvent> {
        return Observable.merge(
            binding.btnDislike.clicks().map {
                Toast.makeText(baseContext, SadResponse.getSadResponse(), Toast.LENGTH_LONG).show()
                if (shouldDislike) EmotionEvent.Dislike else EmotionEvent.Skipped
            },
            binding.btnLike.clicks().map { EmotionEvent.Like },
            binding.btnLove.clicks().map { EmotionEvent.Love }
        )
    }

    override fun Observable<EmotionModel>.subscribeToModel(): Disposable {
        return subscribe {
            binding.tvDislikeCount.text = it.dislikes.toString()
            binding.tvLoveCount.text = it.loves.toString()
            binding.tvLikeCount.text = it.likes.toString()
            if (it.dislikes > 0) binding.sayHi.visibility = View.VISIBLE
        }
    }
}
