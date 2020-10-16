package com.example.yackeensolutionstask.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.yackeensolutionstask.ConnectionType
import com.example.yackeensolutionstask.NetworkMonitorUtil
import com.example.yackeensolutionstask.R

class Details : AppCompatActivity() {
    private val networkMonitor =
        NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // handling internet connection
        checkInternetConnection()

        // getting data from adapter
        val thumb: String = intent.getStringExtra("thumb")
        val name: String = intent.getStringExtra("name")
        val description: String = intent.getStringExtra("description")
        val calories: String = intent.getStringExtra("calories")
        val carbos: String = intent.getStringExtra("carbos")
        val fats: String = intent.getStringExtra("fats")
        val proteins: String = intent.getStringExtra("proteins")
        val time: String = intent.getStringExtra("time")


        // declare views
        val thumbTV = findViewById<ImageView>(R.id.details_thumb) as ImageView
        val nameTV = findViewById<TextView>(R.id.details_name) as TextView
        val descriptionTV = findViewById<TextView>(R.id.details_description) as TextView
        val caloriesTV = findViewById<TextView>(R.id.details_calories) as TextView
        val carbosTV = findViewById<TextView>(R.id.details_carbos) as TextView
        val fatsTV = findViewById<TextView>(R.id.details_fats) as TextView
        val proteinsTV = findViewById<TextView>(R.id.details_proteins) as TextView
        val timeTV = findViewById<TextView>(R.id.details_time) as TextView


        // assign data into views and set default values if data is null
        if (name.equals(""))
            nameTV.text = "name not available"
        else
            nameTV.text = name

        if (description.equals(""))
            descriptionTV.text = "description not available"
        else
            descriptionTV.text = description

        if (calories.equals(""))
            caloriesTV.text = "calories not available"
        else
            caloriesTV.text = "Calories : "+calories

        if (carbos.equals(""))
            carbosTV.text = "carbos not available"
        else
            carbosTV.text = "Carbos : "+carbos

        if (fats.equals(""))
            fatsTV.text = "fats not available"
        else
            fatsTV.text = "Fats : "+fats

        if (proteins.equals(""))
            proteinsTV.text = "proteins not available"
        else
            proteinsTV.text = "Proteins : "+proteins

        if (fats.equals(""))
            timeTV.text = "time not available"
        else
            timeTV.text = time

        Glide.with(this)
            .applyDefaultRequestOptions( RequestOptions ()
                    .placeholder(R.drawable.image)
                    .error(R.drawable.image)
            )
            .load(thumb)
            .into(thumbTV)
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
}