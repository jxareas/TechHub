package com.jxareas.techhub.ui.common.listeners

import android.view.ViewGroup
import com.jxareas.techhub.data.cache.model.CachedTopic

@FunctionalInterface
fun interface TopicAdapterListener {
    fun onTopicClicked(viewGroup : ViewGroup, topic : CachedTopic)
}
