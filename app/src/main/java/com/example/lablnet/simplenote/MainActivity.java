package com.example.lablnet.simplenote;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DateTimePicker.DateTimeDialogFrag;

public class MainActivity extends AppCompatActivity {
    ListView lst;
    Data_Model mModel;
    ArrayList<Data_Model> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Notes");
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_New_Note add_new_note = new Add_New_Note();
                add_new_note.setCancelable(false);
                add_new_note.show(getSupportFragmentManager(), "123");
            }
        });
        lst = (ListView) findViewById(R.id.listView);
        ReadData();
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                Data_Model data = mList.get(position);
                intent.putExtra("Title", data.getTitle());
                intent.putExtra("Description", data.getDescription());
                intent.putExtra("Time", data.getDate());
                intent.putExtra("ID", data.getId());
                Data_Model.setActivity(MainActivity.this);
                String mmdate = data.getReminderDate();
                String mmtime = data.getReminderTime();
                if (mmdate == null) {
                } else {
                    long mills = converTimeInMills(mmdate, mmtime);
                    intent.putExtra("ReminderDate", formattedDate(mills));
                    intent.putExtra("ReminderTime", formattedTime(mills));
                }
                startActivity(intent);
            }
        });
        registerForContextMenu(lst);
    }

    public void ReadData() {
        mList = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.ReadData();
        while (cursor.moveToNext()) {
            mModel = new Data_Model(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
            mList.add(mModel);
        }
        Custum_Adapter mAdapter = new Custum_Adapter(this, mList);
        lst.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void ShowPickerDialog() {
        DateTimeDialogFrag dateTimeDialogFrag = new DateTimeDialogFrag();
        dateTimeDialogFrag.setCancelable(false);
        dateTimeDialogFrag.show(getSupportFragmentManager(), "222");
        Data_Model.setId_FromWherePickerOpen(0);
    }

    public void saveData() {
        DatabaseHelper mDatabase = new DatabaseHelper(this);
        String title = Data_Model.getTitleForReminder();
        String description = Data_Model.getDescriptionForReminder();
        String date = Data_Model.getDataForReminder();
        mDatabase.SaveData(title, description, date, Data_Model.getPickerReminderDate(), Data_Model.getPickerReminderTime(), 1);
        setAlarm(Data_Model.getPickerReminderTime(), Data_Model.getPickerReminderDate(), title, description);
        ReadData();
    }

    public void setAlarm(String mTime, String mDate, String mTitle, String mText) {
        long mills = converTimeInMills(mTime, mDate);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        int id = (int) System.currentTimeMillis();
        intent.putExtra("title", mTitle);
        intent.putExtra("text", mText);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), id, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, mills, pendingIntent);
        Toast.makeText(this, "Alarm set At " + formattedTime(mills) + " " + formattedTime(mills), Toast.LENGTH_SHORT).show();
    }

    public long converTimeInMills(String mTime, String mDate) {
        String[] mtime = mTime.split(" ");
        String[] mdate = mDate.split(" ");
        int[] date = new int[mdate.length];
        int[] time = new int[mtime.length];
        for (int i = 0; i < date.length; i++) {
            date[i] = Integer.parseInt(mdate[i]);
        }
        for (int i = 0; i < time.length; i++) {
            time[i] = Integer.parseInt(mtime[i]);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(date[2], date[1], date[0], time[1], time[0]);
        long mills = calendar.getTimeInMillis();
        return mills;
    }

    public String formattedTime(long mills) {
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("h:mm a");
        return simpleTimeFormat.format(mills);
    }

    public String formattedDate(long mills) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd yyy");
        return simpleDateFormat.format(mills);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Delete a file");
        v.setBackgroundColor(getResources().getColor(R.color.colorListBackgroung));
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                deleteRecord(info.id);
        }
        return super.onContextItemSelected(item);
    }

    public void deleteRecord(long position) {
        Data_Model data = mList.get((int) position);
        int id = data.getId();
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        databaseHelper.DeleteRecord(id);
        Toast.makeText(MainActivity.this, "Deleted ", Toast.LENGTH_SHORT).show();
        ReadData();
    }
}

