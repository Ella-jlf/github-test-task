package com.github.task.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.task.R
import com.github.task.net.response.RepoResponse
import kotlin.math.abs

class RepoAdapter : RecyclerView.Adapter<RepoViewHolder>() {

    var repos: List<RepoResponse> = listOf()
        set(value) {
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
                for (i in dif-1 downTo 0) {
                    notifyItemRemoved(i + oldSize - dif)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_repo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}