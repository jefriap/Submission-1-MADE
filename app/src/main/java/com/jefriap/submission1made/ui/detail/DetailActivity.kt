package com.jefriap.submission1made.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jefriap.submission1made.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    companion object {
        const val EXTRA_ID = "extra_id"
        const val TYPE = "type"
    }
}