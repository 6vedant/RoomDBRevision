package com.vedantjha.roomdemo1

import android.app.Application
import com.vedantjha.roomdemo1.data.WordRepository
import com.vedantjha.roomdemo1.data.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}