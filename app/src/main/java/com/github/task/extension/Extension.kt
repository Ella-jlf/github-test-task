package com.github.task.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.joda.time.DateTime

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun <T> Observable<T>.subscribeApiCall(
    listener: (T) -> Unit,
    errorListener: (Throwable) -> Unit = {},
): Disposable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                listener(it)
            },
            {
                errorListener(it)
            }
        )
}

fun <T> Observable<T>.subscribeApiCall(listener: (T) -> Unit): Disposable {
    return subscribeApiCall(listener, {})
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun String.parseDateTimeToPrettyString(): String {
    return try {
        val dateTime = DateTime.parse(this)
        "${dateTime.dayOfMonth}/${dateTime.monthOfYear}/${dateTime.year}"
    } catch (e: Exception) {
        ""
    }
}