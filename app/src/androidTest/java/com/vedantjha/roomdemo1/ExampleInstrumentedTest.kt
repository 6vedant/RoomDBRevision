package com.vedantjha.roomdemo1

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vedantjha.roomdemo1.data.Word
import com.vedantjha.roomdemo1.data.WordDao
import com.vedantjha.roomdemo1.data.WordRepository
import com.vedantjha.roomdemo1.data.WordRoomDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var wordDao: WordDao
    private lateinit var db: WordRoomDatabase

    @Before
    fun createDB() {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(context, WordRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        wordDao = db.wordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord()  = runBlocking {
        val word = Word("datahere1")
        wordDao.insert(word)

        val word2 = Word("datanothere2")
        wordDao.insert(word2)

        val allWords = wordDao.getAlphabetizedWords().first()
        assertEquals(allWords[0].word, word.word)
        assertEquals(allWords[1].word, word2.word)

    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val word = Word("worddelete1")
        wordDao.insert(word)

        val word2 = Word("word2")
        wordDao.insert(word2)

        wordDao.deleteAll()

        val allWords = wordDao.getAlphabetizedWords().first()
        assertTrue(allWords.isEmpty())
        //assertEquals(0, wordDao.getAlphabetizedWords().asLiveData().value?.size)
    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.vedantjha.roomdemo1", appContext.packageName)
    }
}