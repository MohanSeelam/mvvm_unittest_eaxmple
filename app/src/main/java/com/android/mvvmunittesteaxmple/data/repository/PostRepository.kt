package com.android.mvvmunittesteaxmple.data.repository

import com.android.mvvmunittesteaxmple.core.DispatcherProvider
import com.android.mvvmunittesteaxmple.domain.repository.PostApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.jvm.Throws

class PostRepository @Inject constructor(private val postApiInterface: PostApiInterface,
                                         private val dispatcherProvider: DispatcherProvider
) {
    @Throws(Exception::class)
     suspend fun getPosts()=
        withContext(dispatcherProvider.io()) {
            postApiInterface.getPosts1()
        }
}