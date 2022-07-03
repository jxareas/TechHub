package com.jxareas.techhub.ui.common.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.techhub.databinding.ListItemTopicBinding
import com.jxareas.techhub.domain.model.Topic
import com.jxareas.techhub.ui.common.callbacks.TopicDiffCallback
import com.jxareas.techhub.ui.common.listeners.TopicAdapterListener
import com.jxareas.techhub.ui.common.viewholder.TopicViewHolder
import com.jxareas.techhub.utils.extensions.bind

class TopicListAdapter(private val listener: TopicAdapterListener) :
    ListAdapter<Topic, TopicViewHolder>(
        AsyncDifferConfig.Builder(TopicDiffCallback).build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder =
        TopicViewHolder(parent bind ListItemTopicBinding::inflate).apply {
            val topic by lazy { currentList[adapterPosition] }
            itemView.setOnClickListener { view ->
                listener.onClicked(view as ViewGroup, topic)
            }
        }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) =
        holder.bind(currentList[position])

}
