package com.example.quizled


class AppModel private constructor() {
    init { println("This ($this) is a singleton") }
    private object Holder { val INSTANCE = AppModel() }
    companion object {
        val instance: AppModel by lazy { Holder.INSTANCE }
    }

    var wordsList = arrayListOf<Word>()

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
        for (word in from) {
            var w = Word("1", "2")
            w.fromString(word)
            wordsList.add(w)
        }
    }
}