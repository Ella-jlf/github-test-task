package com.github.task.net.service

import com.github.task.net.response.SearchResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("/search/repositories")
    fun getRepos(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Observable<SearchResponse>
}