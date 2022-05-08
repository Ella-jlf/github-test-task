package com.github.task.screen.base

import com.arellomobile.mvp.MvpPresenter
import com.github.task.application.App
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.kodein.di.instance

abstract class BaseMvpPresenter<V : BaseMvpView> : MvpPresenter<V>() {
    private val compositeDisposable by App.di.instance<CompositeDisposable>()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }
}
