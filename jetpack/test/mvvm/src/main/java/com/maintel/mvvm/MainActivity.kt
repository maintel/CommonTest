package com.maintel.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.maintel.mvvm.bean.Student
import com.maintel.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 这样写
//        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        // 也可以这样写
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.student = Student("老王", 30)
    }
}
