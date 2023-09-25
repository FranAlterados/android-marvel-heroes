package com.fduranortega.marvelheroes.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fduranortega.marvelheroes.databinding.RowHeroBinding
import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.presentation.detail.DetailActivity
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette

class HeroAdapter : ListAdapter<HeroBO, HeroAdapter.HeroViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val itemBinding = RowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        currentList.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    class HeroViewHolder(private val itemBinding: RowHeroBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(heroBO: HeroBO) {
            itemBinding.heroName.text = heroBO.name
            Glide.with(itemBinding.root.context)
                .load(heroBO.urlImage)
                .listener(
                    GlidePalette.with(heroBO.urlImage)
                        .use(BitmapPalette.Profile.MUTED_LIGHT)
                        .intoCallBack { palette ->
                            val rgb = palette?.dominantSwatch?.rgb
                            if (rgb != null) {
                                itemBinding.card.setCardBackgroundColor(rgb)
                            }
                        }.crossfade(true)
                )
                .into(itemBinding.heroImage)

            itemBinding.root.setOnClickListener {
                DetailActivity.startActivity(itemBinding.transformationLayout, heroBO)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<HeroBO>() {
            override fun areItemsTheSame(oldItem: HeroBO, newItem: HeroBO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HeroBO, newItem: HeroBO): Boolean {
                return oldItem == newItem
            }
        }
    }
}