package com.github.task.screen.home

import com.arellomobile.mvp.InjectViewState
import com.github.task.application.App
import com.github.task.extension.subscribeApiCall
import com.github.task.net.repo.GitHubRepo
import com.github.task.net.response.OrderType
import com.github.task.net.response.SortType
import com.github.task.screen.base.BaseMvpPresenter
import org.kodein.di.instance

@InjectViewState
class HomePresenter : BaseMvpPresenter<HomeView>() {

    private val gitHubRepo by App.di.instance<GitHubRepo>()

    fun getRepos(query: String, sort: SortType, order: OrderType) {
        addDisposable(
            gitHubRepo.getRepos(query = query, sort = sort, order = order).subscribeApiCall({
                val repos = it.repos

                viewState.setRepos(repos)
            }, {
                viewState.showErrorScreen()
            })
        )
    }
}