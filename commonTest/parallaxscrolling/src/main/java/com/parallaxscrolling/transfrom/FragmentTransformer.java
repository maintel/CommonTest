package com.parallaxscrolling.transfrom;


import android.view.View;

import com.parallaxscrolling.R;
import com.parallaxscrolling.fragmentBuilder.PageFragment;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @author jieyu.chen
 * @date 2018/9/11
 */
public class FragmentTransformer implements ViewPager.PageTransformer {

    private ITransformerCallBack callBack;

    public FragmentTransformer(ITransformerCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        // 操作View
        Object obj = page.getTag(R.id.st_page_fragment);
        if (obj instanceof PageFragment) {
            ((PageFragment) obj).transformPage(page.getWidth(), position);
        }
        callBack.transformPage(page, position);
    }
}
