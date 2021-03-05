package maintel.commontest.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_scroll_top_tab.*
import maintel.commontest.R
import maintel.commontest.adapter.TestItemAdapter
import maintel.commontest.view.MyAppBarViewBehavior
import maintel.commontest.view.ViewPagerAdapter

/**
 * 滑动到顶部悬停 tab 的 布局测试
 * @author jieyu.chen
 * @date 2021/3/5
 */
class ScrollTopTabActivity : FragmentActivity() {

    private var mViewPagerAdapter: ViewPagerAdapter? = null

    private var recyList: MutableList<RecyclerView> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_top_tab)
//        val dataList = mutableListOf<String>()
//        for (i in 0..100) {
//            dataList.add("第{$i}条")
//        }
//        recy_ottom_ist.layoutManager = LinearLayoutManager(this)
//        recy_ottom_ist.adapter = TestItemAdapter(dataList, this)

        initView()
    }

    private fun initView() {
//        headerRecy.isNestedScrollingEnabled = false
//        headerRecy.setHasFixedSize(true)
//        //解决数据加载完成后, 没有停留在顶部的问题
//        headerRecy.isFocusable = false
//        setRecycleViewData(headerRecy, 40)

        for (i in 0..4) {
            val recyclerView = LayoutInflater.from(this).inflate(R.layout.item_recy, null) as RecyclerView

            if (i == 0) {
                setRecycleViewData(recyclerView)
            }
            recyList.add(recyclerView)
        }
        mViewPagerAdapter = ViewPagerAdapter(recyList as List<View>?)
        viewPager.adapter = mViewPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                val recyclerView = (viewPager.adapter as ViewPagerAdapter).getItemByPoint(viewPager.currentItem) as RecyclerView

                if (recyclerView.layoutManager == null) {
                    val dataList = mutableListOf<String>()
                    for (index in 0..100) {
                        dataList.add("第${position}页第${index}条")
                    }
                    recyclerView.layoutManager = LinearLayoutManager(this@ScrollTopTabActivity)
                    recyclerView.adapter = TestItemAdapter(dataList, this@ScrollTopTabActivity)
                }
            }

        })
        ((titleLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior as MyAppBarViewBehavior).onHeaderScrollTop = {
            println("滑动到顶部了")
            recyList.forEachIndexed { index, recycle ->
                if (index != viewPager.currentItem) {
                    recycle.scrollToPosition(0)
                }
            }
        }
    }

    private fun setRecycleViewData(recyclerView: RecyclerView, max: Int = 100) {
        val dataList = mutableListOf<String>()
        for (index in 0..max) {
            dataList.add("第1页第${index}条")
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TestItemAdapter(dataList, this)
    }

}

