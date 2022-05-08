package com.github.task.screen.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.task.R
import com.github.task.databinding.ItemRepoBinding
import com.github.task.net.response.RepoResponse

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding by viewBinding(ItemRepoBinding::bind)

    fun bind(repoInfo: RepoResponse) {
        binding.vRepoName.text = repoInfo.title
        binding.vRepoForks.text = repoInfo.countForks.toString()
        binding.vRepoWatchers.text = repoInfo.countWatchers.toString()
        binding.vRepoIssues.text = repoInfo.countIssues.toString()
        binding.vRepoOwnerName.text = repoInfo.owner.name

        Glide
            .with(binding.vRepoOwnerImage)
            .load(repoInfo.owner.imageUrl)
            .placeholder(R.drawable.img_default_avatar)
            .into(binding.vRepoOwnerImage)
    }
}