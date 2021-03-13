package com.picpay.desafio.android

import com.picpay.desafio.android.repository.user.UserRepository
import com.picpay.desafio.android.mocks.PicPayServiceMock
import com.picpay.desafio.android.mocks.expectedUsers
import com.picpay.desafio.android.model.ResponseState
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private lateinit var userRepository: UserRepository

    @Test
    fun `When get all with success should return a list of users`() = runBlockingTest {
        userRepository = UserRepository(PicPayServiceMock())

        userRepository.getAll().apply {
            assertTrue(this is ResponseState.Success)
            assertEquals((this as ResponseState.Success).data, expectedUsers)
        }
    }

    @Test
    fun `When get all with error should return a message`() = runBlockingTest {
        userRepository = UserRepository(PicPayServiceMock(true))

        userRepository.getAll().apply {
            assertTrue(this is ResponseState.Error)
            assertNotNull((this as ResponseState.Error).message)
        }
    }

}