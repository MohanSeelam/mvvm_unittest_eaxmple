package com.android.mvvmunittesteaxmple.domain.repository

import com.android.mvvmunittesteaxmple.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostApiInterface {

    fun getPosts(): Flow<List<Post>>
   suspend fun getPosts1(): List<Post>
}