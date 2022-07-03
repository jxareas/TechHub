package com.jxareas.techhub.ui.common.viewholder

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.ListItemCourseMiniBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.utils.extensions.loadImage

class RecentCourseViewHolder(
    private val binding: ListItemCourseMiniBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(course: Course) = binding.run {
        val transitionName = root.context.getString(R.string.card_item_transition)
        ViewCompat.setTransitionName(root, "$transitionName${course.courseId}")
        textViewCourseName.text = course.name
        imageViewCourseImage.loadImage(course.coursePhoto, true)
        imageViewInstructorPhoto.loadImage(course.instructorPhoto)
    }

}
