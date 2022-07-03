package com.jxareas.techhub.ui.common.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.techhub.databinding.ListItemCourseCardBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.callbacks.CourseDiffCallback
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.ui.common.viewholder.CourseCardViewHolder
import com.jxareas.techhub.utils.extensions.bind


class CourseCardAdapter(private val listener: CourseAdapterListener) :
    ListAdapter<Course, CourseCardViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseCardViewHolder =
        CourseCardViewHolder(parent bind ListItemCourseCardBinding::inflate).apply {
            val course by lazy { currentList[adapterPosition] }
            itemView.setOnClickListener { view ->
                listener.onClicked(view as ViewGroup, course)
            }
        }

    override fun onBindViewHolder(holder: CourseCardViewHolder, position: Int): Unit =
        holder.bind(currentList[position])

}
