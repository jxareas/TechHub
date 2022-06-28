package com.jxareas.techhub.adapter.listeners

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {
    fun onItemMove(recyclerView: RecyclerView, fromPosition : Int, toPosition : Int) : Boolean
}
