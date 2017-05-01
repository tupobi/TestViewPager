package com.example.administrator.testviewpager;

import android.widget.ImageView;

/**
 * Created by Administrator on 2017/5/1.
 */

public class PagerItem {
    private ImageView imageItem;
    private int resourceId;
    private String title;
    private ImageView point;

    public PagerItem(ImageView imageItem, int resourceId, String title, ImageView point) {
        this.imageItem = imageItem;
        this.resourceId = resourceId;
        this.title = title;
        this.point = point;
        this.imageItem.setBackgroundResource(this.resourceId);//无填充, setResourceId(); 有填充
        point.setBackgroundResource(R.drawable.point_selector);
    }

    public ImageView getPoint() {
        return point;
    }

    public void setPoint(ImageView point) {
        this.point = point;
    }

    public ImageView getImageItem() {
        return imageItem;
    }

    public void setImageItem(ImageView imageItem) {
        this.imageItem = imageItem;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
