package com.yundom.kache.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yundom.kache.Builder
import com.yundom.kache.Kache
import com.yundom.kache.config.LRU

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cache: Kache<Int, Int> = Builder.build {
            policy = LRU
        }
    }
}
