package com.fduranortega.marvelheroes.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fduranortega.marvelheroes.databinding.RowHeroExtraInfoBinding
import com.fduranortega.marvelheroes.domain.model.HeroExtraBO

class HeroExtraInfoAdapter : ListAdapter<HeroExtraBO, HeroExtraInfoAdapter.HeroExtraInfoViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroExtraInfoViewHolder {
        val itemBinding = RowHeroExtraInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroExtraInfoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HeroExtraInfoViewHolder, position: Int) {
        currentList.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    class HeroExtraInfoViewHolder(private val itemBinding: RowHeroExtraInfoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(heroExtraBO: HeroExtraBO) {
            if (heroExtraBO.id == PLACEHOLDER_ID) {
                itemBinding.shimmer.visibility = View.VISIBLE
                itemBinding.shimmer.startShimmer()
            } else {
                itemBinding.shimmer.visibility = View.GONE
                itemBinding.shimmer.hideShimmer()
                itemBinding.heroExtraName.text = heroExtraBO.title
                Glide.with(itemBinding.root.context)
                    .load(heroExtraBO.urlImage)
                    .into(itemBinding.heroExtraImage)
            }
        }
    }

    companion object {
        const val PLACEHOLDER_ID = -1

        private val diffUtil = object : DiffUtil.ItemCallback<HeroExtraBO>() {
            override fun areItemsTheSame(oldItem: HeroExtraBO, newItem: HeroExtraBO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HeroExtraBO, newItem: HeroExtraBO): Boolean {
                return oldItem == newItem
            }
        }
    }
}