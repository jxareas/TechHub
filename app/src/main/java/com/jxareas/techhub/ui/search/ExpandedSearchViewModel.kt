package com.jxareas.techhub.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.domain.repository.CourseRepository
import com.jxareas.techhub.domain.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpandedSearchViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
) : ViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    internal var courses: LiveData<List<Course>> = _courses

    init {
        getAllRecentlyAccessedCourses()
    }

    fun getAllRecentlyAccessedCourses() {
        viewModelScope.launch {
            courseRepository.getRecentCourses().collectLatest { recentCourses ->
                _courses.postValue(recentCourses)
            }
        }
    }

}
