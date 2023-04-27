package com.aphy.caldavsyncadapter.android.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class CalendarEventData implements Parcelable {
    public static String DtStart = "DtStart";
    public static String TimeZone = "TimeZone";
    public static String DtEnd = "DtEnd";
    public static String EndTimeZone = "EndTimeZone";
    public static String Duration = "Duration";

    public static String AllDay = "AllDay";
    public static String Title = "Title";
    public static String SyncID = "SyncID";
    public static String ETag = "ETag";

    public static String Description = "Description";
    public static String Location = "Location";
    public static String AccessLevel = "AccessLevel";
    public static String Status = "Status";
    public static String Rdate = "Rdate";
    public static String Rrule = "Rrule";
    public static String ExRule = "ExRule";
    public static String ExDate = "ExDate";
    public static String UID = "UID";
    public static String RawData = "RawData";

    private long mDtStart;
    private String mTimeZone;
    private long mDtEnd;
    private String mEndTimeZone;
    private String mDuration;

    private int mAllDay;
    private String mTitle;
    private String mSyncID;
    private String mETag;

    private String mDescription;
    private String mLocation;
    private long mAccessLevel;
    private int mStatus;
    private String mRdate;
    private String mRrule;
    private String mExRule;
    private String mExDate;
    private String mUID;
    private String mRawData;

    public CalendarEventData() {
    }

    public long getDtStart() {
        return mDtStart;
    }

    public void setDtStart(long mDtStart) {
        this.mDtStart = mDtStart;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String mTimeZone) {
        this.mTimeZone = mTimeZone;
    }

    public long getDtEnd() {
        return mDtEnd;
    }

    public void setDtEnd(long mDtEnd) {
        this.mDtEnd = mDtEnd;
    }

    public String getEndTimeZone() {
        return mEndTimeZone;
    }

    public void setEndTimeZone(String mEndTimeZone) {
        this.mEndTimeZone = mEndTimeZone;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public int getAllDay() {
        return mAllDay;
    }

    public void setAllDay(int mAllDay) {
        this.mAllDay = mAllDay;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSyncID() {
        return mSyncID;
    }

    public void setSyncID(String mSyncID) {
        this.mSyncID = mSyncID;
    }

    public String getETag() {
        return mETag;
    }

    public void setETag(String mETag) {
        this.mETag = mETag;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public long getAccessLevel() {
        return mAccessLevel;
    }

    public void setAccessLevel(long mAccessLevel) {
        this.mAccessLevel = mAccessLevel;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getRdate() {
        return mRdate;
    }

    public void setRdate(String mRdate) {
        this.mRdate = mRdate;
    }

    public String getRrule() {
        return mRrule;
    }

    public void setRrule(String mRrule) {
        this.mRrule = mRrule;
    }

    public String getExRule() {
        return mExRule;
    }

    public void setExRule(String mExRule) {
        this.mExRule = mExRule;
    }

    public String getExDate() {
        return mExDate;
    }

    public void setExDate(String mExDate) {
        this.mExDate = mExDate;
    }

    public String getUID() {
        return mUID;
    }

    public void setUID(String mUID) {
        this.mUID = mUID;
    }

    public String getRawData() {
        return mRawData;
    }

    public void setRawData(String mRawData) {
        this.mRawData = mRawData;
    }

    @Override
    public String toString() {
        return "CalendarEventData{" +
                "mDtStart=" + mDtStart +
                ", mTimeZone='" + mTimeZone + '\'' +
                ", mDtEnd=" + mDtEnd +
                ", mEndTimeZone='" + mEndTimeZone + '\'' +
                ", mDuration='" + mDuration + '\'' +
                ", mAllDay=" + mAllDay +
                ", mTitle='" + mTitle + '\'' +
                ", mSyncID='" + mSyncID + '\'' +
                ", mETag='" + mETag + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mAccessLevel=" + mAccessLevel +
                ", mStatus=" + mStatus +
                ", mRdate='" + mRdate + '\'' +
                ", mRrule='" + mRrule + '\'' +
                ", mExRule='" + mExRule + '\'' +
                ", mExDate='" + mExDate + '\'' +
                ", mUID='" + mUID + '\'' +
                ", mRawData='" + mRawData + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mDtStart);
        dest.writeString(mTimeZone);
        dest.writeLong(mDtEnd);
        dest.writeString(mEndTimeZone);
        dest.writeString(mDuration);
        dest.writeInt(mAllDay);
        dest.writeString(mTitle);
        dest.writeString(mSyncID);
        dest.writeString(mETag);
        dest.writeString(mDescription);
        dest.writeString(mLocation);
        dest.writeLong(mAccessLevel);
        dest.writeInt(mStatus);
        dest.writeString(mRdate);
        dest.writeString(mRrule);
        dest.writeString(mExRule);
        dest.writeString(mExDate);
        dest.writeString(mUID);
        dest.writeString(mRawData);
    }

    protected CalendarEventData(Parcel in) {
        mDtStart = in.readLong();
        mTimeZone = in.readString();
        mDtEnd = in.readLong();
        mEndTimeZone = in.readString();
        mDuration = in.readString();
        mAllDay = in.readInt();
        mTitle = in.readString();
        mSyncID = in.readString();
        mETag = in.readString();
        mDescription = in.readString();
        mLocation = in.readString();
        mAccessLevel = in.readLong();
        mStatus = in.readInt();
        mRdate = in.readString();
        mRrule = in.readString();
        mExRule = in.readString();
        mExDate = in.readString();
        mUID = in.readString();
        mRawData = in.readString();
    }

    public static final Creator<CalendarEventData> CREATOR = new Creator<CalendarEventData>() {
        @Override
        public CalendarEventData createFromParcel(Parcel in) {
            return new CalendarEventData(in);
        }

        @Override
        public CalendarEventData[] newArray(int size) {
            return new CalendarEventData[size];
        }
    };
}
