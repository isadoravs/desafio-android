package com.picpay.desafio.android.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PicPayApi {
    private const val url = "http://careers.picpay.com/tests/mobdev/"

    private val gson: Gson by lazy { GsonBuilder().create() }

    private const val CACHE_CONTROL = "cache-control"

    private const val cacheSize = (5 * 1024 * 1024).toLong()

    private fun getCache(context: Context) = Cache(context.cacheDir, cacheSize)

    fun okHttp(context: Context) = OkHttpClient.Builder()
        .cache(getCache(context))
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(context)) {
                request.newBuilder().header(CACHE_CONTROL, "public, max-age=" + 5 * 60).build()
            } else {
                request.newBuilder().header(
                    CACHE_CONTROL,
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()
            }
            chain.proceed(request)
        }
        .build()

    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun service(retrofit: Retrofit): PicPayService = retrofit.create(PicPayService::class.java)

    private fun hasNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}