package com.jxareas.techhub.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.RelatedCoursesListAdapter.CourseViewHolder
import com.jxareas.techhub.adapter.callbacks.CourseDiffCallback
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemRelatedCourseBinding
import com.jxareas.techhub.utils.extensions.bind
import com.jxareas.techhub.utils.extensions.loadImage

class RelatedCoursesListAdapter(private val onCoursedClick: (CachedCourse) -> Unit) :
    ListAdapter<CachedCourse, CourseViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ) {

    inner class CourseViewHolder(private val binding: ListItemRelatedCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: CachedCourse) = binding.run {
            textViewCourseName.text = course.name
            val steps = root.context.getString(
                R.string.course_steps,
                course.step.toString(),
                course.steps.toString()
            )
            textViewCourseSteps.text = steps
            imageViewCourse.loadImage(course.coursePhoto)
            imageViewInstructorPhoto.loadImage(course.instructorPhoto)

            root.setOnClickListener {
                onCoursedClick(course)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder =
        CourseViewHolder(parent bind ListItemRelatedCourseBinding::inflate)

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) =
        holder.bind(currentList[position])

}
