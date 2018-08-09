package com.shreyas208.bustle.ui

import android.app.Activity
import android.support.annotation.StringRes
import android.util.Log
import android.widget.Toast
import com.shreyas208.bustle.R
import com.shreyas208.bustle.models.StopsResponse
import io.reactivex.Flowable

fun Activity.showErrorToast(message: String): Unit {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showErrorToast(@StringRes message: Int): Unit {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun <T> Flowable<T>.withErrorHandling(activity: Activity): Flowable<T> {
    activity.showErrorToast(R.string.an_error_occured)
    onErrorResumeNext { t: Throwable ->
        Log.e("Bustle", t.message)
        Flowable.empty<T>()
    }
}