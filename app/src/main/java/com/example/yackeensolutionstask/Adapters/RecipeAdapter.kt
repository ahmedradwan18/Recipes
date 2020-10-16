package com.example.yackeensolutionstask.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.yackeensolutionstask.R
import com.example.yackeensolutionstask.Model.RecipePojoItem
import com.example.yackeensolutionstask.UI.Details
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.receipe_item.view.*

class RecipeAdapter(
    private val context: Context,
    private var RecipesList: List<RecipePojoItem>

) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.receipe_item,
            parent, false
        )

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
            holder.calories.text = "calories not available"
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
            val intent = Intent(context, Details::class.java)
            intent.putExtra("thumb", currentRecipe.thumb)
            intent.putExtra("name", currentRecipe.name)
            intent.putExtra("description", currentRecipe.description)
            intent.putExtra("calories", currentRecipe.calories)
            intent.putExtra("carbos", currentRecipe.carbos)
            intent.putExtra("fats", currentRecipe.fats)
            intent.putExtra("proteins", currentRecipe.proteins)
            intent.putExtra("time", currentRecipe.time)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }


    override fun getItemCount() = RecipesList.size

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeImage: CircleImageView = itemView.recipe_image
        val recipeName: TextView = itemView.recipe_name
        val recipeHeadline: TextView = itemView.recipe_headline
        val calories: TextView = itemView.recipe_calories
        val recipeDifficulty: TextView = itemView.recipe_difficulty


    }


}