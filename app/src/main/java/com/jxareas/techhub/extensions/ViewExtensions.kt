package com.jxareas.techhub.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun<T : View> T.visible() {
    this.visibility = View.VISIBLE
}

fun<T : View> T.gone() {
    this.visibility = View.GONE
}

inline infix fun<VB : ViewBinding> ViewGroup.bind(
    crossinline bindingInflater : LayoutInflater.(parent : ViewGroup, attachToParent : Boolean) -> VB
) : VB = LayoutInflater.from(context).let {
    bindingInflater.invoke(it, this, false)
}
