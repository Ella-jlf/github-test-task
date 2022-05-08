package com.github.task.net.repo

import com.github.task.net.response.SearchResponse
import com.github.task.net.service.GitHubService
import io.reactivex.rxjava3.core.Observable

class GitHubRepo(private val gitHubService: GitHubService) {

    fun getRepos(query: String): Observable<SearchResponse> {
        return gitHubService.getRepos(query)
    }
}