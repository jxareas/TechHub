package com.jxareas.techhub.ui.topics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.domain.repository.TopicRepository
import com.jxareas.techhub.domain.model.Topic
import com.jxareas.techhub.utils.RequestStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicsViewModel @Inject constructor(
    private val topicRepository: TopicRepository
) : ViewModel() {

    private val _topics = MutableLiveData<List<Topic>>()
    internal val topics: LiveData<List<Topic>> = _topics

    private val _state = MutableLiveData<RequestStatus>()
    internal val state: LiveData<RequestStatus> = _state

    init {
        getAllTopics()
    }

    private fun getAllTopics() {
        viewModelScope.launch {
            topicRepository.getAllTopics(
                onInit = { _state.postValue(RequestStatus.LOADING) },
                onError = { _state.postValue(RequestStatus.ERROR) },
                onSuccess = { _state.postValue(RequestStatus.DONE) }
            ).collectLatest { listOfTopics ->
                _topics.postValue(listOfTopics)
            }
        }
    }

}
