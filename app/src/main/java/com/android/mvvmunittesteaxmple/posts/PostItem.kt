package com.android.mvvmunittesteaxmple.presentation.posts

import android.content.Intent
import com.android.mvvmunittesteaxmple.R
import com.android.mvvmunittesteaxmple.databinding.HolderPostBinding
import com.android.mvvmunittesteaxmple.domain.model.Post
import com.android.mvvmunittesteaxmple.utils.setSafeOnClickListener
import com.xwray.groupie.databinding.BindableItem

class PostItem(val post: Post) : BindableItem<HolderPostBinding>() {

    override fun getLayout(): Int {
        return R.layout.holder_post
    }


    override fun bind(viewBinding: HolderPostBinding, position: Int) {
        with(viewBinding) {
            postTitleTextView.text = post.title
            postBodyTextView.text = post.body
            postBodyTextView.setSafeOnClickListener{
                val intent = Intent(viewBinding.root.context,MainActivity::class.java)
                viewBinding.root.context.startActivity(intent)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostItem

        if (post != other.post) return false

        return true
    }

    override fun hashCode(): Int {
        return post.hashCode()
    }
}