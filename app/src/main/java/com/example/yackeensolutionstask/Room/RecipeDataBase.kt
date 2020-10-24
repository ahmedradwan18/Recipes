package com.example.yackeensolutionstask.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [RecipeFavorite::class],
    version = 1
)
abstract class RecipeDataBase :RoomDatabase(){
    abstract fun getRecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var instance: RecipeDataBase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance=it
            }
        }

        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            RecipeDataBase::class.java,
            "RecipeDatabase"
        ).build()
    }
}