package com.github.task.net.service

import com.github.task.net.response.SearchResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("/search/repositories")
    fun getRepos(@Query("q") search: String): Observable<SearchResponse>
}