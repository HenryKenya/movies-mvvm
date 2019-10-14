package com.example.moviesmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesmvvm.R
import com.example.moviesmvvm.ui.single_movie_details.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_movie.setOnClickListener {
            val intent = Intent(this, SingleMovie::class.java)
            intent.putExtra("movieId", 475557)
            this.startActivity(intent)
        }
    }
}
