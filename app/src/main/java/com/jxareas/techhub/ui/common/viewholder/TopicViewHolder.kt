package com.jxareas.techhub.ui.common.viewholder

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.ui.common.listeners.TopicAdapterListener
import com.jxareas.techhub.data.cache.model.CachedTopic
import com.jxareas.techhub.databinding.ListItemTopicBinding

class TopicViewHolder(
    private val binding: ListItemTopicBinding,
    private val listener: TopicAdapterListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(topic: CachedTopic) = binding.run {

        val transition = itemView.context.getString(R.string.topic_transition)
        ViewCompat.setTransitionName(root, "$transition${topic.topicId}")
        textViewTopicName.text = topic.name
        root.setOnClickListener { listener.onTopicClicked(root, topic) }

    }

}
