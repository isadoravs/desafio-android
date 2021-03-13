package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User
import retrofit2.Response

interface IUserRepository {
    suspend fun getAll(): ResponseState<User>
}