package com.jxareas.techhub.adapter.callbacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.adapter.listeners.ItemTouchHelperListener

class ItemTouchHelperCallback(private val listener: ItemTouchHelperListener) :
    ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean = true

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int = makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean =
        listener.onItemMove(recyclerView, viewHolder.adapterPosition, target.adapterPosition)

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
        Unit
}
