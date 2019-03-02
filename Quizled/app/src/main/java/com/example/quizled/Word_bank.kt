package com.example.quizled

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_word_bank.*

class Word_bank : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_bank)
        backToMain.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }
    }
}
