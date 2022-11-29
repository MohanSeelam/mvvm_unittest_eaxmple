package com.android.mvvmunittesteaxmple.data.source.remote

import com.android.mvvmunittesteaxmple.di.ExcludeWrapper
import com.android.mvvmunittesteaxmple.domain.model.Post


import retrofit2.http.GET

interface ApiService {

    @ExcludeWrapper
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @ExcludeWrapper
    @GET("/posts")
    suspend fun getPosts1(): List<Post>

}