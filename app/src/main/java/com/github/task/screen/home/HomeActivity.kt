package com.github.task.screen.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.task.R
import com.github.task.databinding.ActivityHomeBinding
import com.github.task.extension.gone
import com.github.task.extension.hideKeyboard
import com.github.task.extension.visible
import com.github.task.net.response.OrderType
import com.github.task.net.response.RepoResponse
import com.github.task.net.response.SortType
import com.github.task.screen.base.BaseMvpActivity
import com.github.task.screen.home.adapter.RepoAdapter


class HomeActivity : BaseMvpActivity(R.layout.activity_home), HomeView {

    private val binding by viewBinding(ActivityHomeBinding::bind)

    @InjectPresenter
    lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding.inclRepos.root.adapter = RepoAdapter(this::openUrl)

        binding.inclSearch.vSearch.setOnClickListener {
            val query = binding.inclSearch.vSearchQuery.text.toString()

            val sort = when (binding.inclSearch.vgSortFilters.checkedRadioButtonId) {
                binding.inclSearch.vRadioForks.id -> {
                    SortType.Forks
                }
                binding.inclSearch.vRadioUpdated.id -> {
                    SortType.Updated
                }
                binding.inclSearch.vRadioStars.id -> {
                    SortType.Stars
                }
                else -> {
                    SortType.Updated
                }
            }

            val order = when (binding.inclSearch.vgOrderFilters.checkedRadioButtonId) {
                binding.inclSearch.vRadioDescending.id -> {
                    OrderType.Descending
                }
                binding.inclSearch.vRadioAscending.id -> {
                    OrderType.Ascending
                }
                else -> {
                    OrderType.Descending
                }
            }

            it.hideKeyboard()
            presenter.getRepos(query, sort, order)
        }
    }

    override fun setRepos(repos: List<RepoResponse>) {
        binding.inclError.vErrorNotFound.gone()
        binding.inclError.vErrorManyRepos.gone()

        (binding.inclRepos.root.adapter as? RepoAdapter)?.repos = repos

        if (repos.isEmpty()) {
            binding.inclError.vErrorNotFound.visible()
        }
    }

    override fun showErrorScreen() {
        binding.inclError.vErrorNotFound.gone()
        binding.inclError.vErrorManyRepos.visible()

        (binding.inclRepos.root.adapter as? RepoAdapter)?.repos = listOf()
    }

    private fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}