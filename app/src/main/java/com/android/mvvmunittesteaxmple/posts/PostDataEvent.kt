package com.android.mvvmunittesteaxmple.presentation.posts

import com.android.mvvmunittesteaxmple.domain.model.Post
import java.lang.Exception

sealed class PostDataEvent{
    object NoInternetConnection : PostDataEvent()
    object Loading : PostDataEvent()
    data class PostDataSuccess(val response: List<Post>) : PostDataEvent()
    data class PostDataAPIError(val error: String) : PostDataEvent()
    data class PostDataException(val exception: Exception) : PostDataEvent()
}
