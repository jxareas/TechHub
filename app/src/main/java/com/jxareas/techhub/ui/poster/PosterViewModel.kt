package com.jxareas.techhub.ui.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.data.repository.CourseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PosterViewModel @Inject constructor(
    private val courseRepository: CourseRepositoryImpl,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _courseImage = MutableLiveData<String>()
    internal var courseImage: LiveData<String> = _courseImage

    init {
        savedStateHandle.get<Int>("courseId")?.let { courseId ->
            loadCourse(courseId)
        }
    }

    private fun loadCourse(courseId: Int) {
        viewModelScope.launch {
            courseRepository.getCourseById(courseId).collectLatest { cachedCourse ->
                _courseImage.postValue(cachedCourse.coursePhoto)
            }
        }
    }


}
