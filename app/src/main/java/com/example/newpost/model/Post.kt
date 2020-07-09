package com.example.newpost.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    val userId: String,
    @field:PrimaryKey
    val id: String,
    val title: String,
    val body: String
)