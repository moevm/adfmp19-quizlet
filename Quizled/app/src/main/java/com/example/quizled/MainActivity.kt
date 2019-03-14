package com.example.quizled

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_activity_statistics.*
import kotlinx.android.synthetic.main.word_of_the_day.*

class MainActivity : AppCompatActivity() {

    var model = AppModel.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("oncreate")

        val prefs = this.getSharedPreferences("com.example.quizled.storage", 0)
        val savedWords = prefs!!.getStringSet("words", setOf<String>())
        if(savedWords.isEmpty()) {
            model.loadSetFromAssets(this.application, "defaultSet.txt")
        }

        println("restored ${savedWords.size} entries")
        model.restore(savedWords)

        quizButton.setOnClickListener{
            val intent = Intent(this, Quiz_Activity::class.java)
            startActivity(intent)

        }

        wordsButton.setOnClickListener{
            val intent = Intent(this, Word_bank::class.java)
            startActivity(intent)
        }

        shareButton.setOnClickListener{
            /*model.wordsList.clear()
            model.loadSetFromAssets(this.application, "defaultSet.txt")*/
            shareProgress()
        }

        updateWOTD()
    }

    fun shareProgress() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I have ${model.wordsCount} words in my Quizled library and my success rate is ${model.totalSuccessRate}%! #getquizled")
        startActivity(Intent.createChooser(shareIntent, "Share your progress:"))
    }

    fun updateWOTD() {
        val rw = model.randomWord()
        wotdOriginal.setText(rw.originalWord)
        wotdTranslation.setText(rw.translatedWord)
    }

    override fun onResume() {
        super.onResume()

        totalWordsCountLabel.setText("${model.wordsCount}")
        totalSuccessRateLabel.setText("${model.totalSuccessRate}%")
    }

    override fun onDestroy() {
        super.onDestroy()

        println("ondestroy")

        val prefs = this.getSharedPreferences("com.example.quizled.storage", 0)
        val editor = prefs!!.edit()
        editor.putStringSet("words", model.save())
        editor.commit()
    }


}
