package com.example.quizled

import com.example.quizled.R
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.example.quizled.MainActivity
import com.example.quizled.WordCard
import kotlinx.android.synthetic.main.activity_word_bank.*
import kotlinx.android.synthetic.main.word_bank_activity.*
import kotlinx.android.synthetic.main.word_bank_activity_search_add.*
import kotlinx.android.synthetic.main.word_card.view.*
import android.R.drawable.btn_dialog
import android.view.Window.FEATURE_NO_TITLE
import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.Button


class Word_bank : AppCompatActivity() {

    var model = AppModel.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_bank)

        backToMain.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        addWordButton.setOnClickListener{
            if(!sourceWord.text.isEmpty() && !translationWord.text.isEmpty()) {
                model.addWord(Word(sourceWord.text.toString(), translationWord.text.toString()))
                refreshView()
            }
        }

        refreshView()

    }

    fun cardLongPressed(v: View) {
        val view = v as? WordCard
        if (view != null) {
            println("${view.wordOriginal} - ${view.wordTranslated}")

            showDialog(this, view.wordOriginal)
        }

    }

    fun showDialog(activity: Activity, word: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS)

        dialog.setCancelable(true)
        dialog.setContentView(R.layout.popup)

        val w = model.findWord(word)!!

        val originalLabel = dialog.findViewById(R.id.fieldOriginalWord) as TextView
        originalLabel.setText(w.originalWord)

        val translatedLabel = dialog.findViewById(R.id.fieldTranslatedWord) as TextView
        translatedLabel.setText(w.translatedWord)

        val successLabel = dialog.findViewById(R.id.fieldSuccessRate) as TextView

        var successRate: Int = 0
        if(w.totalAttempts != 0) {
            successRate = w.successfulAttempts / w.totalAttempts
        }

        successLabel.setText("${successRate}%")

        val successInfoLabel = dialog.findViewById(R.id.fieldSuccessInfo) as TextView
        successInfoLabel.setText("Success rate: ${w.successfulAttempts} of ${w.totalAttempts}")



        val dialogButton = dialog.findViewById(R.id.deleteButton) as Button
        dialogButton.setOnClickListener {
            model.removeWord(word)
            refreshView()
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun refreshView() {
        ll.removeAllViews()
        for(word in model.wordsList) {
            val wc = createWordCard(word)
            wc.setOnClickListener(::cardLongPressed)
            ll.addView(wc, 0)
        }
        println("refresh")

    }

    private fun createWordCard(w: Word) : WordCard {
        val wc = WordCard(this)
        wc.wordOriginal = w.originalWord
        wc.wordTranslated = w.translatedWord
        return wc
    }
}