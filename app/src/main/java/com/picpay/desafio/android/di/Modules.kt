package com.picpay.desafio.android.di

import com.picpay.desafio.android.repository.user.IUserRepository
import com.picpay.desafio.android.repository.user.UserRepository
import com.picpay.desafio.android.network.PicPayServiceConfig.okHttp
import com.picpay.desafio.android.network.PicPayServiceConfig.retrofit
import com.picpay.desafio.android.network.PicPayServiceConfig.service
import com.picpay.desafio.android.view.users.UsersViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    single {
        okHttp(get())
    }
    single {
        retrofit(get())
    }
    single {
        service(get())
    }
}

val repositoryModule = module {
    factory<IUserRepository> { UserRepository(get()) }
}

val viewModelModule = module {
    viewModel { UsersViewModel(get(), Dispatchers.IO) }
}
