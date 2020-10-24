package com.example.yackeensolutionstask.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeFavorite(
    val name: String,
    val calories: String,
    val difficulty: Int,
    val headline: String,
    val image: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}