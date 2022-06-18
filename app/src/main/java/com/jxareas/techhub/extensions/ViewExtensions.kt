package com.jxareas.techhub.extensions

import android.view.View

fun<T : View> T.visible() {
    this.visibility = View.VISIBLE
}

fun<T : View> T.gone() {
    this.visibility = View.GONE
}
