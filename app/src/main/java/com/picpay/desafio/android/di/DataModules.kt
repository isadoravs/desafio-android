package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.IUserRepository
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.network.okHttp
import com.picpay.desafio.android.network.retrofit
import com.picpay.desafio.android.network.service
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

