package com.jxareas.techhub.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun<T : ImageView> T.loadImage(url : String) =
    Unit.also {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
