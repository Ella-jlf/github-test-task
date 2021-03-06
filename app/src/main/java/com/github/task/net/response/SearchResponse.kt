package com.github.task.net.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val repos: List<RepoResponse>,
    @SerializedName("total_count")
    val totalCount: Int
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
    val countWatchers: Int = 0,
    @SerializedName("created_at")
    val date: String = "",
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("html_url")
    val url: String = ""
)

data class Owner(
    @SerializedName("avatar_url")
    val imageUrl: String = "",
    @SerializedName("login")
    val name: String = "",
    @SerializedName("html_url")
    val url: String = "",
    @SerializedName("id")
    val id: Int = 0
)

enum class SortType(val queryName: String) {
    Stars("stars"),
    Forks("forks"),
    Updated("updated")
}

enum class OrderType(val queryName: String) {
    Descending("desc"),
    Ascending("asc")
}