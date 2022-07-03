package com.jxareas.techhub.ui.common.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.techhub.databinding.ListItemCourseMiniBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.callbacks.CourseDiffCallback
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.ui.common.viewholder.RecentCourseViewHolder
import com.jxareas.techhub.utils.extensions.bind

class RecentCoursesListAdapter(private val listener: CourseAdapterListener) :
    ListAdapter<Course, RecentCourseViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentCourseViewHolder =
        RecentCourseViewHolder(parent bind ListItemCourseMiniBinding::inflate).apply {
            itemView.setOnClickListener { view ->
                val course by lazy { currentList[adapterPosition] }
                listener.onClicked(view as ViewGroup, course)
            }
        }

    override fun onBindViewHolder(holder: RecentCourseViewHolder, position: Int) =
        holder.bind(currentList[position])

}
