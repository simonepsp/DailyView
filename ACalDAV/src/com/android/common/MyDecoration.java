package com.android.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ch.punkt.mp02.dailyview.R;

public class MyDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;

    public MyDecoration(Context context) {
        mContext = context;
    }
    private int distance = 0;

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (distance <= 0) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    distance = 76;
                    View childView = parent.getChildAt(0);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
                    layoutParams.setMargins(0, distance, 0, 0);
                    childView.setLayoutParams(layoutParams);
                    if (parent.getChildCount() == 2) {
                        parent.scrollToPosition(0);
                    }
                }
            });
        }
        int itemCount = parent.getAdapter().getItemCount();
        if (pos == 0) {
            layoutParams.setMargins(0, distance, 0, 0);
        } else if (pos == 2 && pos == itemCount -1) {
            layoutParams.setMargins(0, 10, 0, 110);
        } else if (pos == 2) {
            layoutParams.setMargins(0, 10, 0, 0);
        } else if (pos == itemCount - 1) {
            layoutParams.setMargins(0, 0, 0, 110);
        } else {
            layoutParams.setMargins(0, 0, 0, 0);
        }
        view.setLayoutParams(layoutParams);
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mContext.getColor(R.color.mp02UnfocusedItem));
        paint.setStrokeWidth(1);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (position == 1) {
                c.drawLine(30, child.getBottom() + 5, 250, child.getBottom() + 5, paint);
            }
        }
    }
}
