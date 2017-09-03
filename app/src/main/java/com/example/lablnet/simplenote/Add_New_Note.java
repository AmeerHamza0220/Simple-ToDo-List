package com.example.lablnet.simplenote;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Add_New_Note extends DialogFragment {
    boolean isReminder = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add__new__note, null, false);
        mDialog.setView(view);
        final EditText edtTitle = (EditText) view.findViewById(R.id.edtTitle);
        final EditText edtDescription = (EditText) view.findViewById(R.id.edtDes);
        edtDescription.setMaxHeight(200);
        final CheckBox setReminder = (CheckBox) view.findViewById(R.id.Reminder);
        setReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isReminder = isChecked;

            }
        });
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnCancel = (Button) view.findViewById(R.id.buttonCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTitle = edtTitle.getText().toString();
                String mDescription = edtDescription.getText().toString();
                if (mTitle.replace(" ","").isEmpty() || mDescription.replace(" ","").isEmpty()) {
                    Toast.makeText(getActivity(), "Cannot save empty note", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                    Date d = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd yyy");
                    String mDate = simpleDateFormat.format(d);
                    final MainActivity activity = (MainActivity) getActivity();
                    dismiss();
                    if(isReminder){
                        Data_Model.setTitleForREminder(mTitle);
                        Data_Model.setDescriptionForReminder(mDescription);
                        Data_Model.setDataForReminder(mDate);
                        activity.ShowPickerDialog();
                    }
                    else {
                        databaseHelper.SaveData(mTitle, mDescription, mDate,null,null,0);
                    }
                    activity.ReadData();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return mDialog.create();
    }

}
