package com.picpay.desafio.android.mocks

import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.network.PicPayService
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class PicPayServiceMock(private val error: Boolean = false) : PicPayService {
    override suspend fun getUsers(): Response<List<User>> {
        return if (error) {
            Response.error(404, "error".toResponseBody())
        } else Response.success(expectedUsers)
    }
}