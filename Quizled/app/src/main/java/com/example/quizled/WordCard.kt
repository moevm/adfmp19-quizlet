package com.example.quizled

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.word_card.view.*
import android.widget.LinearLayout


class WordCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    var wordOriginal: String
        get() {
            return originalWord.text.toString()
        }
        set(value) {
            originalWord.setText(value)
        }

    var wordTranslated: String
        get() {
            return translatedWord.text.toString()
        }
        set(value) {
            translatedWord.setText(value)
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.word_card, this, true)

        orientation = VERTICAL
    }
}

