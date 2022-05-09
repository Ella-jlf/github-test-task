package com.github.task.screen.home

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.github.task.R
import com.github.task.databinding.ActivityHomeBinding
import com.github.task.databinding.ViewRepoDetailBinding
import com.github.task.extension.gone
import com.github.task.extension.hideKeyboard
import com.github.task.extension.parseDateTimeToPrettyString
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
        binding.inclRepos.root.adapter = RepoAdapter(this::openRepoDetails, this::openUrl)

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
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Toast.makeText(this, "Wrong url", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openRepoDetails(repo: RepoResponse) {
        val repoDetailsBinding =
            ViewRepoDetailBinding.inflate(LayoutInflater.from(this))

        repoDetailsBinding.vRepoName.text = repo.title
        repoDetailsBinding.vRepoForks.text = repo.countForks.toString()
        repoDetailsBinding.vRepoIssues.text = repo.countIssues.toString()
        repoDetailsBinding.vRepoWatchers.text = repo.countWatchers.toString()
        repoDetailsBinding.vRepoCreated.text = getString(R.string.created_at, repo.date.parseDateTimeToPrettyString())
        repoDetailsBinding.vRepoSize.text = getString(R.string.size, repo.size.toString())
        repoDetailsBinding.vRepoReference.setOnClickListener {
            openUrl(repo.url)
        }

        repoDetailsBinding.vRepoOwnerName.text = repo.owner.name
        repoDetailsBinding.vRepoOwnerId.text = getString(R.string.user_id, repo.owner.id.toString())
        repoDetailsBinding.vRepoOwnerReference.setOnClickListener {
            openUrl(repo.owner.url)
        }

        Glide
            .with(repoDetailsBinding.root)
            .load(repo.owner.imageUrl)
            .placeholder(R.drawable.img_default_avatar)
            .into(repoDetailsBinding.vRepoOwnerImage)

        AlertDialog.Builder(this).setView(repoDetailsBinding.root).show()
    }
}