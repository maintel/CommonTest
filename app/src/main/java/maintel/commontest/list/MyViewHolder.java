package maintel.commontest.list;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明：自定义Holder实现类
 * 作者：mainTel
 * 时间：2016/6/29 17:57
 * 备注：
 */
public class MyViewHolder {

    SparseArray<View> viewSparseArray;
    View view;

    public MyViewHolder(View itemView) {
        viewSparseArray = new SparseArray<>();
        view = itemView;
        view.setTag(viewSparseArray);
    }

    public static MyViewHolder getHolder(View view) {
        MyViewHolder viewBaseHolder = (MyViewHolder) view.getTag();
        if (viewBaseHolder == null) {
            viewBaseHolder = new MyViewHolder(view);
            view.setTag(viewBaseHolder);
        }
        return viewBaseHolder;
    }

    public <T extends View> T get(int id) {
        View childView = viewSparseArray.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            viewSparseArray.put(id, childView);
        }
        return (T) childView;
    }

    public View getConvertView() {
        return view;
    }

    public TextView getTextView(int id) {
        return get(id);
    }

    public Button getButton(int id) {
        return get(id);
    }

    public ImageView getImageView(int id) {
        return get(id);
    }

    public void setTextView(int id, CharSequence charSequence) {
        getTextView(id).setText(charSequence);
    }
}
