package com.example.quizled

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    var model = AppModel.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        resetStatisticsButton.setOnClickListener {

            model.resetStats()

            /*lateinit var dialog: AlertDialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reset all statistics")
            builder.setMessage("Are you sure?")
            val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> model.resetStats()
                    DialogInterface.BUTTON_NEGATIVE -> println("no")
                }
            }

            builder.setPositiveButton("Reset", dialogClickListener)
            builder.setNegativeButton("Cancel", dialogClickListener)
            dialog = builder.create()
            dialog.show()*/
        }

        resetAllButton.setOnClickListener {

            model.wordsList.clear()
            model.loadSetFromAssets(this.application, "defaultSet.txt")

            /*lateinit var dialog: AlertDialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reset all words")
            builder.setMessage("Are you sure?")
            val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        model.wordsList.clear()
                        model.loadSetFromAssets(this.application, "defaultSet.txt")
                    }
                    DialogInterface.BUTTON_NEGATIVE -> println("no")
                }
            }

            builder.setPositiveButton("Reset", dialogClickListener)
            builder.setNegativeButton("Cancel", dialogClickListener)
            dialog = builder.create()
            dialog.show()*/
        }
    }
}
