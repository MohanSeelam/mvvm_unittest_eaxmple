package com.android.mvvmunittesteaxmple.presentation.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mvvmunittesteaxmple.core.UiModel
import com.android.mvvmunittesteaxmple.core.applyUiModel
import com.android.mvvmunittesteaxmple.data.repository.PostRepository
import com.android.mvvmunittesteaxmple.domain.model.Post
import com.android.mvvmunittesteaxmple.domain.repository.PostApiInterface
import com.android.mvvmunittesteaxmple.utils.NetworkHandler
import com.android.mvvmunittesteaxmple.utils.UnitEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(
    val postApiInterface: PostApiInterface,
    val repository: PostRepository,
    val networkHandler: NetworkHandler
) : ViewModel() {

    val postsData = MutableLiveData<UiModel<List<Post>>>()
  private  val _postsLiveData = MutableLiveData<UnitEvent<PostDataEvent>>()
    val postsLiveData :LiveData<UnitEvent<PostDataEvent>> =  _postsLiveData

    fun getPosts() {
        viewModelScope.launch {
            postApiInterface.getPosts()
                .applyUiModel()
                .collect { postsData.value = it }
        }


    }

    fun getPosts1() =
        viewModelScope.launch(Dispatchers.Main) {
            _postsLiveData.value = UnitEvent(PostDataEvent.Loading)
            try {
                val response:List<Post> = repository.getPosts()
                if(response.size>0){
                   // val apiResponse = response.result!!
                    _postsLiveData.value = UnitEvent(PostDataEvent.PostDataSuccess(response))
                }else{
                    _postsLiveData.value = UnitEvent(PostDataEvent.PostDataAPIError("Error"))
                }

            }catch (e:java.lang.Exception){
               _postsLiveData.value = UnitEvent(getPostViewStateExceptionState(e))
            }

        }





    private fun getPostViewStateExceptionState(e:java.lang.Exception):PostDataEvent{
        return if(!networkHandler.isInternetAvailable())  PostDataEvent.NoInternetConnection  else PostDataEvent.PostDataException(e)
    }


    companion object {
        private val TAG = PostsViewModel::class.java.name
    }

}