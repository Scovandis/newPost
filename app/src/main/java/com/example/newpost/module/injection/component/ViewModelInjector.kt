package com.example.newpost.module.injection.component

import com.example.newpost.module.injection.module.NetworkModule
import com.example.newpost.ui.post.PostListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {


    fun injec(postListViewModel: PostListViewModel)

    @Component.Builder
    interface Builder{
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}