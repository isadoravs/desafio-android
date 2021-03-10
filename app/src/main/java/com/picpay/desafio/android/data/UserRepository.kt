package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.network.PicPayService
import retrofit2.Response

class UserRepository(private val service: PicPayService) : IUserRepository {
    override suspend fun getUsers(): Response<List<User>> {
        return service.getUsers()
    }
}