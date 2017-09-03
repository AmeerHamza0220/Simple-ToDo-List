package DateTimePicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.lablnet.simplenote.Data_Model;
import com.example.lablnet.simplenote.R;

import java.util.Calendar;


public class DatePickerFrag extends Fragment implements DatePicker.OnDateChangedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_date_picker, container, false);
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month= calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year,month, day, this);
        String mDate = day + " " + month + " " + year;
        sendDataToMainActivity(mDate);
        return v;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String mDate = dayOfMonth + " " + monthOfYear + " " + year;
        sendDataToMainActivity(mDate);

    }
    public void sendDataToMainActivity(String mDate){
        Data_Model.setPickerReminderDate(mDate);
    }
}

