package maintel.commontest.list;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    LoadMoreAdapter testAdapter;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_test);
        recyclerView = $(R.id.recy_test);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("testtest" + i);
        }
        testAdapter = new LoadMoreAdapter(list, this);
        testAdapter.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() {

                if (total > 2) {
                    testAdapter.showLoadNoMore();
                }
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add("testtest more" + i);
                }
                testAdapter.upDateAdd(list);
                total++;
            }

            @Override
            public void loadError() {
                Log.e("RecycleViewTest", "loadRrror");
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add("testtest error" + i);
                }
                testAdapter.upDateAdd(list);
            }
        });
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
