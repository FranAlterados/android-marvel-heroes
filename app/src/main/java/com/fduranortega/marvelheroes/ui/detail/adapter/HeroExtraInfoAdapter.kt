package com.fduranortega.marvelheroes.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fduranortega.marvelheroes.data.model.bo.HeroExtraBO
import com.fduranortega.marvelheroes.databinding.RowHeroExtraInfoBinding

class HeroExtraInfoAdapter : RecyclerView.Adapter<HeroExtraInfoAdapter.HeroExtraInfoViewHolder>() {

    private var currentList: MutableList<HeroExtraBO> = mutableListOf()
    private var numExtras: Int = 0

    fun setNumberExtras(numExtras: Int) {
        this.numExtras = numExtras
        notifyDataSetChanged()
    }

    fun setData(data: List<HeroExtraBO>) {
        currentList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroExtraInfoViewHolder {
        val itemBinding = RowHeroExtraInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroExtraInfoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HeroExtraInfoViewHolder, position: Int) {
        currentList.getOrNull(position)?.let {
            holder.bind(it)
        } ?: run {
            holder.bindPlaceholder()
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.isNotEmpty()) currentList.size else numExtras
    }

    class HeroExtraInfoViewHolder(private val itemBinding: RowHeroExtraInfoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(heroExtraBO: HeroExtraBO) {
            itemBinding.shimmer.visibility = View.GONE
            itemBinding.shimmer.hideShimmer()
            itemBinding.heroExtraName.text = heroExtraBO.title
            Glide.with(itemBinding.root.context)
                .load(heroExtraBO.urlImage)
                .into(itemBinding.heroExtraImage)
        }

        fun bindPlaceholder() {
            itemBinding.shimmer.visibility = View.VISIBLE
            itemBinding.shimmer.startShimmer()
        }
    }
}