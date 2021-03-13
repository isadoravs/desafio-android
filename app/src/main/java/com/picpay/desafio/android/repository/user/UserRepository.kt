package com.picpay.desafio.android.repository.user

import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.network.PicPayService

class UserRepository(private val service: PicPayService) : IUserRepository {
    override suspend fun getAll(): ResponseState<User> {
        service.getUsers().apply {
             return when {
                isSuccessful -> {
                    ResponseState.Success(body())
                }
                else -> ResponseState.Error(message())
            }
        }
    }
}