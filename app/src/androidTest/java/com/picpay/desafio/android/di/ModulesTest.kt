package com.picpay.desafio.android.di

import com.picpay.desafio.android.network.PicPayServiceConfig.retrofit
import com.picpay.desafio.android.network.PicPayServiceConfig.service
import org.koin.dsl.module

fun retrofitModuleTest(baseUrl: String) = module {
    single(override = true) {
        retrofit(get(), baseUrl)
    }
    single(override = true)  {
        service(get())
    }
}