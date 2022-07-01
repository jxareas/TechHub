package com.jxareas.techhub.ui.search

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialContainerTransform
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.FragmentExpandedCourseSearchBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.adapters.RecentCoursesListAdapter
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import com.jxareas.techhub.utils.extensions.getLong
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExpandedSearchFragment : Fragment(), CourseAdapterListener {

    private var _binding: FragmentExpandedCourseSearchBinding? = null
    private val binding: FragmentExpandedCourseSearchBinding
        get() = _binding!!

    private val recentCoursesAdapter = RecentCoursesListAdapter(this@ExpandedSearchFragment)

    private val viewModel: ExpandedSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_main
            scrimColor = Color.TRANSPARENT
            duration = resources.getLong(R.integer.material_motion_duration_medium_2)
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExpandedCourseSearchBinding.inflate(inflater, container, false).apply {
            searchViewExploreCourses.isIconified = false
        }
        viewModel.getAllRecentlyAccessedCourses()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        setupListeners()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupListeners() {

        binding.searchViewExploreCourses.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val currentQuery = binding.searchViewExploreCourses.query.toString()
                val transitionName = getString(R.string.search_results_transition)
                val extras =
                    FragmentNavigatorExtras(binding.searchViewExploreCourses to transitionName)
                val directions =
                    ExpandedSearchFragmentDirections.actionExpandedSearchToResults(currentQuery)
                findNavController().navigate(directions, extras)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true

        })


        binding.searchViewExploreCourses.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus)
                showInputMethod(view.findFocus())
        }
        binding.searchViewExploreCourses.requestFocus()
    }


    private fun showInputMethod(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }


    private fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { recentCourses ->
            recentCoursesAdapter.submitList(recentCourses)
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewRecentCourses.run {
        itemAnimator = SpringAddItemAnimator()
        adapter = recentCoursesAdapter
        setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClicked(viewGroup: ViewGroup, item: Course) {
        val extras =
            FragmentNavigatorExtras(viewGroup to getString(R.string.course_detail_transition))
        val direction =
            ExpandedSearchFragmentDirections.actionExpandedToDetail(item.courseId)
        findNavController().navigate(direction, extras)
    }

}
