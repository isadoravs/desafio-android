package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.network.PicPayService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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