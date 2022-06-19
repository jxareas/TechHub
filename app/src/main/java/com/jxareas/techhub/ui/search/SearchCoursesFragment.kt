package com.jxareas.techhub.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jxareas.techhub.adapter.TopicListAdapter
import com.jxareas.techhub.databinding.FragmentSearchCoursesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCoursesFragment : Fragment() {

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



    private fun setupObservers() {
        viewModel.topics.observe(viewLifecycleOwner) { cachedTopics ->
            cachedTopics?.let { topics ->
                (binding.recyclerViewTopics.adapter as TopicListAdapter).submitList(topics)
            }
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewTopics.run {
        adapter = TopicListAdapter()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}