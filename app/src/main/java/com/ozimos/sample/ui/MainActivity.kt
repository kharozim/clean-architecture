package com.ozimos.sample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozimos.sample.databinding.ActivityMainBinding
import com.ozimos.sample.ui.picsum.PicsumActivity
import com.ozimos.sample.ui.picsum.hilt.PicsumActivityHilt
import com.ozimos.sample.ui.picsum.paging3.PicsumActivityPaging3
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            btnList.setOnClickListener {
                startActivity(Intent(this@MainActivity, PicsumActivity::class.java))
            }
            btnListPaging3.setOnClickListener {
                startActivity(Intent(this@MainActivity, PicsumActivityPaging3::class.java))
            }

            btnListPaging3Hilt.setOnClickListener {
                startActivity(Intent(this@MainActivity, PicsumActivityHilt::class.java))
            }
        }

    }
}