package com.jxareas.techhub.ui.common.events

@FunctionalInterface
fun interface OnLongPressed<ID> {
    fun onLongPressed(id: ID)
}
