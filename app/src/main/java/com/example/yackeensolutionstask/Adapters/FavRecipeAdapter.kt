package com.example.yackeensolutionstask.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.yackeensolutionstask.Model.RecipePojoItem
import com.example.yackeensolutionstask.R
import com.example.yackeensolutionstask.Room.RecipeDataBase
import com.example.yackeensolutionstask.Room.RecipeFavorite
import com.example.yackeensolutionstask.UI.Details
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.receipe_item.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavRecipeAdapter(
    private val context: Context,
    private var RecipesList: List<RecipeFavorite>

) :
    RecyclerView.Adapter<FavRecipeAdapter.RecipeViewHolder>() {

    private lateinit var db: RecipeDataBase


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.receipe_item,
            parent, false
        )
        db = Room.databaseBuilder(context, RecipeDataBase::class.java, "RecipeDatabase").build()

        return RecipeViewHolder(
            itemView
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = RecipesList[position]

        if (currentRecipe.name.equals(""))
            holder.recipeName.text = "name not available"
        else
            holder.recipeName.text = currentRecipe.name

        if (currentRecipe.headline.equals(""))
            holder.recipeHeadline.text = "headline not available"
        else
            holder.recipeHeadline.text = currentRecipe.headline


        if (currentRecipe.calories.equals(""))
            holder.calories.text = "not available"
        else
            holder.calories.text = currentRecipe.calories

        holder.recipeDifficulty.text = "Difficulty : " + currentRecipe.difficulty.toString()

        Glide.with(context).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
        )
            .load(currentRecipe.image)
            .into(holder.recipeImage)


        holder.itemView.setOnClickListener {
           Toast.makeText(context,"You Clicked "+currentRecipe.name,Toast.LENGTH_SHORT).show()
        }


        holder.favoriteImage.visibility=View.INVISIBLE
    }


    override fun getItemCount() = RecipesList.size

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeImage: CircleImageView = itemView.recipe_image
        val favoriteImage: ImageView = itemView.favorite
        val recipeName: TextView = itemView.recipe_name
        val recipeHeadline: TextView = itemView.recipe_headline
        val calories: TextView = itemView.recipe_calories
        val recipeDifficulty: TextView = itemView.recipe_difficulty


    }
}