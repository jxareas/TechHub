package com.jxareas.techhub.adapter.listeners

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {
    fun onItemDismissed(viewHolder: RecyclerView.ViewHolder, position : Int)
}
