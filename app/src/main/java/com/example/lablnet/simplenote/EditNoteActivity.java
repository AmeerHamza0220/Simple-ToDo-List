package com.example.lablnet.simplenote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import DateTimePicker.DateTimeDialogFrag;

public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    int ID;
    boolean menuIsInflated, edittextAdded;
    String title, description, mTime;
    LinearLayout linearLayout1, linearLayout2;
    TextView txtTitle, txtDescription;
    EditText edtTitle;
    MainActivity act;
    TextView txtReminder;


    EditText edtDescription;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        toolbar = (Toolbar) findViewById(R.id.tBarEdit);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        menuIsInflated = false;
        txtReminder = (TextView) findViewById(R.id.txtReminder);
        final Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        description = intent.getStringExtra("Description");
        mTime = intent.getStringExtra("Time");
        ID = intent.getIntExtra("ID", 0);
        String mReminderDate = intent.getStringExtra("ReminderDate");
        String mReminderTime = intent.getStringExtra("ReminderTime");
        if (mReminderDate == null) {
            txtReminder.setText("There is not reminder");
        } else {
            txtReminder.setText("Reminder: " + mReminderDate + " at " + mReminderTime);
        }
        TextView txtTime = (TextView) findViewById(R.id.txtTime);
        txtTime.setText(mTime);
        LinearLayout.LayoutParams layoutparam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtTime.setTextSize(20);
        layoutparam.gravity = Gravity.RIGHT;
        layoutparam.bottomMargin = 16;
        txtTime.setLayoutParams(layoutparam);
        LinearLayout.LayoutParams layoutparamforTitle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutparamforTitle.bottomMargin = 25;
        linearLayout1 = (LinearLayout) findViewById(R.id.Linear1);
        linearLayout2 = (LinearLayout) findViewById(R.id.Linear2);
        setTextView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        if (edittextAdded) {
                            title = edtTitle.getText().toString();
                            description = edtDescription.getText().toString();
                        }
                        if (title.replace(" ", "").isEmpty() || description.replace(" ", "").isEmpty()) {
                            Toast.makeText(EditNoteActivity.this, "Cannot save empty note", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseHelper data = new DatabaseHelper(EditNoteActivity.this);
                            data.UpdateDatabase(title, description, ID);
                            Toast.makeText(EditNoteActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            act = Data_Model.getActivity();
                            if (Data_Model.id_FromWherePickerOpen == 1) {
                                act.setAlarm(Data_Model.getPickerReminderTime(), Data_Model.getPickerReminderDate(), title, description);
                            }
                            finish();
                            act.ReadData();
                        }

                }
                return false;
            }
        });
        txtReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateMenu();
                Data_Model.setId_FromWherePickerOpen(1);
                DateTimeDialogFrag dateTimeDialogFrag = new DateTimeDialogFrag();
                dateTimeDialogFrag.show(getSupportFragmentManager(), "222");
            }
        });
    }

    @Override
    public void onClick(View v) {
        ReplaceTxtWithEditText();
    }

    public void setTextView() {
        edittextAdded = false;
        LinearLayout.LayoutParams layoutparamforTitle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutparamforTitle.bottomMargin = 25;
        txtTitle = new TextView(this);
        txtTitle.setLayoutParams(layoutparamforTitle);
        txtDescription = new TextView(this);
        txtTitle.setText(title);
        txtTitle.setTextSize(24);
        txtTitle.setTypeface(Typeface.DEFAULT_BOLD);
        txtDescription.setText(description);
        txtDescription.setTextSize(18);
        txtDescription.setMovementMethod(new ScrollingMovementMethod());
        linearLayout1.addView(txtTitle);
        linearLayout2.addView(txtDescription);
        txtDescription.setOnClickListener(this);
        txtTitle.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            exitActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitActivity();
    }

    public void exitActivity() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
        finish();
    }

    public void afterPickerDialog() {
        act = Data_Model.getActivity();
        long mills = act.converTimeInMills(Data_Model.getPickerReminderTime(), Data_Model.getPickerReminderDate());
        String mTime = act.formattedDate(mills);
        String mDate = act.formattedTime(mills);
        txtReminder.setText("Reminder: " + mTime + " at " + mDate);
    }

    public void ReplaceTxtWithEditText() {
        edittextAdded = true;
        linearLayout1.removeView(txtTitle);
        linearLayout2.removeView(txtDescription);
        edtTitle = new EditText(EditNoteActivity.this);
        edtTitle.setText(title);
        linearLayout1.addView(edtTitle);
        edtDescription = new EditText(EditNoteActivity.this);
        edtDescription.setBackgroundColor(Color.TRANSPARENT);
        edtDescription.setText(description);
        linearLayout2.addView(edtDescription);
        edtTitle.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(edtTitle, InputMethodManager.SHOW_IMPLICIT);
        inflateMenu();
    }

    public void inflateMenu() {
        if (!menuIsInflated) {
            menuIsInflated = true;
            toolbar.setTitle("Edit");
            toolbar.inflateMenu(R.menu.toolbarsavebutton);
        }
    }
}
