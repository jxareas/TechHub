package com.jxareas.techhub.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun <T : ImageView> T.loadImage(url: String, fadeTransition: Boolean = false) =
    Unit.also {
        if (fadeTransition)
            Glide.with(this.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
        else Glide.with(this.context)
            .load(url)
            .into(this)
    }


