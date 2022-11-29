package com.android.mvvmunittesteaxmple.data.repository


import com.android.mvvmunittesteaxmple.data.source.remote.ApiService
import com.android.mvvmunittesteaxmple.domain.model.Post
import com.android.mvvmunittesteaxmple.domain.repository.PostApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImp @Inject constructor(
    private val apiService: ApiService
) : PostApiInterface {

    override fun getPosts(): Flow<List<Post>> {
        return flow {
            val result = apiService.getPosts()
            emit(result)
        }.map { it }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPosts1(): List<Post> {


       return apiService.getPosts1()
    }


}