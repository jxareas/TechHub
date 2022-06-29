package com.jxareas.techhub.ui.common.viewholder

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemCourseCardBinding
import com.jxareas.techhub.utils.extensions.loadImage

class CourseCardViewHolder(
    private val binding: ListItemCourseCardBinding,
    private val listener: CourseAdapterListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(course: CachedCourse) = with(binding) {

        val transitionName = root.context.getString(R.string.card_item_transition)
        ViewCompat.setTransitionName(root, "$transitionName${course.courseId}")
        textViewCourseName.text = course.name
        textViewCourseTopic.text = course.topicName
        textViewCourseSteps.text = course.steps.toString()
        imageViewCoursePhoto.loadImage(course.coursePhoto, true)
        imageViewInstructorPhoto.loadImage(course.instructorPhoto)
        root.setOnClickListener { listener.onCourseClicked(root, course) }

    }

}
