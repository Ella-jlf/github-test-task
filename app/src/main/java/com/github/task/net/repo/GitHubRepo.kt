package com.github.task.net.repo

import com.github.task.net.response.OrderType
import com.github.task.net.response.SearchResponse
import com.github.task.net.response.SortType
import com.github.task.net.service.GitHubService
import io.reactivex.rxjava3.core.Observable

class GitHubRepo(private val gitHubService: GitHubService) {

    private val baseCountPerPage = 50
    private val firstPage = 1

    fun getRepos(
        query: String,
        perPage: Int = baseCountPerPage,
        page: Int = firstPage,
        sort: SortType = SortType.Updated,
        order: OrderType = OrderType.Descending
    ): Observable<SearchResponse> {
        return gitHubService.getRepos(query, perPage, page, sort.queryName, order.queryName)
    }
}