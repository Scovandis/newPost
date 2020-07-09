package com.example.newpost.ui.post

import android.view.View

import androidx.lifecycle.MutableLiveData
import com.example.newpost.R
import com.example.newpost.base.BaseViewModel
import com.example.newpost.model.Post
import com.example.newpost.model.PostDao
import com.example.newpost.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao): BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    val postListAdapter: PostListAdapter = PostListAdapter()

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPost() }

    init{
        loadPost()
    }
    private fun loadPost(){
        subscription = Observable.fromCallable{postDao.all}
            .concatMap {
                dbPostList ->
                if (dbPostList.isEmpty())
                    postApi.getPost().concatMap {
                        apiPostList -> postDao.selectAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{onRetrievePostListStart()}
            .doOnTerminate { onRetrivePostListFinis() }
            .subscribe (
                {result -> onRetrivePostListSucces(result)},
                {onRetrivePostListOnError()}
            )

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


    private fun onRetrivePostListOnError() {
        errorMessage.value = R.string.post_error
    }

    private fun onRetrivePostListSucces(postList: List<Post>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrivePostListFinis() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }
}