package com.example.yackeensolutionstask.UI

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yackeensolutionstask.*
import com.example.yackeensolutionstask.Adapters.RecipeAdapter
import com.example.yackeensolutionstask.ApiInterface.RecipeApiInterface
import com.example.yackeensolutionstask.Model.RecipePojoItem
import kotlinx.android.synthetic.main.home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : AppCompatActivity() {

    lateinit var adapter: RecipeAdapter
    private val networkMonitor =
        NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        // setup recyclerView
        receipes_Recycler.layoutManager = LinearLayoutManager(this)
        receipes_Recycler.setHasFixedSize(true)

        // view progress till the data be loaded
        progressBar.max = 1000
        val currentProgress = 900
        ObjectAnimator.ofInt(progressBar, "Progress", currentProgress)
            .setDuration(2000)
            .start()

        // handle internet connection
        checkInternetConnection()

        // build retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hf-android-app.s3-eu-west-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface: RecipeApiInterface = retrofit.create(
            RecipeApiInterface::class.java)
        val call: Call<List<RecipePojoItem>> = apiInterface.getRecipes()
        fetchData(call)

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

    private fun fetchData(call: Call<List<RecipePojoItem>>) {
        call.enqueue(object : Callback<List<RecipePojoItem>> {
            override fun onResponse(
                call: Call<List<RecipePojoItem>>,
                response: Response<List<RecipePojoItem>>
            ) {

                adapter =
                    RecipeAdapter(
                        applicationContext,
                        response.body()!!
                    )
                receipes_Recycler.adapter = adapter
                progressBar.setVisibility(View.INVISIBLE)

            }

            override fun onFailure(call: Call<List<RecipePojoItem>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }
}