package com.fduranortega.marvelheroes.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fduranortega.marvelheroes.data.model.bo.HeroBO
import com.fduranortega.marvelheroes.databinding.RowHeroBinding
import com.fduranortega.marvelheroes.ui.detail.DetailActivity
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette

class HeroAdapter : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    private var currentList: MutableList<HeroBO> = mutableListOf()

    fun setData(data: List<HeroBO>) {
        currentList.addAll(data)
        notifyItemRangeInserted(currentList.size - data.size, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val itemBinding = RowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        currentList.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
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
}