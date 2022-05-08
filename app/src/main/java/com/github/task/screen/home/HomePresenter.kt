package com.github.task.screen.home

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.task.application.App
import com.github.task.extension.subscribeApiCall
import com.github.task.net.repo.GitHubRepo
import com.github.task.screen.base.BaseMvpPresenter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.kodein.di.instance

@InjectViewState
class HomePresenter : BaseMvpPresenter<HomeView>() {

    private val gitHubRepo by App.di.instance<GitHubRepo>()

    fun getRepos(query: String) {
        addDisposable(
            gitHubRepo.getRepos(query).subscribeApiCall({
                val repos = it.repos

                viewState.setRepos(repos)
            }, {
                Log.i("DEBUGSOSI", "$it")
            })
        )
    }
}