package com.github.task.screen.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import by.kirich1409.viewbindingdelegate.viewBinding
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.task.R
import com.github.task.databinding.ActivityHomeBinding
import com.github.task.extension.hideKeyboard
import com.github.task.net.response.RepoResponse
import com.github.task.screen.base.BaseMvpActivity
import com.github.task.screen.home.adapter.RepoAdapter

class HomeActivity : BaseMvpActivity(R.layout.activity_home), HomeView {

    private val binding by viewBinding(ActivityHomeBinding::bind)

    @InjectPresenter
    lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        presenter.getRepos("00")
    }

    private fun initUI() {
        binding.inclRepos.root.adapter = RepoAdapter()

        binding.inclSearch.vSearch.setOnClickListener {
            val query = binding.inclSearch.vSearchQuery.text.toString()

            it.hideKeyboard()
            presenter.getRepos(query)
        }
    }

    override fun setRepos(repos: List<RepoResponse>) {
        (binding.inclRepos.root.adapter as? RepoAdapter)?.repos = repos
    }
}