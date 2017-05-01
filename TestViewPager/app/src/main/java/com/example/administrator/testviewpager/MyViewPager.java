package com.example.administrator.testviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.administrator.testviewpager.R.id.tv_title;

public class MyViewPager extends AppCompatActivity {
    private ViewPager myViewPager;
    private TextView tvTitle;
    private LinearLayout llPointGroup;
    private ArrayList<PagerItem> pagerItems;
    private int previousPosition = 0;
    private boolean isDragging = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int item = myViewPager.getCurrentItem()+1;
            myViewPager.setCurrentItem(item);

            handler.sendEmptyMessageDelayed(0, 4*1000);
        }
    };//实现定时滑动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initPagerItems();
        setPager();
    }

    private void setPager() {
        myViewPager.setAdapter(new MyPagerAdapter(pagerItems, this, handler));
        myViewPager.setCurrentItem(400-400%pagerItems.size());//初始位置必须为pagerItems.size()的整数倍，且放在setAdapter()后面才有用。
        handler.sendEmptyMessageDelayed(0, 5*1000);//相当于定时滑动的触发器
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position%pagerItems.size();
                tvTitle.setText(pagerItems.get(realPosition).getTitle());
                llPointGroup.getChildAt(previousPosition).setEnabled(false);
                llPointGroup.getChildAt(realPosition).setEnabled(true);
                previousPosition = realPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_DRAGGING){
                    handler.removeCallbacksAndMessages(null);
                    isDragging = true;
                }else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging) {
                    isDragging = false;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0, 5*1000);
                }else if (state == ViewPager.SCROLL_STATE_SETTLING){}
            }
        });

    }

    private void initPagerItems() {
        pagerItems = new ArrayList<>();
        pagerItems.add(new PagerItem(new ImageView(this), R.drawable.pager1, "111111", new ImageView(this)));
        pagerItems.add(new PagerItem(new ImageView(this), R.drawable.pager2, "222222", new ImageView(this)));
        pagerItems.add(new PagerItem(new ImageView(this), R.drawable.pager3, "333333", new ImageView(this)));
        pagerItems.add(new PagerItem(new ImageView(this), R.drawable.pager4, "444444", new ImageView(this)));
        pagerItems.add(new PagerItem(new ImageView(this), R.drawable.pager5, "555555", new ImageView(this)));

        for (int i = 0; i < pagerItems.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
            if (i == 0) {
                pagerItems.get(i).getPoint().setEnabled(true);
                tvTitle.setText(pagerItems.get(i).getTitle());
            } else {
                pagerItems.get(i).getPoint().setEnabled(false);
                params.leftMargin = 15;
            }
            pagerItems.get(i).getPoint().setLayoutParams(params);

            llPointGroup.addView(pagerItems.get(i).getPoint());
        }
    }

    private void initView() {
        setContentView(R.layout.activity_my_view_pager);//考虑到有些控件必须分先后初始化，所以这句有时候放在外面开头
        llPointGroup = (LinearLayout) findViewById(R.id.ll_pointGroup);
        tvTitle = (TextView) findViewById(tv_title);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
    }
}
