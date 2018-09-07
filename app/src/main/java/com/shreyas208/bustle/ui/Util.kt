package com.shreyas208.bustle.ui

import android.app.Activity
import android.support.annotation.StringRes
import android.util.Log
import android.widget.Toast
import com.shreyas208.bustle.R
import io.reactivex.Flowable

fun Activity.showToast(message: String): Unit {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(@StringRes message: Int): Unit {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun <T> Flowable<T>.withErrorHandling(activity: Activity): Flowable<T> {
    return onErrorResumeNext { t: Throwable ->
        activity.showToast(R.string.an_error_occured)
        Log.e("Bustle", t.message)
        Flowable.empty<T>()
    }
}

fun <T> Flowable<T>.withErrorHandling(): Flowable<T> {
    return onErrorResumeNext { t: Throwable ->
        Log.e("Bustle", t.message)
        Flowable.empty<T>()
    }
}