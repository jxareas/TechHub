package com.jxareas.techhub.ui.common.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.techhub.domain.model.Topic

object TopicDiffCallback : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean =
        oldItem.topicId == newItem.topicId

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean =
        oldItem.name == newItem.name
}
