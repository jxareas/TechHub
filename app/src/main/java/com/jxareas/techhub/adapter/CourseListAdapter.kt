package com.jxareas.techhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.util.CourseDiffCallback
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemCourseBinding
import com.jxareas.techhub.utils.extensions.loadImage

class CourseListAdapter(private val listener: CourseAdapterListener) :
    ListAdapter<CachedCourse, CourseListAdapter.CourseListViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ) {


    inner class CourseListViewHolder(private val binding: ListItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CachedCourse) = binding.run {
            val transitionName = root.context.getString(R.string.card_item_transition)
            ViewCompat.setTransitionName(root, "$transitionName${course.courseId}")
            val steps = root.context.getString(R.string.course_steps, course.step.toString(), course.steps.toString())
            textViewCourseName.text = course.name
            textViewCourseSteps.text = steps
            imageViewCourseImage.loadImage(course.coursePhoto, true)
            imageViewInstructorPhoto.loadImage(course.instructorPhoto)
            root.setOnClickListener { listener.onArtworkClicked(root, course) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder =
        CourseListViewHolder(
            ListItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) =
        holder.bind(currentList[position])

}