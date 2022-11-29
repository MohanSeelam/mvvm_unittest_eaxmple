package com.android.mvvmunittesteaxmple.presentation.posts

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.android.mvvmunittesteaxmple.R
import com.android.mvvmunittesteaxmple.databinding.ActivityMainBinding
import com.android.mvvmunittesteaxmple.di.ServiceFailureException
import com.android.mvvmunittesteaxmple.domain.model.Post
import com.android.mvvmunittesteaxmple.utils.observeEvent
import com.example.reusable.card.UICard
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import java.net.ServerSocket
import java.net.SocketTimeoutException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val postViewModel: PostsViewModel by viewModels()
    private val dialog: Dialog by lazy {
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
    }

    open fun isDialogShowing(): Boolean = dialog.isShowing
    private lateinit var section: Section
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityMainBinding.root)

        renderView()
        postAPICalling()

    }

    private fun postAPICalling() {
        postViewModel.getPosts1()

    }


    private fun renderView() {
        postViewModel.postsLiveData.observeEvent(this, onEventUnHandledContent = {
            when (it) {
                is PostDataEvent.Loading -> {
                    if (!isDialogShowing())
                        showLoadingDialog()
                }
                is PostDataEvent.PostDataSuccess -> {
                    hideLoadingDialog()
                    showLinearLayout(it.response)
                }
                is PostDataEvent.NoInternetConnection -> {
                    hideLoadingDialog()
                    Toast.makeText(this@MainActivity, "Internet not", Toast.LENGTH_LONG).show()
                }
                is PostDataEvent.PostDataException -> {
                    hideLoadingDialog()
                    handleExceptionState(it.exception)
                }
                else -> {
                    Toast.makeText(this@MainActivity, "message", Toast.LENGTH_LONG).show()

                }
            }

        })


    }

    private fun showLinearLayout(post: List<Post>) = activityMainBinding.run {
        llMain.addPostItem(post)
       // renderingUICard(post)
    }

//    private fun renderingUICard(item: List<Post>){
//        item.first().apply {
//            llMain.primaryText = this.body
//            llMain.secondaryText = this.title
//            llMain.setSafeOnClickListener{
//                Toast.makeText(this@MainActivity,"Main",Toast.LENGTH_LONG).show()
//            }
//        }
//    }

    private fun LinearLayoutCompat.addPostItem(item: List<Post>) {
        orientation = LinearLayoutCompat.VERTICAL
        visibility= View.VISIBLE
        item.forEach { post ->
            addView(
                UICard(this@MainActivity).apply {
                    primaryText = post.title
                    secondaryText = post.body
                    showButton = true
                    buttonText ="OK"
//                    context?.let {
//                        leftImage = ContextCompat.getDrawable(
//                            it,
//                            post.id
//                        )
//                    }
                    setTextClickListener{
                        Toast.makeText(this@MainActivity,"Main",Toast.LENGTH_LONG).show()

                    }
//                    setSafeOnClickListener {
//                        Toast.makeText(this@MainActivity,"Main",Toast.LENGTH_LONG).show()
//                    }
                })
        }
    }

    private fun handleExceptionState(exception: Exception) {
        val message = when (exception) {
            is SocketTimeoutException -> ""
            is ServerSocket -> ""
            else -> " "
        }
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()

    }

    fun showLoadingDialog() {
        hideLoadingDialog()
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.activity_detail)
        if (!isFinishing)
            dialog.show()
    }

    private fun hideLoadingDialog() {
        if (!isFinishing && dialog.isShowing)
            dialog.dismiss()
    }

    fun showOnFailurePopup(t: Throwable) {
        val message: String
        if (t is ServiceFailureException) {
            message = t.errorMessage
        } else {
            message = SERVER_ERROR_OCCURRED
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {
        private val TAG = PostsActivity::class.java.name
        private val SERVER_ERROR_OCCURRED =
            "Something went wrong while getting response from server. Please try again.;"
    }
}