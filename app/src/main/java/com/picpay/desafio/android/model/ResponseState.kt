package com.picpay.desafio.android.model

sealed class ResponseState<out T> {
    class Success<T>(val data: List<T>?) : ResponseState<T>()
    class Error<T>(val message: String) : ResponseState<T>()
}