package com.ozimos.sample.ui.util

sealed class StateUtil<out T : Any> {
    object Loading : StateUtil<Nothing>()
    data class Success<out S : Any>(val message: String, val data: S) : StateUtil<S>()
    data class Failed(val message: String) : StateUtil<Nothing>()
}