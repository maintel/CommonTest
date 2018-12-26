package maintel.commontest.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;

/**
 * @author jieyu.chen
 * @date 2018/9/5
 */
@Content(R.layout.activity_anim_test)
public class AnimTestActivity extends BaseActivity {

    ImageView iv17zuoyeOff;
    ImageView iv17zuoyeON;


    RelativeLayout mRl17zuoyeTab;
    private boolean isAniming;


    @Override
    protected void initData() {
        mRl17zuoyeTab = findViewById(R.id.rl_17zuoye_tab);
        iv17zuoyeON = findViewById(R.id.iv_17zuoye_on);
        iv17zuoyeOff = findViewById(R.id.iv_17zuoye_off);
        mRl17zuoyeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start17zuoyeAnim(iv17zuoyeON.getVisibility() == View.VISIBLE);
            }
        });
    }

    /**
     * 开始动画
     *
     * @param isSelect 当前是否是选中
     */
    private void start17zuoyeAnim(boolean isSelect) {
        if (isSelect) {
            AnimatorSet endsetAnimation = new AnimatorSet();
            iv17zuoyeOff.setVisibility(View.VISIBLE);
            ObjectAnimator endscaleXAnim = ObjectAnimator.ofFloat(iv17zuoyeOff, "scaleX", 0.5f, 1.1f, 1f);
            ObjectAnimator endscaleYAnim = ObjectAnimator.ofFloat(iv17zuoyeOff, "scaleY", 0.5f, 1.1f, 1f);
            iv17zuoyeON.setVisibility(View.INVISIBLE);
//            ObjectAnimator endscaleXAnimOn = ObjectAnimator.ofFloat(iv17zuoyeON, "scaleX", 1f, 1.1f, 1f);
//            ObjectAnimator endscaleYAnimon = ObjectAnimator.ofFloat(iv17zuoyeON, "scaleY", 1f, 1.1f, 1f);
            endsetAnimation.setDuration(300);
            endsetAnimation.setInterpolator(new LinearInterpolator());
            endsetAnimation.playTogether(endscaleXAnim, endscaleYAnim);
            endsetAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    isAniming = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isAniming = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    isAniming = false;
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    isAniming = true;
                }
            });
            endsetAnimation.start();
        } else {
            AnimatorSet endsetAnimation = new AnimatorSet();
            iv17zuoyeON.setVisibility(View.VISIBLE);
            ObjectAnimator endscaleXAnim = ObjectAnimator.ofFloat(iv17zuoyeON, "scaleX", 1f, 1.2f, 1f);
            ObjectAnimator endscaleYAnim = ObjectAnimator.ofFloat(iv17zuoyeON, "scaleY", 1f, 1.2f, 1f);
            iv17zuoyeOff.setVisibility(View.INVISIBLE);
//            ObjectAnimator endscaleXAnimOn = ObjectAnimator.ofFloat(iv17zuoyeON, "scaleX", 1f, 1.1f, 1f);
//            ObjectAnimator endscaleYAnimon = ObjectAnimator.ofFloat(iv17zuoyeON, "scaleY", 1f, 1.1f, 1f);
            endsetAnimation.setDuration(300);
            endsetAnimation.setInterpolator(new LinearInterpolator());
            endsetAnimation.playTogether(endscaleXAnim, endscaleYAnim);
            endsetAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    isAniming = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isAniming = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    isAniming = false;
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    isAniming = true;
                }
            });
            endsetAnimation.start();
        }
        isAniming = true;
    }
}
