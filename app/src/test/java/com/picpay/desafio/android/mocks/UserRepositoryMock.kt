package com.picpay.desafio.android.mocks

import com.picpay.desafio.android.data.IUserRepository
import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User

class UserRepositoryMock(private val result: ResponseState<User>) : IUserRepository {
    override suspend fun getAll(): ResponseState<User> {
        return result
    }
}