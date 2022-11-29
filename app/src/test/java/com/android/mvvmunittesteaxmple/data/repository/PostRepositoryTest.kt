package com.android.mvvmunittesteaxmple.data.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.mvvmunittesteaxmple.domain.model.Post
import com.android.mvvmunittesteaxmple.domain.repository.PostApiInterface
import com.android.mvvmunittesteaxmple.presentation.posts.PostDataEvent
import com.android.mvvmunittesteaxmple.util.TestDispatcherProvider
import com.android.mvvmunittesteaxmple.util.TestMainCoroutineScopeRule
import com.android.mvvmunittesteaxmple.utils.NetworkHandler
import com.android.mvvmunittesteaxmple.utils.UnitEvent
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*


@ExperimentalCoroutinesApi
class PostsRepositoryTest {

    /**
     * A JUnit Test Rule that swaps the background executor used by the Architecture Components with a different one which executes each task synchronously.
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testMainCoroutineScopeRule: TestMainCoroutineScopeRule = TestMainCoroutineScopeRule()

    @Mock
    lateinit var postApiInterface: PostApiInterface

    private var testDispatcherProvider =
        TestDispatcherProvider(testMainCoroutineScopeRule.testDispatcher)


    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var networkHandler: NetworkHandler


    @Mock
    private lateinit var postObserver: Observer<UnitEvent<PostDataEvent>>

    @Captor
    private lateinit var postArgumentCaptor: ArgumentCaptor<UnitEvent<PostDataEvent>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        postRepository = PostRepository(postApiInterface, testDispatcherProvider)
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
    }

    @Test
    fun success_post() = runTest {

        val postResponse = getPostDummyResponse()

        Mockito.`when`(postApiInterface.getPosts1()).thenReturn(postResponse)
        val result = postApiInterface.getPosts1()
        TestCase.assertEquals(postResponse, result)

    }


//    @Test
//    fun post_other_exception() = runBlockingTest {
//
//        val expectedResponse= Exception()
//
//        Mockito.`when`(postApiInterface.getPosts1()).thenThrow(expectedResponse)
//
//    }

//    @Test
//    fun post_API_errors() = runBlockingTest {
//
//
//        Mockito.`when`(networkHandler.isInternetAvailable()).thenReturn(true)
//        Mockito.`when`(postRepository.getPosts()).thenReturn()
//        viewModel.postsLiveData.observeForever(postObserver)
//        viewModel.getPosts1()
//        Mockito.verify(postObserver,Mockito.times(2)).onChanged(
//            postArgumentCaptor.capture()
//        )
//        val values = postArgumentCaptor.allValues
//        TestCase.assertEquals(PostDataEvent.Loading,values[0].getContentIfNotHandled())
//        TestCase.assertEquals(,values[1].getContentIfNotHandled())
//
//        viewModel.postsLiveData.removeObserver(postObserver)
//    }

    companion object {

        private fun getPostDummyResponse(): List<Post> {
            return listOf<Post>(Post(1, 1, "Header", "J unit testing using Mockito"))
        }
    }

}