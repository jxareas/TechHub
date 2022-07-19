package com.jxareas.techhub.ui.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.data.repository.CourseRepositoryImpl
import com.jxareas.techhub.utils.RequestStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseRepository: CourseRepositoryImpl
) : ViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    internal val courses: LiveData<List<Course>> = _courses

    private val _state = MutableLiveData<RequestStatus>()
    internal val state: LiveData<RequestStatus> = _state

    init {
        getAllCourses()
    }

    fun onRefreshData() = getAllCourses()

    private fun getAllCourses() {
        viewModelScope.launch {
            _state.postValue(RequestStatus.LOADING)
            courseRepository.getAllCourses(
                onInit = { _state.postValue(RequestStatus.LOADING) },
                onError = { _state.postValue(RequestStatus.ERROR) },
                onSuccess = { _state.postValue(RequestStatus.DONE) }
            ).collectLatest { listOfCourses ->
                _courses.postValue(listOfCourses)
            }
        }
    }




}
