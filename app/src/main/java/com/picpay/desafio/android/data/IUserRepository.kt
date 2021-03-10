package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.User
import retrofit2.Response

interface IUserRepository {
    suspend fun getUsers(): Response<List<User>>
}