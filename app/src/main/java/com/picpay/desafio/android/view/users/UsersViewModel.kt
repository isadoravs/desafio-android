package com.picpay.desafio.android.view.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.repository.user.IUserRepository
import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class UsersViewModel(private val userRepository: IUserRepository, private val dispatcher: CoroutineDispatcher) : ViewModel() {
    private val _contacts = MutableLiveData<List<User>>()
    val contacts : LiveData<List<User>> = _contacts

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean> = _error

    init {
        fetchContacts()
    }

    private fun fetchContacts() {
        viewModelScope.launch(dispatcher) {
            userRepository.getAll().apply {
                when (this) {
                    is ResponseState.Success -> {
                        _contacts.postValue(data)
                    }
                    else -> _error.postValue(true)
                }
            }
        }
    }
}