package com.jxareas.techhub.ui.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _courses = MutableLiveData<List<CachedCourse>>()
    internal val courses: LiveData<List<CachedCourse>> = _courses

    init {
        getAllCourses()
    }

    private fun getAllCourses() {
            viewModelScope.launch {
                courseRepository.loadCourses{}.collect { listOfCourses ->
                    _courses.postValue(listOfCourses)
                }
            }
    }




}