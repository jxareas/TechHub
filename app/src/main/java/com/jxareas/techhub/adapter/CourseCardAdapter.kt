package com.jxareas.techhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemCourseBinding
import com.jxareas.techhub.extensions.load


class CourseCardAdapter :
    ListAdapter<CachedCourse, CourseCardAdapter.CourseCardViewHolder>(DiffCallback) {

    private companion object DiffCallback : DiffUtil.ItemCallback<CachedCourse>() {
        override fun areItemsTheSame(oldItem: CachedCourse, newItem: CachedCourse): Boolean =
            oldItem.courseId == newItem.courseId

        override fun areContentsTheSame(oldItem: CachedCourse, newItem: CachedCourse): Boolean =
            oldItem.imageUrl == newItem.imageUrl

    }

    inner class CourseCardViewHolder(private val binding: ListItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CachedCourse) = with(binding) {
            textViewCourseName.text = course.name
            textViewCourseTopic.text = course.topicName
            textViewCourseSteps.text = course.steps.toString()
            imageViewCoursePhoto.load(course.imageUrl)
            imageViewInstructorPhoto.load(course.instructorPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseCardViewHolder =
        CourseCardViewHolder(
            ListItemCourseBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CourseCardViewHolder, position: Int) : Unit =
        holder.bind(currentList[position])


}