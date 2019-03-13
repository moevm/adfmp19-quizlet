package com.example.quizled

import kotlinx.android.synthetic.main.word_card.view.*
import kotlin.math.roundToInt


class AppModel private constructor() {
    init { println("This ($this) is a singleton") }
    private object Holder { val INSTANCE = AppModel() }
    companion object {
        val instance: AppModel by lazy { Holder.INSTANCE }
    }

    var wordsList = arrayListOf<Word>()

    val wordsCount get() = wordsList.size

    val totalSuccessRate: Int
        get() {
            var successful: Int = 0
            var total: Int = 0

            for(word in wordsList) {
                successful += word.successfulAttempts
                total += word.totalAttempts
            }

            val rate = successful.toFloat() / total.toFloat() * 100.0F

            return rate.roundToInt()
        }

    fun addWord(w: Word) : Boolean {
        wordsList.add(w)
        println("(${wordsList.size}) add word ${w.originalWord} to ${w.translatedWord}")
        return true
    }

    fun removeWord(w: String) {
        for(word in wordsList) {
            if(word.originalWord == w) {
                wordsList.remove(word)
                return
            }
        }
    }

    fun findWord(withOriginalText: String) : Word? {
        for(word in wordsList) {
            if(word.originalWord == withOriginalText) {
                return word
            }
        }

        return null
    }

    fun save() : HashSet<String> {
        val set = HashSet<String>()
        for (word in wordsList) {
            set.add(word.toString())
        }

        return set
    }

    fun restore(from: Set<String>) {
        wordsList.clear()
        for (word in from) {
            var w = Word("1", "2")
            w.fromString(word)
            wordsList.add(w)
        }
    }

    fun randomWord() : Word {
        return wordsList.random()
    }


}