package com.jxareas.techhub.utils.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator

fun NavController.safeNavigate(direction: NavDirections, extras: FragmentNavigator.Extras? = null) {

    currentDestination?.getAction(direction.actionId)?.run {
        if(extras != null) navigate(direction, extras) else navigate(direction)
    }
}
