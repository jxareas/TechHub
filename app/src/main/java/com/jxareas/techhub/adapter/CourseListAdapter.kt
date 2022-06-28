package com.jxareas.techhub.adapter

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.CourseListAdapter.CourseListViewHolder
import com.jxareas.techhub.adapter.callbacks.CourseDiffCallback
import com.jxareas.techhub.adapter.listeners.CourseAdapterListener
import com.jxareas.techhub.adapter.listeners.ItemTouchHelperListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemCourseBinding
import com.jxareas.techhub.utils.extensions.bind
import com.jxareas.techhub.utils.extensions.loadImage
import java.util.*

class CourseListAdapter(private val listener: CourseAdapterListener) :
    ListAdapter<CachedCourse, CourseListViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ), ItemTouchHelperListener {


    inner class CourseListViewHolder(private val binding: ListItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CachedCourse) = binding.run {
            val transitionName = root.context.getString(R.string.card_item_transition)
            ViewCompat.setTransitionName(root, "$transitionName${course.courseId}")
            val steps = root.context.getString(
                R.string.course_steps,
                course.step.toString(),
                course.steps.toString()
            )
            textViewCourseName.text = course.name
            textViewCourseSteps.text = steps
            imageViewCourseImage.loadImage(course.coursePhoto, true)
            imageViewInstructorPhoto.loadImage(course.instructorPhoto)
            root.setOnClickListener { listener.onCourseClicked(root, course) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder =
        CourseListViewHolder(parent bind ListItemCourseBinding::inflate)

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun onItemMove(
        recyclerView: RecyclerView,
        fromPosition: Int,
        toPosition: Int
    ): Boolean {
        if (fromPosition < toPosition)
            for (index in fromPosition until toPosition)
                Collections.swap(currentList.toMutableList(), index, index + 1)
        else
            for (index in toPosition downTo fromPosition + 1)
                Collections.swap(currentList.toMutableList(), index, index - 1)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

}
