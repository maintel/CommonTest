package maintel.commontest.recycleviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import maintel.commontest.R;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/11/3 14:54
 * 备注：
 */
public class RecycleViewTest extends AppCompatActivity implements View.OnClickListener {

    @SuppressWarnings("unchecked")
    public <T> T $(int resId) {
        return (T) findViewById(resId);
    }

    RecyclerView recyclerView;
    RecycleViewTestAdapter<String> testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_test);
        recyclerView = $(R.id.recy_test);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("testtest" + i);
        }
        testAdapter = new RecycleViewTestAdapter<>(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(testAdapter);
    }


    @Override
    public void onClick(View v) {
        List<String> list = new ArrayList<>();
        for (int i = 10; i < 20; i++) {
            list.add("testtest" + i);
        }
        testAdapter.upDateAdd(list);

    }
}
