package com.example.lablnet.simplenote;

/**
 * Created by lablnet on 8/25/2017.
 */

public class Data_Model {
    public String mTitle,mDescription;
    static String mDate;
    public String mReminderDate;
    public String mReminderTime;
    int showAlarmIcon;
    int id;
    public static MainActivity activity;
    public static String pickerReminderDate;
    public static String titleForReminder,DescriptionForReminder,dataForReminder;
    public static int id_FromWherePickerOpen;

    public static int getId_FromWherePickerOpen() {
        return id_FromWherePickerOpen;
    }

    public static void setId_FromWherePickerOpen(int id_FromWherePickerOpen) {
        Data_Model.id_FromWherePickerOpen = id_FromWherePickerOpen;
    }

    public static String getTitleForReminder() {
        return titleForReminder;
    }

    public static void setTitleForREminder(String titleForReminder) {
        Data_Model.titleForReminder = titleForReminder;
    }

    public static String getDescriptionForReminder() {
        return DescriptionForReminder;
    }

    public static void setDescriptionForReminder(String descriptionForReminder) {
        DescriptionForReminder = descriptionForReminder;
    }

    public static String getDataForReminder() {
        return dataForReminder;
    }

    public static void setDataForReminder(String dataForReminder) {
        Data_Model.dataForReminder = dataForReminder;
    }

    public static String getPickerReminderDate() {
        return pickerReminderDate;
    }

    public static void setPickerReminderDate(String pickerReminderDate) {
        Data_Model.pickerReminderDate = pickerReminderDate;
    }

    public static String getPickerReminderTime() {
        return pickerReminderTime;
    }

    public static void setPickerReminderTime(String pickerReminderTime) {
        Data_Model.pickerReminderTime = pickerReminderTime;
    }

    public static String pickerReminderTime;

    public static MainActivity getActivity() {
        return activity;
    }

    public static void setActivity(MainActivity activity) {
        Data_Model.activity = activity;
    }

    public int getId() {
        return id;
    }

    public Data_Model() {
    }

    public Data_Model(int id, String mTitle, String mDescription, String mDate, String mReminderTime, String mReminderDate, int showAlarmIcon) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.showAlarmIcon=showAlarmIcon;
        this.mReminderDate = mReminderDate;
        this.mReminderTime = mReminderTime;
        this.id=id;
    }


    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getDate() {
        return mDate;
    }

    public String getReminderDate() {
        return mReminderDate;
    }

    public String getReminderTime() {
        return mReminderTime;
    }
    public int getShowAlarmIcon() {
        return showAlarmIcon;
    }
}
