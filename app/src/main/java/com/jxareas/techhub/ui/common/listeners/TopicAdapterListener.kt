package com.jxareas.techhub.ui.common.listeners

import android.view.ViewGroup
import com.jxareas.techhub.domain.model.Topic

@FunctionalInterface
fun interface TopicAdapterListener {
    fun onTopicClicked(viewGroup : ViewGroup, topic : Topic)
}
