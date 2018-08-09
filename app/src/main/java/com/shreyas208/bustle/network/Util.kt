package com.shreyas208.bustle.network

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.Builder.configure(): Retrofit.Builder =
        addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

fun <T> Flowable<T>.configure(): Flowable<T> =
        subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())