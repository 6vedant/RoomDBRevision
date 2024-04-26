package com.vedantjha.roomdemo1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        // singleton object for WordRoomDatabase
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            // if the instance is null, then create it
            // otherwise return it
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java, "word_database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { wordRoomDatabase ->
                scope.launch { populateDatabase(wordRoomDatabase.wordDao()) }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            // delete db
            wordDao.deleteAll()

            val word = Word( "word1")
            wordDao.insert(word)
            val word2 = Word("vedant")


            wordDao.insert(word2)

        }
    }
}