package com.jxareas.techhub.ui.courses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.FragmentCoursesBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.adapters.CourseCardAdapter
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.utils.RequestStatus
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import com.jxareas.techhub.utils.extensions.gone
import com.jxareas.techhub.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : Fragment(), CourseAdapterListener {

    private var _binding: FragmentCoursesBinding? = null
    private val binding: FragmentCoursesBinding
        get() = _binding!!

    private val courseCardAdapter = CourseCardAdapter(this)
    private val viewModel: CoursesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObservers()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() = binding.run {
        swipeRefreshLayoutExploreCourses.setOnRefreshListener {
            viewModel.onRefreshData()
            swipeRefreshLayoutExploreCourses.isRefreshing = false
        }

        searchViewExploreCourses.setOnQueryTextFocusChangeListener { searchView, hasFocus ->
            if (hasFocus)
                searchView.clearFocus().also { navigateToExpandedSearch(searchView) }
        }

        searchViewExploreCourses.setOnClickListener { searchView -> navigateToExpandedSearch(searchView) }
    }

    private fun navigateToExpandedSearch(searchView: View) {
        val transitionName = getString(R.string.search_view_transition)
        val extras = FragmentNavigatorExtras(
            searchView to transitionName
        )
        val direction = CoursesFragmentDirections.actionCoursesToExpandedSearch()
        findNavController().navigate(direction, extras)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { listOfCourses ->
            listOfCourses?.let { newCourses -> courseCardAdapter.submitList(newCourses) }
        }
        viewModel.state.observe(viewLifecycleOwner) { requestStatus ->
            requestStatus?.let { newStatus ->
                handleStatus(newStatus) }
        }
    }

    private fun handleStatus(newStatus: RequestStatus) = binding.run {
        when (newStatus) {
            RequestStatus.LOADING -> {
                circularProgressIndicator.visible()
            }
            RequestStatus.DONE -> {
                circularProgressIndicator.gone()
            }
            RequestStatus.ERROR -> {
                circularProgressIndicator.gone()
                Log.d("SOMETHING", "ERROR HANDLED BY THE VIEW")
                Toast.makeText(requireContext(), "An error ocurred fetching the data.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewCourses.run {
        itemAnimator = SpringAddItemAnimator()
        adapter = courseCardAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClicked(viewGroup: ViewGroup, item: Course) {
        val extras =
            FragmentNavigatorExtras(viewGroup to getString(R.string.course_detail_transition))
        val direction =
            CoursesFragmentDirections.coursesToDetailFragment(item.courseId)
        findNavController().navigate(direction, extras)
    }

}
