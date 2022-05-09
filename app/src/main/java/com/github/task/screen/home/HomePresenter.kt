package com.github.task.screen.home

import com.arellomobile.mvp.InjectViewState
import com.github.task.application.App
import com.github.task.extension.subscribeApiCall
import com.github.task.net.repo.GitHubRepo
import com.github.task.net.response.OrderType
import com.github.task.net.response.SortType
import com.github.task.screen.base.BaseMvpPresenter
import org.kodein.di.instance
import java.util.concurrent.atomic.AtomicBoolean

@InjectViewState
class HomePresenter : BaseMvpPresenter<HomeView>() {

    private val gitHubRepo by App.di.instance<GitHubRepo>()
    private var totalCount = 0
    private var querySave: String = ""
    private var sortSave: SortType = SortType.Updated
    private var orderSave: OrderType = OrderType.Descending
    private var pageSave: Int = 1
    private val loading: AtomicBoolean = AtomicBoolean(false)

    fun getRepos(query: String, sort: SortType, order: OrderType) {
        querySave = query
        sortSave = sort
        orderSave = order
        pageSave = 1

        addDisposable(
            gitHubRepo.getRepos(query = query, sort = sort, order = order).subscribeApiCall({
                val repos = it.repos
                totalCount = it.totalCount

                viewState.setRepos(repos)
            }, {
                viewState.showErrorScreen()
            })
        )
    }

    fun tryLoadMoreRepos(currentItemCount: Int) {
        if (loading.get()) {
            return
        }

        loading.set(true)
        pageSave += 1
        if (currentItemCount < totalCount) {
            addDisposable(
                gitHubRepo.getRepos(
                    query = querySave,
                    sort = sortSave,
                    order = orderSave,
                    page = pageSave
                ).subscribeApiCall({
                    viewState.addRepos(it.repos)
                    loading.set(false)
                }, {
                    loading.set(false)
                })
            )
        }
    }
}