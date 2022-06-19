package com.jxareas.techhub.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.adapter.TopicListAdapter.TopicViewHolder
import com.jxareas.techhub.adapter.util.TopicDiffCallback
import com.jxareas.techhub.data.cache.model.CachedTopic
import com.jxareas.techhub.databinding.ListItemTopicBinding
import com.jxareas.techhub.extensions.bind

class TopicListAdapter : ListAdapter<CachedTopic, TopicViewHolder>(TopicDiffCallback) {

    inner class TopicViewHolder(private val binding: ListItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(topic : CachedTopic) = binding.run {
                textViewTopicName.text = topic.name
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder =
        TopicViewHolder(parent bind ListItemTopicBinding::inflate)

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) =
        holder.bind(currentList[position])

}