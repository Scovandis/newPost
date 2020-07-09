package com.example.newpost.model.appdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newpost.model.Post
import com.example.newpost.model.PostDao

@Database(entities = arrayOf(Post::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun postDao(): PostDao
}