package com.vedantjha.roomdemo1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vedantjha.roomdemo1.data.Word
import com.vedantjha.roomdemo1.data.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

class WordViewModel (private val wordRepository: WordRepository) : ViewModel() {
    val allWords: LiveData<List<Word>> = wordRepository.allWords.asLiveData()

    fun insert(word: Word) {
        viewModelScope.launch {
            wordRepository.insert(word)
        }
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class!")
    }
}