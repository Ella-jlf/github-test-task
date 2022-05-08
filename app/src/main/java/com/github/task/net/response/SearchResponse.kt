package com.github.task.net.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val repos: List<RepoResponse>,
    val total_count: Int
)

data class RepoResponse(
    @SerializedName("forks_count")
    val countForks: Int = 0,
    @SerializedName("name")
    val title: String = "",
    @SerializedName("open_issues_count")
    val countIssues: Int = 0,
    @SerializedName("owner")
    val owner: Owner = Owner(),
    @SerializedName("watchers_count")
    val countWatchers: Int = 0
)

data class Owner(
    @SerializedName("avatar_url")
    val imageUrl: String = "",
    @SerializedName("login")
    val name: String = ""
)