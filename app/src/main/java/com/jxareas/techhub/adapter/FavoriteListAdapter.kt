package com.jxareas.techhub.adapter

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.FavoriteListAdapter.FavoritesListViewHolder
import com.jxareas.techhub.adapter.callbacks.CourseDiffCallback
import com.jxareas.techhub.adapter.listeners.FavoriteAdapterListener
import com.jxareas.techhub.adapter.listeners.ItemTouchHelperListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemCourseBinding
import com.jxareas.techhub.utils.extensions.bind
import com.jxareas.techhub.utils.extensions.loadImage

class FavoriteListAdapter(private val listener: FavoriteAdapterListener) :
    ListAdapter<CachedCourse, FavoritesListViewHolder>(
        AsyncDifferConfig.Builder(CourseDiffCallback).build()
    ), ItemTouchHelperListener {


    inner class FavoritesListViewHolder(private val binding: ListItemCourseBinding) :
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
            root.setOnLongClickListener {
                listener.onFavoriteLongPressed(course.courseId)
                true
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesListViewHolder =
        FavoritesListViewHolder(parent bind ListItemCourseBinding::inflate)

    override fun onBindViewHolder(holder: FavoritesListViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun onItemDismissed(viewHolder: RecyclerView.ViewHolder, position: Int) {
        listener.onFavoriteCourseSwiped(currentList[position].courseId)
    }


}
