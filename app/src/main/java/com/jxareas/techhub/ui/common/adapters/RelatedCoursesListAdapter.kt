package com.jxareas.techhub.ui.common.adapters


import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.techhub.databinding.ListItemRelatedCourseBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.callbacks.CourseDiffCallback
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.ui.common.viewholder.RelatedCourseViewHolder
import com.jxareas.techhub.utils.extensions.bind

class RelatedCoursesListAdapter(private val listener: CourseAdapterListener) :
    ListAdapter<Course, RelatedCourseViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RelatedCourseViewHolder =
        RelatedCourseViewHolder(parent bind ListItemRelatedCourseBinding::inflate).apply {
            val course by lazy { currentList[adapterPosition] }
            itemView.setOnClickListener { view ->
                listener.onClicked(view as ViewGroup, course)
            }
        }

    override fun onBindViewHolder(holder: RelatedCourseViewHolder, position: Int) =
        holder.bind(currentList[position])


}


