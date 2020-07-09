package com.example.newpost.base

import androidx.lifecycle.ViewModel
import com.example.newpost.module.injection.module.NetworkModule
import com.example.newpost.module.injection.component.ViewModelInjector
import com.example.newpost.module.injection.component.DaggerViewModelInjector


import com.example.newpost.ui.post.PostListViewModel

abstract class BaseViewModel: ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject(){
        when(this){
            is PostListViewModel -> injector.injec(this)
        }
    }
}