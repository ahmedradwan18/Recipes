package com.example.yackeensolutionstask.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {

    @Insert
     fun addRecipe(recipe: RecipeFavorite)

    @Query("SELECT * FROM recipefavorite")
     fun getAllRecipes(): MutableList<RecipeFavorite>

    @Query("DELETE from recipefavorite")
    fun deleteAll()
}