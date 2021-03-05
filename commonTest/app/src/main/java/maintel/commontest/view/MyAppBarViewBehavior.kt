package maintel.commontest.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

/**
 *
 * @author jieyu.chen
 * @date 2021/3/5
 */
class MyAppBarViewBehavior(context: Context, attrs: AttributeSet) : AppBarLayout.Behavior(context, attrs) {

    var lastDy = 0

    var onHeaderScrollTop: (() -> Unit)? = null


    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        val started = super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type)
        return started
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: AppBarLayout, ev: MotionEvent): Boolean {
        return super.onTouchEvent(parent, child, ev)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: AppBarLayout, dependency: View): Boolean {
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: AppBarLayout, dependency: View): Boolean {
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: AppBarLayout, dependency: View) {
        super.onDependentViewRemoved(parent, child, dependency)
    }

    override fun onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams()
    }

    override fun onMeasureChild(parent: CoordinatorLayout, child: AppBarLayout, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }

    override fun onLayoutChild(parent: CoordinatorLayout, abl: AppBarLayout, layoutDirection: Int): Boolean {
        return super.onLayoutChild(parent, abl, layoutDirection)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, abl: AppBarLayout, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {

        if (lastDy >= 0 && dyUnconsumed < 0) {
            lastDy = dyUnconsumed
            // 说明是从底部划上来了
            onHeaderScrollTop?.invoke()
        } else if (dyUnconsumed == 0 && lastDy != 0) {
            lastDy = 0
        }
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
    }

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, velocityX: Float, velocityY: Float): Boolean {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

}