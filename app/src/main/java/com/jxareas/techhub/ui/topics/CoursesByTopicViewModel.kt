package com.jxareas.techhub.ui.topics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.data.repository.CourseRepository
import com.jxareas.techhub.domain.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesByTopicViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _courses = MutableLiveData<List<Course>>()
    internal val courses: LiveData<List<Course>>
        get() = _courses

    init {
        savedStateHandle.get<String>("topic")?.let { topicName ->
            getCoursesByTopicName(topicName)
        }
    }

    private fun getCoursesByTopicName(topic: String) {
        viewModelScope.launch {
            courseRepository.getCoursesByTopicName(topic).collectLatest { listOfCourses ->
                _courses.postValue(listOfCourses)
            }
        }
    }

}
