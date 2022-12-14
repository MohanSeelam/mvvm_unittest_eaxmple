package com.android.mvvmunittesteaxmple.presentation.posts

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.mvvmunittesteaxmple.R
import com.android.mvvmunittesteaxmple.core.onFailure
import com.android.mvvmunittesteaxmple.core.onSuccess
import com.android.mvvmunittesteaxmple.databinding.ActivityPostsBinding
import com.android.mvvmunittesteaxmple.di.ServiceFailureException
import com.android.mvvmunittesteaxmple.utils.isNetworkAvailable
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {

    private lateinit var activityPostsBinding: ActivityPostsBinding
    private val postViewModel: PostsViewModel by viewModels()
    private lateinit var section: Section

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostsBinding = DataBindingUtil.setContentView(this, R.layout.activity_posts)

        if (isNetworkAvailable()) {
            postViewModel.getPosts()
        } else {
            Toast.makeText(
                this,
                getString(R.string.no_internet_connection),
                LENGTH_SHORT
            ).show()
        }

        setUpRecyclerView()
        renderView()

    }

    private fun setUpRecyclerView() {
        val postAdapter = GroupAdapter<GroupieViewHolder>()
        section = Section()
        postAdapter.add(section)

        activityPostsBinding.postsRecyclerView.adapter = postAdapter

    }

    private fun renderView() {
        with(postViewModel) {

            postsData.observe(this@PostsActivity, Observer {
                toggleProgressBar(it.inProgress)
                it.onFailure {
                    showOnFailurePopup(it)
                }
                it.onSuccess {
                    for (model in it) {
                        section.add(PostItem(model))
                    }
                }

            })
        }

    }

    fun toggleProgressBar(show: Boolean) {
        if (show) {
            activityPostsBinding.postsProgressBar.visibility = View.VISIBLE
        } else {
            activityPostsBinding.postsProgressBar.visibility = GONE
        }
    }

    fun showOnFailurePopup(t: Throwable) {
        val message: String
        if (t is ServiceFailureException) {
            message = t.errorMessage
        } else {
            message = SERVER_ERROR_OCCURRED
        }
        Toast.makeText(this, message, LENGTH_LONG).show()
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
