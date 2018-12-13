package maintel.commontest.optimize;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.utils.LoadBitMap;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/7/24 10:23
 * 备注：
 */

public class RuanYNTestActivity extends BaseActivity {
    @Override
    protected void initData() {

        LinearLayout linearLayout = new LinearLayout(this);

        setContentView(linearLayout);
        Button button = new Button(this);
        button.setText("GC");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
            }
        });
        linearLayout.addView(button);

        Button button2 = new Button(this);
        button2.setText("ADD");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadBitMap.getINSTANCE().addBitmapCache(getResources(), R.drawable.oom3);
            }
        });
        linearLayout.addView(button2);
    }
}
