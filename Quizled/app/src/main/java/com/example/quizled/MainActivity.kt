package com.example.quizled

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_activity_statistics.*
import kotlinx.android.synthetic.main.word_of_the_day.*
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


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

        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        updateWOTD()

        setupPieChart(model.getProgress())
    }

    fun setupPieChart(progress: Int) {
        mainPieChart.setUsePercentValues(true)
        mainPieChart.description.isEnabled = false
        mainPieChart.centerText = "Progress\n $progress%"
        mainPieChart.setCenterTextSize(25f)
        mainPieChart.holeRadius = 70f
        mainPieChart.setHoleColor(0)
        mainPieChart.legend.isEnabled = false

        val yvalues = ArrayList<PieEntry>()
        val rest = (100 - progress).toFloat()
        yvalues.add(PieEntry(progress.toFloat(), 0f))
        yvalues.add(PieEntry(rest, 1f))

        val dataSet = PieDataSet(yvalues, "data")
        dataSet.setDrawIcons(false)
        val colors = ArrayList<Int>()
        colors.add(ColorTemplate.getHoloBlue())
        colors.add(0)
        dataSet.setColors(colors)

        val pieData = PieData(dataSet)
        pieData.setValueTextSize(0f)
        mainPieChart.data = pieData
        mainPieChart.invalidate()
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

        setupPieChart(model.getProgress())
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
