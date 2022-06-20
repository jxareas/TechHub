package com.jxareas.techhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.listeners.CourseAdapterListener
import com.jxareas.techhub.adapter.diffutil.CourseDiffCallback
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemCourseCardBinding
import com.jxareas.techhub.utils.extensions.loadImage


class CourseCardAdapter(private val listener: CourseAdapterListener) :
    ListAdapter<CachedCourse, CourseCardAdapter.CourseCardViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ) {

    inner class CourseCardViewHolder(private val binding: ListItemCourseCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: CachedCourse) = with(binding) {
            val transitionName = root.context.getString(R.string.card_item_transition)
            ViewCompat.setTransitionName(root, "$transitionName${course.courseId}")
            textViewCourseName.text = course.name
            textViewCourseTopic.text = course.topicName
            textViewCourseSteps.text = course.steps.toString()
            imageViewCoursePhoto.loadImage(course.coursePhoto, true)
            imageViewInstructorPhoto.loadImage(course.instructorPhoto)

            root.setOnClickListener {
                listener.onCourseClicked(root, course)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseCardViewHolder =
        CourseCardViewHolder(
            ListItemCourseCardBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CourseCardViewHolder, position: Int): Unit =
        holder.bind(currentList[position])


}