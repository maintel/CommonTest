package maintel.commontest.layout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_layout_main.*
import maintel.commontest.R

/**
 *
 * @author jieyu.chen
 * @date 2021/3/5
 */
class LayoutMainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_main)
        initView()
    }

    private fun initView() {
        btnScrollTopTab.setOnClickListener {
            startActivity(Intent(this, ScrollTopTabActivity::class.java))
        }
    }
}