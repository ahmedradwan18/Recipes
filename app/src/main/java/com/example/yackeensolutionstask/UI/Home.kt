package com.example.yackeensolutionstask.UI

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.yackeensolutionstask.Adapters.FavRecipeAdapter
import com.example.yackeensolutionstask.Adapters.RecipeAdapter
import com.example.yackeensolutionstask.ApiInterface.RecipeApiInterface
import com.example.yackeensolutionstask.ConnectionType
import com.example.yackeensolutionstask.Model.RecipePojoItem
import com.example.yackeensolutionstask.NetworkMonitorUtil
import com.example.yackeensolutionstask.R
import com.example.yackeensolutionstask.Room.RecipeDataBase
import com.example.yackeensolutionstask.Room.RecipeFavorite
import kotlinx.android.synthetic.main.home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Home : AppCompatActivity() {
    private lateinit var db: RecipeDataBase
    lateinit var favList: MutableList<RecipeFavorite>
    lateinit var recipesList: MutableList<RecipePojoItem>
    lateinit var adapter: RecipeAdapter
    lateinit var favAdapter: FavRecipeAdapter

    private val networkMonitor =
        NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        // setup recyclerView
        receipes_Recycler.layoutManager = LinearLayoutManager(this)
        receipes_Recycler.setHasFixedSize(true)

        db = Room.databaseBuilder(applicationContext, RecipeDataBase::class.java, "RecipeDatabase")
            .build()
        favList = ArrayList()

        fab_btn.visibility = View.INVISIBLE

        fab_btn.setOnClickListener {
            recipesList.clear()
            receipes_Recycler?.adapter?.notifyDataSetChanged()

            displayRecipes()

        }


        // view progress till the data be loaded
        progressBar.max = 1000
        val currentProgress = 900
        ObjectAnimator.ofInt(progressBar, "Progress", currentProgress)
            .setDuration(2000)
            .start()

        // handle internet connection
        checkInternetConnection()

        // build retrofit and fetch data
        buildRetrofit()


    }


    private fun displayRecipes() {

        GlobalScope.launch (Dispatchers.IO){
            favList = RecipeDataBase(this@Home).getRecipeDao().getAllRecipes()

        withContext(Dispatchers.Main){
            favAdapter =
                FavRecipeAdapter(
                    applicationContext, favList
                )
            receipes_Recycler.adapter = favAdapter
            progressBar.setVisibility(View.INVISIBLE)
        }

        }



    }
    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    fun checkInternetConnection() {
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Wifi Connection")
                            }
                            ConnectionType.Cellular -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Cellular Connection")
                            }
                            else -> {
                            }
                        }
                    }
                    false -> {
                        val intent = Intent(this, internetConnection::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun fetchData(call: Call<MutableList<RecipePojoItem>>) {
        GlobalScope.launch (Dispatchers.IO) {

            call.enqueue(object : Callback<MutableList<RecipePojoItem>> {
                override fun onResponse(
                    call: Call<MutableList<RecipePojoItem>>,
                    response: Response<MutableList<RecipePojoItem>>
                ) {
                    recipesList = response.body()!!

                    adapter =
                        RecipeAdapter(
                            applicationContext,
                            recipesList
                        )
                    receipes_Recycler.adapter = adapter
                    progressBar.setVisibility(View.INVISIBLE)
                    fab_btn.visibility = View.VISIBLE

                }

                override fun onFailure(call: Call<MutableList<RecipePojoItem>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()

                }
            })

        }

    }
    fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hf-android-app.s3-eu-west-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface: RecipeApiInterface = retrofit.create(
            RecipeApiInterface::class.java
        )
        val call: Call<MutableList<RecipePojoItem>> = apiInterface.getRecipes()
        fetchData(call)
    }
}