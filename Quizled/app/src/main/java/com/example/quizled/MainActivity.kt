package com.example.quizled

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var model = AppModel.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = this.getSharedPreferences("com.example.quizled.storage", 0)
        val savedWords = prefs!!.getStringSet("words", setOf<String>())
        model.restore(savedWords)

        quizButton.setOnClickListener{
            val intent = Intent(this, Quiz_Activity::class.java)
            startActivity(intent)

        }


        wordsButton.setOnClickListener{
            val intent = Intent(this, Word_bank::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val prefs = this.getSharedPreferences("com.example.quizled.storage", 0)
        val editor = prefs!!.edit()
        editor.putStringSet("words", model.save())
        editor.commit()
    }


}
