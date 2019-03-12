package com.example.quizled

class Word {
    var originalWord: String
    var translatedWord: String
    var successfulAttempts: Int = 0
    var totalAttempts: Int = 0

    constructor(original: String, translated: String) {
        originalWord = original
        translatedWord = translated
    }

    override fun toString(): String {
        return "${originalWord}|${translatedWord}|${successfulAttempts}|${totalAttempts}"
    }

    fun fromString(str: String) {
        var arr = str.split("|")
        originalWord = arr[0]
        translatedWord = arr[1]
        successfulAttempts = arr[2].toInt()
        totalAttempts = arr[3].toInt()
    }
}