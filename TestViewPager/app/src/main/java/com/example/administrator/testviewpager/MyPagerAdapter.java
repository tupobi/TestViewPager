package com.example.administrator.testviewpager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/1.
 */

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<PagerItem> mPagerItems;
    private Context mContext;
    private Handler mHandler;
    private long downTime;
    private int downX;

    public MyPagerAdapter(ArrayList<PagerItem> pagerItems, Context context, Handler handler) {
        super();
        this.mPagerItems = pagerItems;
        this.mContext = context;
        this.mHandler = handler;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        int realPosition = position % mPagerItems.size();
        ImageView imageItem = mPagerItems.get(realPosition).getImageItem();
        container.addView(imageItem);


        imageItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);
                        downX = (int) motionEvent.getX();
                        downTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - downTime < 500
                                && Math.abs(downX - motionEvent.getX()) < 30) {
                            Toast.makeText(mContext, "position:" + position%mPagerItems.size(), Toast.LENGTH_SHORT).show();
                        }//判断是滑动还是点击事件！
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(0, 5*1000);
                        break;
                    case MotionEvent.ACTION_CANCEL:
//                        mHandler.removeCallbacksAndMessages(null);
//                        mHandler.sendEmptyMessageDelayed(0, 5*1000);
//                        防止与onPageScrollStateChanged()冲突
                        break;
                }
                return true;
            }
        });

        return imageItem;
    }

    @Override
    public int getCount() {
//        return mPagerItems.size();
        return 800;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
