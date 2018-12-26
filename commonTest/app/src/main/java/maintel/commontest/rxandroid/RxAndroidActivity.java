package maintel.commontest.rxandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import maintel.commontest.R;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/4/5 10:09
 * 备注：
 */

public class RxAndroidActivity extends AppCompatActivity {


    @Bind(R.id.btn_get_user)
    Button btnGetUser;
    @Bind(R.id.btn_rx_just)
    Button btnRxJust;

    private Subscriber<String> subscriber;
    private Observable<String> observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        ButterKnife.bind(this);
        createSubscriber();
    }

    private void createSubscriber() {
        subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e("RxAndroidActivity", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("RxAndroidActivity", "onNext");
                Log.e("RxAndroidActivity", s);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("RxAndroidActivity", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("RxAndroidActivity", "onComplete");
            }
        };
    }


    private void createObservable() {
        observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                subscriber.onNext(getHello());
                subscriber.onComplete();
            }
        });
        bindSubscriber();
    }

    private void bindSubscriber() {
        observable.subscribe();
    }

    private String getHello() {
        return "Hello RxAndroid";
    }

    private String getHello1() {
        return "Hello RxAndroid 1";
    }

    @OnClick({R.id.btn_get_user, R.id.btn_rx_just})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_user:
                createObservable();
                break;
            case R.id.btn_rx_just:
                Observable.just(getHello1()).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
        }
    }

}
