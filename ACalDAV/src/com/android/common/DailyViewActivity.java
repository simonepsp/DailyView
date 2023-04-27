package com.android.common;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.aphy.caldavsyncadapter.android.entities.CalendarEventData;
import com.aphy.caldavsyncadapter.utils.TimeUtil;

import java.time.Duration;
import java.util.ArrayList;

import ch.punkt.mp02.dailyview.R;


public class DailyViewActivity extends AppCompatActivity {
    private ScrollView mScrollView;
    private TextView mTitleText;
    private TextView mDateText;
    private TextView mContentText;
    private ArrayList<String> mRecyclerViewList = new ArrayList<>();

    private CalendarEventData mEventData;

    private String mWeek;

    private long mDtStart;
    private long mDtEnd;

    private String mDescription;
    private String mLocation;
    private String mTitle;

    private String mDuration;
    private String mRrule;

    private int mAllday;

    private int mCurrentIndex = 1;
    private int mLastPosition = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        mScrollView = findViewById(R.id.scrollview);
        mTitleText = findViewById(R.id.title_text);
        mDateText = findViewById(R.id.date_text);
        mContentText = findViewById(R.id.content_text);

        mEventData = getIntent().getParcelableExtra("calendarEventData");
        if (mEventData != null) {
            analysisEvent();
        }
        mWeek = getIntent().getStringExtra("week");

        initList();
        mScrollView.requestFocus();
    }

    private void analysisEvent() {
        mDtStart = mEventData.getDtStart();
        mDtEnd = mEventData.getDtEnd();
        mDescription = mEventData.getDescription();
        mLocation = mEventData.getLocation();
        mTitle = mEventData.getTitle();
        if (mTitle == null | mTitle.length() == 0 | mTitle.equals("**unknown")) {
            mTitle = getString(R.string.no_title);
        }
        mAllday = mEventData.getAllDay();
        mDuration = mEventData.getDuration();
        mRrule = mEventData.getRrule();
    }

    @SuppressLint("SetTextI18n")
    private void initList() {
        String dateString = TimeUtil.getDay(mDtStart) + " " + TimeUtil.getMonth(this, mDtStart) + " " + TimeUtil.getYear(mDtStart);

        String dateEndString = TimeUtil.getDay(mDtEnd) + " " + TimeUtil.getMonth(this, mDtEnd) + " " + TimeUtil.getYear(mDtEnd);

        String startTime = TimeUtil.getHour(mDtStart);
        String endTime = TimeUtil.getHour(mDtEnd);

        mTitleText.setText(mTitle);

        if (mAllday == 1) {
            mDateText.setText(dateString + "\n" + mWeek); // + " " + "00:00" + "-" + "24:00");
        } else {
            mDateText.setText(dateString + "\n" + mWeek + " " + startTime + "-" + endTime);
        }

        if ("FREQ=DAILY".equals(mRrule)) {
            Duration duration = Duration.parse(mDuration);
            long dtend =  mDtStart + duration.toMillis();
            dateEndString = TimeUtil.getDay(dtend) + " " + TimeUtil.getMonth(this, dtend) + " " + TimeUtil.getYear(dtend);
            endTime = TimeUtil.getHour(mDtStart + duration.toMillis());
            if (!dateString.equals(dateEndString)) {
                mDateText.setText(dateString + " " + startTime + " " + "-" + "\n" + dateEndString + " "  + endTime);
            } else {
                mDateText.setText(dateString + "\n" + mWeek + " " + startTime + "-" + endTime);
            }
        } else if ("FREQ=WEEKLY".equals(mRrule)) {
            Duration duration = Duration.parse(mDuration);
            long dtend =  mDtStart + duration.toMillis();
            dateEndString = TimeUtil.getDay(dtend) + " " + TimeUtil.getMonth(this, dtend) + " " + TimeUtil.getYear(dtend);

            endTime = TimeUtil.getHour(dtend);
            if (mAllday == 1) {
                mDateText.setText(dateString + "\n" + mWeek); // + " " + "00:00" + "-" + "24:00");
            } else if (!dateString.equals(dateEndString)) {
                mDateText.setText(dateString + " " + startTime + " " + "-" + "\n" + dateEndString + " "  + endTime);
            } else {
                mDateText.setText(dateString + "\n" + mWeek + " " + startTime + "-" + endTime);
            }
        } else if ((mRrule == null) && (!dateString.equals(dateEndString)) && mAllday != 1) {
            mDateText.setText(dateString + " " + startTime + " " + "-" + "\n" + dateEndString + " "  + endTime);
        }

        if (mLocation != null) {
            mDateText.append("\n\n" + getString(R.string.location) + ": " + mLocation);
//            mContentText.setText(getString(R.string.location) + ":\n" + mLocation + "\n\n" + mDescription);
//        } else {
        }

        if (mDescription != null)
            mContentText.setText(mDescription);
//        }

    }
}
