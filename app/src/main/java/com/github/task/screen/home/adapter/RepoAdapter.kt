package com.github.task.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.task.R
import com.github.task.net.response.RepoResponse
import kotlin.math.abs

class RepoAdapter(
    private val onItemClicked: (RepoResponse) -> Unit,
    private val onImageClicked: (String) -> Unit
) :
    RecyclerView.Adapter<RepoViewHolder>() {

    var repos: List<RepoResponse> = listOf()
        set(value) {
            //smooth replacement on all data changed

            val oldSize = field.size
            val dif = field.size - value.size
            field = value

            if (dif < 0) {
                for (i in 0 until oldSize) {
                    notifyItemChanged(i)
                }
                for (i in 0 until abs(dif)) {
                    notifyItemInserted(i + oldSize)
                }
            } else {
                for (i in 0 until (oldSize - dif)) {
                    notifyItemChanged(i)
                }
                for (i in dif - 1 downTo 0) {
                    notifyItemRemoved(i + oldSize - dif)
                }
            }
        }

    fun loadRepos(newRepos: List<RepoResponse>) {
        val allRepos = repos.toMutableList()
        allRepos.addAll(newRepos)
        repos = allRepos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_repo,
                parent,
                false
            ),
            onItemClicked,
            onImageClicked
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}