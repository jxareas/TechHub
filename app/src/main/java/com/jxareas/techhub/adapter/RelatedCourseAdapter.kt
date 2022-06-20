package com.jxareas.techhub.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.callbacks.CourseDiffCallback
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemRelatedCourseBinding
import com.jxareas.techhub.utils.extensions.loadImage

class RelatedCourseAdapter(private val onCoursedClick: (CachedCourse) -> Unit) :
    ListAdapter<CachedCourse, RelatedCourseAdapter.CourseViewHolder>(
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
        CourseViewHolder(
            ListItemRelatedCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) =
        holder.bind(currentList[position])

}