package com.jxareas.techhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemCourseCardBinding
import com.jxareas.techhub.extensions.loadImage


class CourseCardAdapter(private val onCourseClickedListener: (CachedCourse) -> Unit) :
    ListAdapter<CachedCourse, CourseCardAdapter.CourseCardViewHolder>(DiffCallback) {

    private companion object DiffCallback : DiffUtil.ItemCallback<CachedCourse>() {
        override fun areItemsTheSame(oldItem: CachedCourse, newItem: CachedCourse): Boolean =
            oldItem.courseId == newItem.courseId

        override fun areContentsTheSame(oldItem: CachedCourse, newItem: CachedCourse): Boolean =
            oldItem.coursePhoto == newItem.coursePhoto

    }

    inner class CourseCardViewHolder(private val binding: ListItemCourseCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onCourseClickedListener.invoke(currentList[adapterPosition])
            }
        }

        fun bind(course: CachedCourse) = with(binding) {
            textViewCourseName.text = course.name
            textViewCourseTopic.text = course.topicName
            textViewCourseSteps.text = course.steps.toString()
            imageViewCoursePhoto.loadImage(course.coursePhoto)
            imageViewInstructorPhoto.loadImage(course.instructorPhoto)
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