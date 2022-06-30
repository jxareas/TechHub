package com.jxareas.techhub.ui.common.events

@FunctionalInterface
fun interface OnSwiped<ID> {
    fun onSwiped(id: ID)
}
