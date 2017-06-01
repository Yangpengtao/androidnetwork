package cn.com.oomall.kktown.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.com.oomall.kktown.App;
import cn.com.oomall.kktown.R;
import cn.com.oomall.kktown.Utils.ScreenUtils;

public class LoadingProgressDialog extends ProgressDialog {

    private ImageView mImageView;

    private Context mContext;

    public LoadingProgressDialog(Context context) {
        super(context);
        this.mContext = context;
        setCanceledOnTouchOutside(true);
         getWindow().requestFeature(Window.FEATURE_NO_TITLE) ;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageView = new ImageView(mContext);
        int width = ScreenUtils.convertDpToPixel(mContext, 80);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, width);

        mImageView.setLayoutParams(layoutParams);
        mImageView.setBackgroundColor(Color.TRANSPARENT);
        Glide.with(App.getInstance())
                .load(R.drawable.load)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);
        setContentView(mImageView );
    }

}
