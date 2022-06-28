package com.jxareas.techhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.RecentCoursesListAdapter.CourseViewHolder
import com.jxareas.techhub.adapter.callbacks.CourseDiffCallback
import com.jxareas.techhub.adapter.listeners.CourseAdapterListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemCourseMiniBinding
import com.jxareas.techhub.utils.extensions.bind
import com.jxareas.techhub.utils.extensions.loadImage

class RecentCoursesListAdapter(private val listener: CourseAdapterListener) :
    ListAdapter<CachedCourse, CourseViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ) {

    inner class CourseViewHolder(private val binding: ListItemCourseMiniBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CachedCourse) = binding.run {
            val transitionName = root.context.getString(R.string.card_item_transition)
            ViewCompat.setTransitionName(root, "$transitionName${course.courseId}")
            textViewCourseName.text = course.name
            imageViewCourseImage.loadImage(course.coursePhoto, true)
            imageViewInstructorPhoto.loadImage(course.instructorPhoto)
            root.setOnClickListener { listener.onCourseClicked(root, course) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder =
        CourseViewHolder(parent bind ListItemCourseMiniBinding::inflate)

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) =
        holder.bind(currentList[position])

}
