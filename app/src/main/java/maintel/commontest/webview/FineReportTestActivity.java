package maintel.commontest.webview;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;

/**
 * 说明：FineReportTest
 * 作者：mainTel
 * 时间：2016/11/4 17:16
 * 备注：
 */
@Content(R.layout.activity_fine_report)
public class FineReportTestActivity extends BaseActivity {

    @Bind(R.id.web_fine)
    WebView web_fine;

    @Override
    protected void initData() {
        web_fine.loadUrl("http://192.168.1.200:8080/examples/ReportServer?reportlet=map.cpt&__bypagesize__=false");
        WebSettings settings = web_fine.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        web_fine.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        web_fine.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }
        });
    }
}
