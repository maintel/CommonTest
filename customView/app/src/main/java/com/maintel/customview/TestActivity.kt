package com.maintel.customview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.PointF
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationSet
import com.alibaba.android.arouter.facade.annotation.Route
import com.maintel.customview.anim.PointEvaluator
import com.maintel.customview.tabTest.TabLayoutTestActivity
import com.maintel.customview.view.MoveImageView
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @author jieyu.chen
 * @date 2018/8/2
 */
@Route(path = "/test/testActivity")
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageview = findViewById(R.id.iv_test)

        val imageAnim = ObjectAnimator.ofFloat(imageview!!, "rotation", 0f, 30f, 0f, -30f, 0f, 30f, 0f)

        imageAnim.duration = 500
        imageAnim.repeatMode = ObjectAnimator.RESTART
        imageAnim.repeatCount = -1
        imageAnim.start()

        findViewById(R.id.fl_parent).bringToFront()


        btn_tab_layout.setOnClickListener {
            startActivity(Intent(this, TabLayoutTestActivity::class.java))
        }


    }


    private fun test(poi: Int) {
        val layout = tv_textview_test.getLayout();
        val bound = Rect();
        val line = layout.getLineForOffset(poi);
        layout.getLineBounds(line, bound);
        println(bound)
        val yAxisTop = bound.top;//字符顶部y坐标
        val yAxisBottom = bound.bottom;//字符底部y坐标
        val xAxisLeft = layout.getPrimaryHorizontal(poi);//字符左边x坐标
        val xAxisRight = layout.getSecondaryHorizontal(poi);//字符右边x坐标

        println("$yAxisTop::$yAxisBottom::$xAxisLeft::$xAxisRight")
    }

    override fun onResume() {
        super.onResume()

        val xxxImg = findViewById(R.id.xxx) as MoveImageView
        val xxx2 = findViewById(R.id.xxx2) as MoveImageView
        xxxImg.post {
            animStart(xxxImg, 0, 0)
            animStart(xxx2, 500, 1)
//            xxxMoveAnim.duration = 1000
//            xxxMoveAnim.start()
        }

        tv_textview_test.post {
            val location = intArrayOf(0, 0)
            tv_textview_test.getLocationInWindow(location)
            println("${location[0]}::${location[1]}")
            tv_textview_test.getLocationOnScreen(location)
            println("${location[0]}::${location[1]}")
        }

        tv_textview_test.post {
            test(3)
            test(5)
        }


    }

    private fun animStart(xxxImg: MoveImageView, delay: Long, direction: Int) {
        val animSet = AnimatorSet()
        val startPointF: PointF = PointF(xxxImg.x, xxxImg.y)
        val endPointF: PointF = PointF(xxxImg.x, xxxImg.y - 50)
        val xxxMoveAnim = ObjectAnimator.ofObject(xxxImg, "mPointF", PointEvaluator(direction), startPointF, endPointF)
        val xxxSxAnim = ObjectAnimator.ofFloat(xxxImg, "scaleX", 0.5f, 1.5f)
        val xxxSyAnim = ObjectAnimator.ofFloat(xxxImg, "scaleY", 0.5f, 1.5f)
        val xxxApAnim = ObjectAnimator.ofFloat(xxxImg, "alpha", 1f, 0.4f)
        animSet.duration = 1000
        xxxMoveAnim.repeatMode = ObjectAnimator.RESTART
        xxxMoveAnim.repeatCount = -1
        xxxSxAnim.repeatMode = ObjectAnimator.RESTART
        xxxSxAnim.repeatCount = -1
        xxxSyAnim.repeatMode = ObjectAnimator.RESTART
        xxxSyAnim.repeatCount = -1
        xxxApAnim.repeatMode = ObjectAnimator.RESTART
        xxxApAnim.repeatCount = -1
        animSet.playTogether(xxxMoveAnim, xxxSxAnim, xxxSyAnim, xxxApAnim)
        animSet.startDelay = delay
        animSet.start()
    }

}