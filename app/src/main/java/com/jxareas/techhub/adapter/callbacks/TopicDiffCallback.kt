package com.jxareas.techhub.adapter.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.techhub.data.cache.model.CachedTopic

object TopicDiffCallback : DiffUtil.ItemCallback<CachedTopic>() {
    override fun areItemsTheSame(oldItem: CachedTopic, newItem: CachedTopic): Boolean =
        oldItem.topicId == newItem.topicId

    override fun areContentsTheSame(oldItem: CachedTopic, newItem: CachedTopic): Boolean =
        oldItem.name == newItem.name
}