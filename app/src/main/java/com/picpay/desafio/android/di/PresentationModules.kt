package com.picpay.desafio.android.di

import com.picpay.desafio.android.view.users.ContactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val contactsModule = module {
    viewModel { ContactsViewModel(get()) }
}