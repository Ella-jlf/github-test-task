package com.github.task.screen.home

import com.github.task.net.response.RepoResponse
import com.github.task.screen.base.BaseMvpView

interface HomeView : BaseMvpView {

    fun setRepos(repos: List<RepoResponse>)
    fun showErrorScreen()
    fun addRepos(repos: List<RepoResponse>)
}