package com.example.quizled

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo

import kotlinx.android.synthetic.main.activity_quiz_.*
import kotlinx.android.synthetic.main.content_quiz_.*

class Quiz_Activity : AppCompatActivity() {

    var model = AppModel.instance
    var runScore: Int = 0
    var runAttempts: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_)

        backToMain2.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        nextRound()
    }

    private fun nextRound() {
        val randomWord = model.randomWord()

        wotdOriginal.setText(randomWord.originalWord)

        fieldForTranslation.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                println("typing done")

                runAttempts += 1

                if(randomWord.translatedWord.toLowerCase() == fieldForTranslation.text.toString().toLowerCase()) {
                    runScore += 1
                    randomWord.successfulAttempts += 1
                }

                randomWord.totalAttempts += 1
                scoreLabel.setText("Score: ${runScore}/${runAttempts}")
                fieldForTranslation.setText("")
                nextRound()

                true
            } else {
                false
            }
        }
    }

}
