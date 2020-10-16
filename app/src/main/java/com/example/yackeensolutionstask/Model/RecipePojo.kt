package com.example.yackeensolutionstask.Model

class RecipePojo : ArrayList<RecipePojoItem>()

data class RecipePojoItem(
    val calories: String,
    val carbos: String,
    val country: String,
    val description: String,
    val difficulty: Int,
    val fats: String,
    val headline: String,
    val id: String,
    val image: String,
    val name: String,
    val proteins: String,
    val thumb: String,
    val time: String
)