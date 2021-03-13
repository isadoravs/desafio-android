package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.IUserRepository
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.network.PicPayApi.okHttp
import com.picpay.desafio.android.network.PicPayApi.retrofit
import com.picpay.desafio.android.network.PicPayApi.service
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
    single<IUserRepository> { UserRepository(get()) }
}

val viewModelModule = module {
    viewModel { UsersViewModel(get(), Dispatchers.IO) }
}
