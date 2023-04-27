package com.android.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.aphy.caldavsyncadapter.android.entities.CalendarEventData;
import com.aphy.caldavsyncadapter.authenticator.AuthenticatorActivity;
import com.aphy.caldavsyncadapter.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import ch.punkt.mp02.dailyview.R;

public class DailyViewListActivity extends AppCompatActivity {
    private RecyclerView mRcContent;
    private MyDecoration mDecoration;
    private View mCurrentView;
    private TextSwitcher mTextSwitcher;

    private MyAdaptor myAdaptor;
    private CenterLayoutManager manager;
    private ArrayList<String> mRecyclerViewList = new ArrayList<>();
    private ArrayList<String> mTitleList = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private int mCurrentIndex = 0;
    private int mLastPosition = 0;
    private int mCurrentIndexStoped = 0;

    private String mWeek;
    private String mDateStamp;
    private List<CalendarEventData> mCalendarEventDataListFromDataBase;

    private boolean mDataInited = false;
    private long mRespondTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRcContent = findViewById(R.id.rv_content);
        mTextSwitcher = findViewById(R.id.textSwitcher);
        mTextSwitcher.setFactory(mFactory);
        Intent intent = getIntent();
        mWeek = intent.getStringExtra("week");
        mDateStamp = intent.getStringExtra("dateStamp");
        mCalendarEventDataListFromDataBase = intent.getParcelableArrayListExtra("calendarDataList");
        initList();
        MenuItem.init(this);
        myAdaptor = new MyAdaptor();
        myAdaptor.setIListItemClick(iListItemClickListener);
        manager = new CenterLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRcContent.setLayoutManager(manager);
        mDecoration = new MyDecoration(this);
        mRcContent.addItemDecoration(mDecoration);
        mRcContent.setAdapter(myAdaptor);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initList() {
        long dtStart = 0;
        for (CalendarEventData calendarEventData : mCalendarEventDataListFromDataBase) {
            if (calendarEventData != null) {
                String date = TimeUtil.getDate(calendarEventData.getDtStart());
                if (date.equals(mDateStamp)) {
                    String title = calendarEventData.getTitle();
                    if ("**unkonwn**".equals(title)) {
                        title = "No title";
                    }
                    mTitleList.add(title);
                    if (dtStart == 0) {
                        dtStart = calendarEventData.getDtStart();
                    }
                }
            }
        }
        String dateString = TimeUtil.getDay(dtStart) + " " + TimeUtil.getMonth(this, dtStart) + " " + TimeUtil.getYear(dtStart);
        mRecyclerViewList.add(dateString);
        mRecyclerViewList.add(mWeek);
        mRecyclerViewList.addAll(mTitleList);
        if (mRecyclerViewList.size() == 3) {
            mRecyclerViewList.add("");
        }
    }

    private final ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {
            // Create a new TextView
            TextView t = new TextView(DailyViewListActivity.this);
            t.setTextAppearance(R.style.mp02TextViewFocusStyle);
            t.setSingleLine(true);
            t.setEllipsize(TextUtils.TruncateAt.END);
            return t;
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        if (action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mRespondTime < 400) {
                return true;
            }
            mRespondTime = System.currentTimeMillis();
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (mRecyclerViewList == null) return true;
                    mCurrentIndex--;
                    if (mCurrentIndex < 2) {
                        mCurrentIndex = 2;
                        return true;
                    }

                    if (myAdaptor != null && myAdaptor.mSparseArray != null) {
                        MyHolder mUpHolder = myAdaptor.mSparseArray.get(mCurrentIndex);
                        if (mUpHolder != null) {
                            mUpHolder.mView.requestFocus();
                            setTextSwitcher(MenuItem.Direction.UP);
                        }
                    }
                    manager.smoothScrollToPosition(mRcContent, null, mLastPosition, mCurrentIndex);
                    return true;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (mRecyclerViewList == null) return true;
                    mCurrentIndex++;
                    if (mRecyclerViewList.size() == 4 && "".equals(mRecyclerViewList.get(3))) {
                        if (mCurrentIndex > 2) {
                            mCurrentIndex = 2;
                            return true;
                        }
                    }
                    if (mCurrentIndex > mRecyclerViewList.size() - 1) {
                        mCurrentIndex = mRecyclerViewList.size() - 1;
                        return true;
                    }
                    if (myAdaptor != null && myAdaptor.mSparseArray != null) {
                        MyHolder mDownHolder = myAdaptor.mSparseArray.get(mCurrentIndex);
                        if (mDownHolder != null) {
                            mDownHolder.mView.requestFocus();
                            setTextSwitcher(MenuItem.Direction.DOWN);
                        }
                    }
                    manager.smoothScrollToPosition(mRcContent, null, mLastPosition, mCurrentIndex);
                    return true;
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    if (mCurrentView != null) mCurrentView.performClick();
                    return true;
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;
            }
        }
        if (mLastPosition != mCurrentIndex) {
            mLastPosition = mCurrentIndex;
        }
        return super.dispatchKeyEvent(event);
    }

    private void setTextSwitcher(MenuItem.Direction direction) {
        switch (direction) {
            case DOWN:
                mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.upandvisible));
                mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.upandgone));
                break;
            case UP:
                mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.downandvisible));
                mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.downandgone));
                break;
        }
        mTextSwitcher.setText(mRecyclerViewList.get(mCurrentIndex));
    }

    private void requestFocus(TextView textView) {
//        textView.setTextAppearance(R.style.mp02TextViewFocusStyle);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 54);
        textView.setLayoutParams(lp);
//        textView.setPadding(5, 0, 0, 0);
    }

    private void requestUnfocus(TextView textView) {
//        textView.setTextAppearance(R.style.mp02TextViewUnfocusStyle);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 32);
        textView.setLayoutParams(lp);
//        textView.setPadding(30, 0, 0, 0);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        View mView;

        MyHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
    }

    class MyAdaptor extends RecyclerView.Adapter<MyHolder> {

        IListItemClick mIListItemClick;
        SparseArray<MyHolder> mSparseArray = new SparseArray<>();

        public void setIListItemClick(IListItemClick mIListItemClick) {
            this.mIListItemClick = mIListItemClick;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_layout, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
            final String s = mRecyclerViewList.get(position);
            if (!TextUtils.isEmpty(s)) {
                if (position >= 2) {
                    holder.mView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                mCurrentView = v;
                                holder.mView.setSelected(true);
                                requestFocus((TextView) holder.mView.findViewById(R.id.tv_item));
                            } else {
                                holder.mView.setSelected(false);
                                requestUnfocus((TextView) holder.mView.findViewById(R.id.tv_item));
                            }
                        }
                    });
                }

                holder.mView.setTag(position);
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mIListItemClick != null) {
                            mIListItemClick.itemClick(v, position);
                        }
                    }
                });

                TextView item = holder.mView.findViewById(R.id.tv_item);
                item.setText(s);

                mSparseArray.put(position, holder);
                if (!mDataInited && position == 2) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDataInited = true;
                            mCurrentIndex = position;
                            manager.smoothScrollToPosition(mRcContent, null, mLastPosition, position);
                            holder.mView.requestFocus();
                            if (mLastPosition != mCurrentIndex) {
                                mLastPosition = mCurrentIndex;
                            }
                            mTextSwitcher.setText(mRecyclerViewList.get(2));
                        }
                    }, 100);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewList == null ? 0 : mRecyclerViewList.size();
        }
    }

    private IListItemClick iListItemClickListener = new IListItemClick() {
        @Override
        public void itemClick(View view, int position) {
            if (position > 1) {
                CalendarEventData calendarEventData = mCalendarEventDataListFromDataBase.get(position - 2);
                Intent intent = new Intent();
                intent.putExtra("calendarEventData", calendarEventData);
                intent.putExtra("week", mWeek);
                intent.setClass(DailyViewListActivity.this, DailyViewActivity.class);
                startActivity(intent);
            }
        }
    };

}
