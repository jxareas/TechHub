package com.jxareas.techhub.utils.animation

import androidx.dynamicanimation.animation.SpringAnimation

fun listenForAllSpringsEnd(
    onEnd: (Boolean) -> Unit,
    vararg springs: SpringAnimation
) = MultiSpringEndListener(onEnd, *springs)