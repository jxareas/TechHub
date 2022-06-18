package com.jxareas.techhub.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun<T : ImageView> T.load(url : String) =
    Unit.also {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
