package com.android.mvvmunittesteaxmple.util

import com.android.mvvmunittesteaxmple.core.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider(private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    DispatcherProvider {
    override fun io(): CoroutineDispatcher =
        testDispatcher

    override fun main(): CoroutineDispatcher = testDispatcher

    override fun default(): CoroutineDispatcher = testDispatcher

    override fun unconfined(): CoroutineDispatcher = testDispatcher

}
