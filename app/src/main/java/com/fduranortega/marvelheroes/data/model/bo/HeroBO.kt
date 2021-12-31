package com.fduranortega.marvelheroes.data.model.bo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeroBO(
    val id: Int,
    val name: String,
    val description: String,
    val urlImage: String,
) : Parcelable