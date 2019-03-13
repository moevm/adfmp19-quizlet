package com.example.quizled

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_word_bank.*
import kotlinx.android.synthetic.main.word_bank_activity.*
import kotlinx.android.synthetic.main.word_bank_activity_search_add.*
import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.Button
import kotlin.math.roundToInt


class Word_bank : AppCompatActivity() {

    var model = AppModel.instance
    var wordFilter = ""
    var sortingType = SortingMethod.Ascending

    enum class SortingMethod {
        Ascending,
        Descending
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_bank)

        backToMain.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        sortButton.setOnClickListener {
            when (sortingType) {
                SortingMethod.Ascending -> sortingType = SortingMethod.Descending
                SortingMethod.Descending -> sortingType = SortingMethod.Ascending
            }
            refreshView()
        }

        addWordButton.setOnClickListener{
            if(!sourceWord.text.isEmpty() && !translationWord.text.isEmpty()) {
                model.addWord(Word(sourceWord.text.toString(), translationWord.text.toString()))
                refreshView()
            }
        }

        filterField.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                println("query: ${newText}")
                refreshView()
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })


        refreshView()

    }

    fun cardLongPressed(v: View) {
        val view = v as? WordCard
        if (view != null) {
            println("${view.wordOriginal} - ${view.wordTranslated}")

            showPopup(this, view.wordOriginal)
        }

    }

    fun showPopup(activity: Activity, word: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS)

        dialog.setCancelable(true)
        dialog.setContentView(R.layout.popup)

        val w = model.findWord(word)!!

        val originalLabel = dialog.findViewById(R.id.wotdOriginal) as TextView
        originalLabel.setText(w.originalWord)

        val translatedLabel = dialog.findViewById(R.id.fieldTranslatedWord) as TextView
        translatedLabel.setText(w.translatedWord)

        val successLabel = dialog.findViewById(R.id.fieldSuccessRate) as TextView

        var successRate: Float = 0.0F
        if(w.totalAttempts != 0) {
            successRate = w.successfulAttempts.toFloat() / w.totalAttempts.toFloat() * 100
        }

        successLabel.setText("${successRate.roundToInt()}%")

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

        val filter = filterField.query.toString()
        var allWords: List<Word> = model.wordsList
        if (!filter.isEmpty()) {
             allWords = model.wordsList.filter {
                it.originalWord.contains(filter, ignoreCase = true)
            }
        }

        when (sortingType) {
            SortingMethod.Ascending ->
                allWords = allWords.sortedBy { it.originalWord }
            SortingMethod.Descending ->
                allWords = allWords.sortedByDescending { it.originalWord }
        }


        ll.removeAllViews()
        for(word in allWords) {
            val wc = createWordCard(word)
            wc.setOnClickListener(::cardLongPressed)
            ll.addView(wc)
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