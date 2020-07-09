package com.example.newpost.network

import retrofit2.http.GET
import com.example.newpost.model.Post
import io.reactivex.Observable

interface PostApi {
    @GET("/posts")
    fun getPost(): Observable<List<Post>>
}