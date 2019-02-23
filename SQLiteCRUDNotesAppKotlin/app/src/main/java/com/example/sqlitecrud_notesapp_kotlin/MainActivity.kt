package com.example.sqlitecrud_notesapp_kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizButton.setOnClickListener{
            println("HELLO")
            val intent = Intent(this, Word_bank::class.java);
            startActivity(intent);
        }


        wordsButton.setOnClickListener{
            println("HELLO2")
            val intent = Intent(this, Quiz_Activity::class.java);
            startActivity(intent);
        }
    }


}
