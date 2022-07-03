package com.jxareas.techhub.ui.common.viewholder

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.ListItemTopicBinding
import com.jxareas.techhub.domain.model.Topic

class TopicViewHolder(
    private val binding: ListItemTopicBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(topic: Topic) = binding.run {
        val transition = itemView.context.getString(R.string.topic_transition)
        ViewCompat.setTransitionName(root, "$transition${topic.topicId}")
        textViewTopicName.text = topic.name
    }

}
