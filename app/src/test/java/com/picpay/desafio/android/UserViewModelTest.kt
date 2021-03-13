package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.IUserRepository
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.mocks.UserRepositoryMock
import com.picpay.desafio.android.mocks.expectedUsers
import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.network.PicPayService
import com.picpay.desafio.android.view.users.UsersViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import retrofit2.Response
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.*

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contactsObserver : Observer<List<User>>

    @Mock
    private lateinit var errorObserver : Observer<Boolean>

    private lateinit var viewModel : UsersViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
        viewModel.contacts.removeObserver(contactsObserver)
    }

    @Test
    fun `When init view model should fetch contacts`() = coroutineTestRule.testDispatcher.runBlockingTest {
        viewModel = UsersViewModel(UserRepositoryMock(ResponseState.Success(expectedUsers)), coroutineTestRule.testDispatcher)

        viewModel.contacts.observeForever(contactsObserver)

        verify(contactsObserver).onChanged(expectedUsers)
    }

    @Test
    fun `When init view model with error should post error`() = coroutineTestRule.testDispatcher.runBlockingTest {
        viewModel = UsersViewModel(UserRepositoryMock(ResponseState.Error("error")), coroutineTestRule.testDispatcher)

        viewModel.error.observeForever(errorObserver)

        verify(errorObserver).onChanged(true)
    }


}