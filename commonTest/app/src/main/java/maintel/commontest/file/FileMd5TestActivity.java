package maintel.commontest.file;

import android.os.Bundle;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import maintel.commontest.utils.FileUtils;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/6/5 14:16
 * 备注：
 */

public class FileMd5TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        setContentView(textView);
        textView.setText(FileUtils.getFileMD5Assets(this, "test.bmp"));
    }
}
