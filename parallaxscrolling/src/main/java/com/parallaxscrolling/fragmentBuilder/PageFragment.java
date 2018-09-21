package com.parallaxscrolling.fragmentBuilder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parallaxscrolling.R;

/**
 * 单个页面 Fragment
 *
 * @author jieyu.chen
 * @date 2018/9/11
 */
public class PageFragment extends Fragment {


    public static PageFragment newInstance(PageOptions provider) {
        return new PageFragment();
    }


    View textView;
    View textView2;
    View textView3;
    View textView4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        view.setTag(R.id.st_page_fragment, this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.tv);
        textView2 = view.findViewById(R.id.tv2);
        textView3 = view.findViewById(R.id.tv3);
        textView4 = view.findViewById(R.id.tv4);

    }

    public void transformPage(int width, float position) {


//        if (position < -1) {//看不到的一页 *
//            page.setScaleX(1);
//            page.setScaleY(1);
//        } else if (position <= 1) {
//            if (position < 0) {//滑出的页 0.0 ~ -1 *
//                float scaleFactor = (1 - MIN_SCALE) * (0 - position);
//                page.setScaleX(1 - scaleFactor);
//                page.setScaleY(1 - scaleFactor);
//            } else {//滑进的页 1 ~ 0.0 *
//                float scaleFactor = (1 - MIN_SCALE) * (1 - position);
//                page.setScaleX(MIN_SCALE + scaleFactor);
//                page.setScaleY(MIN_SCALE + scaleFactor);
//            }
//        } else {//看不到的另一页 *
//            page.setScaleX(1);
//            page.setScaleY(1);
//        }


        // 阈值 低的 后进后出，高的先进先出

        /**
         * position < 0 表示 手指 从右向左滑动，用来处理向左滑出的页面，以及从左向右滑动时从左边滑入的页面   （差不多就是处理左边的页面）
         */
        /**
         * 阈值 越小移动的越慢
         */
        if (position < 0) {
            System.out.println(position);
            textView.setTranslationX(position * width * 0.01f);
            textView2.setTranslationX(position * width * 0.4f);
            textView3.setTranslationX(position * width * 0.6f);
            textView4.setTranslationX(position * width * 5f);
        }
    }
}
