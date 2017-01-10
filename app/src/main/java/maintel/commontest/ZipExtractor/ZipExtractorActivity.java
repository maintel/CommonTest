package maintel.commontest.ZipExtractor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/1/9 11:42
 * 备注：
 */
@Content(R.layout.activity_zip)
public class ZipExtractorActivity extends BaseActivity implements ZipExtractorContract.View {


    @Bind(R.id.btn_down_load)
    Button btnDownLoad;
    @Bind(R.id.btn_zip_extractor)
    Button btnZipExtractor;
    @Bind(R.id.pro_down_load)
    ProgressBar proDownLoad;
    @Bind(R.id.tv_message)
    TextView tvMessage;

    ZipExtractorContract.Presenter zipExtractorPresenter;

    @Override
    protected void initData() {
        new ZipExtractorPresenter(this,this);
    }

    @OnClick({R.id.btn_down_load, R.id.btn_zip_extractor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_down_load:
                zipExtractorPresenter.downLoad();
                break;
            case R.id.btn_zip_extractor:
                zipExtractorPresenter.zipExtractor();
                break;
        }
    }

    @Override
    public void setPresenter(ZipExtractorContract.Presenter presenter) {
        zipExtractorPresenter = presenter;
    }

    @Override
    public void setMessage(String msg) {
        tvMessage.setText(msg);
    }

}
