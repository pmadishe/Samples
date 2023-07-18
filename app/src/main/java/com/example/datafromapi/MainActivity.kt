package com.example.datafromapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(CatApiService::class.java)
    }

    lateinit var rtv : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rtv = findViewById(R.id.rtv)

        getCatImageResponse()
    }

    private fun getCatImageResponse() {
        val call = apiService.searchImages(1, "full")
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    rtv.text = response.body()
                } else {
                    Log.e("MainActivity", "Failed to get onResponse: , \n${response.errorBody()?.string().orEmpty()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MainActivity", "Failed to get Results: ", t )
            }

        })
    }
}