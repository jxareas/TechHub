package com.jxareas.techhub.ui.common.listeners

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {
    fun onItemDismissed(viewHolder: RecyclerView.ViewHolder, position : Int)
}
