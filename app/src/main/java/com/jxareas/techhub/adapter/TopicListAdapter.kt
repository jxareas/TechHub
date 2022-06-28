package com.jxareas.techhub.adapter

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.TopicListAdapter.TopicViewHolder
import com.jxareas.techhub.adapter.callbacks.TopicDiffCallback
import com.jxareas.techhub.adapter.listeners.TopicAdapterListener
import com.jxareas.techhub.data.cache.model.CachedTopic
import com.jxareas.techhub.databinding.ListItemTopicBinding
import com.jxareas.techhub.utils.extensions.bind

class TopicListAdapter(private val listener: TopicAdapterListener) :
    ListAdapter<CachedTopic, TopicViewHolder>(
        AsyncDifferConfig.Builder(TopicDiffCallback).build()
    ) {

    inner class TopicViewHolder(private val binding: ListItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: CachedTopic) = binding.run {

            val transition = itemView.context.getString(R.string.topic_transition)
            ViewCompat.setTransitionName(root, "$transition${topic.topicId}")

            textViewTopicName.text = topic.name

            root.setOnClickListener {
                listener.onTopicClicked(root, topic)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder =
        TopicViewHolder(parent bind ListItemTopicBinding::inflate)

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) =
        holder.bind(currentList[position])

}
