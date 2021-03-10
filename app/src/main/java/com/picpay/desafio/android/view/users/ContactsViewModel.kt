package com.picpay.desafio.android.view.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.IUserRepository
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.network.PicPayService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: IUserRepository) : ViewModel() {
    val contacts = MutableLiveData<List<User>>()
    val error = MutableLiveData<Boolean>()

    fun fetchContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.getUsers()

            if (res.isSuccessful) {
                contacts.postValue(res.body())
            } else {
                error.postValue(true)
            }
        }
    }
}