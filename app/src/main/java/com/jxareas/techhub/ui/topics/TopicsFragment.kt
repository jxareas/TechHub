package com.jxareas.techhub.ui.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.FragmentTopicsBinding
import com.jxareas.techhub.domain.model.Topic
import com.jxareas.techhub.ui.common.adapters.TopicListAdapter
import com.jxareas.techhub.ui.common.listeners.TopicAdapterListener
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import com.jxareas.techhub.utils.extensions.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopicsFragment : Fragment(), TopicAdapterListener {

    private var _binding: FragmentTopicsBinding? = null
    private val binding: FragmentTopicsBinding
        get() = _binding!!

    private val topicAdapter = TopicListAdapter(this)
    private val viewModel: TopicsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }


    private fun setupObservers() {
        viewModel.topics.observe(viewLifecycleOwner) { listOfTopics ->
            listOfTopics?.let { newTopics -> topicAdapter.submitList(newTopics) }
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewCourses.run {
        itemAnimator = SpringAddItemAnimator()
        adapter = topicAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClicked(viewGroup: ViewGroup, item: Topic) {
        val transitionName = getString(R.string.topic_transition)
        val extras =
            FragmentNavigatorExtras(viewGroup to transitionName)
        val directions =
            TopicsFragmentDirections.searchToCourseByTopic(item.name)
        findNavController().safeNavigate(directions, extras)
    }

}
