package com.jxareas.techhub.ui.search

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
import com.jxareas.techhub.adapter.TopicListAdapter
import com.jxareas.techhub.adapter.listeners.TopicAdapterListener
import com.jxareas.techhub.data.cache.model.CachedTopic
import com.jxareas.techhub.databinding.FragmentSearchCoursesBinding
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCoursesFragment : Fragment(), TopicAdapterListener {

    private var _binding: FragmentSearchCoursesBinding? = null
    private val binding: FragmentSearchCoursesBinding
        get() = _binding!!

    private val viewModel: SearchCoursesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchCoursesBinding.inflate(inflater, container, false)
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
        viewModel.topics.observe(viewLifecycleOwner) { cachedTopics ->
            cachedTopics?.let { topics ->
                (binding.recyclerViewCourses.adapter as TopicListAdapter).submitList(topics)
            }
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewCourses.run {
        itemAnimator = SpringAddItemAnimator()
        adapter = TopicListAdapter(this@SearchCoursesFragment)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTopicClicked(view: ViewGroup, topic: CachedTopic) {
        val transitionName = getString(R.string.topic_transition)
        val extras = FragmentNavigatorExtras(
            view to transitionName
        )
        val direction = SearchCoursesFragmentDirections.searchToCourseByTopic(topic.name)
        findNavController().navigate(direction, extras)
    }

}