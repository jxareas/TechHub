package com.jxareas.techhub.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.viewbinding.ViewBinding
import com.jxareas.techhub.utils.animation.getKey

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

fun View.spring(
    property: DynamicAnimation.ViewProperty,
    stiffness: Float = 200f,
    damping: Float = 0.3f,
    startVelocity: Float? = null
): SpringAnimation {
    val key = getKey(property)
    var springAnim = getTag(key) as? SpringAnimation?
    if (springAnim == null) {
        springAnim = SpringAnimation(this, property).apply {
            spring = SpringForce().apply {
                this.dampingRatio = damping
                this.stiffness = stiffness
                startVelocity?.let { setStartVelocity(it) }
            }
        }
        setTag(key, springAnim)
    }
    return springAnim
}


