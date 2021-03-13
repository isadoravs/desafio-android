package com.picpay.desafio.android.repository.user

import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User

interface IUserRepository {
    suspend fun getAll(): ResponseState<User>
}