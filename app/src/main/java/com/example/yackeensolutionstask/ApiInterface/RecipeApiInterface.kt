package com.example.yackeensolutionstask.ApiInterface

import com.example.yackeensolutionstask.Model.RecipePojoItem
import retrofit2.Call
import retrofit2.http.GET

interface RecipeApiInterface {

    @GET("android-test/recipes.json")
    fun getRecipes(): Call<MutableList<RecipePojoItem>>

}