package com.maintel.customview.ringProgress

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.maintel.customview.R

/**
 *
 * @author jieyu.chen
 * @date 2018/12/24
 */
@Route(path = "/test/ringProgress")
class RingProgressActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring_pro)
    }

}