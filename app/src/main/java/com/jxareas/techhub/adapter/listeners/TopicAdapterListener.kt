package com.jxareas.techhub.adapter.listeners

import android.view.ViewGroup
import com.jxareas.techhub.data.cache.model.CachedTopic

@FunctionalInterface
fun interface TopicAdapterListener {
    fun onTopicClicked(viewGroup : ViewGroup, topic : CachedTopic)
}
