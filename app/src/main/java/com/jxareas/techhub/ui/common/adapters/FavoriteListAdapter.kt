package com.jxareas.techhub.ui.common.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.databinding.ListItemCourseBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.callbacks.CourseDiffCallback
import com.jxareas.techhub.ui.common.listeners.FavoriteAdapterListener
import com.jxareas.techhub.ui.common.listeners.ItemTouchHelperListener
import com.jxareas.techhub.ui.common.viewholder.FavoriteViewHolder
import com.jxareas.techhub.utils.extensions.bind

class FavoriteListAdapter(private val listener: FavoriteAdapterListener) :
    ListAdapter<Course, FavoriteViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ), ItemTouchHelperListener {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder =
        FavoriteViewHolder(parent bind ListItemCourseBinding::inflate).apply {
            val course by lazy { currentList[adapterPosition] }
            itemView.setOnClickListener { view ->
                listener.onClicked(view as ViewGroup, course)
            }
            itemView.setOnLongClickListener {
                listener.onLongPressed(course.courseId)
                true
            }
        }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun onItemDismissed(viewHolder: RecyclerView.ViewHolder, position: Int) {
        listener.onSwiped(currentList[position].courseId)
    }

}
