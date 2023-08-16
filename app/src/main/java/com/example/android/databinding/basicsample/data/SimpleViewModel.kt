package com.example.android.databinding.basicsample.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class SimpleViewModel : ViewModel() {
    private val _name = MutableLiveData("Magnus")
    private val _lastName = MutableLiveData("Carlsen")
    private val _likes =  MutableLiveData(0)

    val name: LiveData<String> = _name
    val lastName: LiveData<String> = _lastName
    val likes: LiveData<Int> = _likes

    fun onLike() {
        _likes.value = (_likes.value ?: 0) + 1
    }

    val popularity: LiveData<Popularity> = Transformations.map(_likes) {
        when {
            it > 50 -> Popularity.STAR
            it > 10 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }
}

enum class Popularity {
    NORMAL,
    POPULAR,
    STAR
}
