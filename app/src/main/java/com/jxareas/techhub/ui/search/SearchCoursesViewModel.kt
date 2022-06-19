package com.jxareas.techhub.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.data.cache.model.CachedTopic
import com.jxareas.techhub.data.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCoursesViewModel @Inject constructor(
    private val topicRepository: TopicRepository  
) : ViewModel() {

    private val _topics = MutableLiveData<List<CachedTopic>>()
    internal val topics: LiveData<List<CachedTopic>> = _topics

    init {
        getAllTopics()
    }

    private fun getAllTopics() {
        viewModelScope.launch {
            topicRepository.getAllTopics{}.collect { listOfTopics ->
                _topics.postValue(listOfTopics)
            }
        }
    }

}