package com.vedantjha.roomdemo1.data

import androidx.annotation.WorkerThread
import com.vedantjha.roomdemo1.data.Word
import com.vedantjha.roomdemo1.data.WordDao
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}