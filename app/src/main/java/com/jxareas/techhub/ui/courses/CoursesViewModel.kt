package com.jxareas.techhub.ui.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.domain.repository.CourseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseRepository: CourseRepositoryImpl
) : ViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    internal val courses: LiveData<List<Course>> = _courses

    init {
        getAllCourses()
    }

    private fun getAllCourses() {
        viewModelScope.launch {
            courseRepository.getAllCourses {}.collect { listOfCourses ->
                _courses.postValue(listOfCourses)
            }
        }
    }


}
