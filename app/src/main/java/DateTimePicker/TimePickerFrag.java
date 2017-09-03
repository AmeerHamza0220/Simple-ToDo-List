package DateTimePicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.lablnet.simplenote.Data_Model;
import com.example.lablnet.simplenote.R;


public class TimePickerFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_time_picker, container, false);
        TimePicker timePicker=(TimePicker)v.findViewById(R.id.timePicker);
        String time=timePicker.getCurrentMinute()+" "+timePicker.getCurrentHour();
        sendDataToManActivity(time);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
              String time=minute+" "+hourOfDay;
                sendDataToManActivity(time);
            }
        });
        return v;
    }
    public void sendDataToManActivity(String time){
        Data_Model.setPickerReminderTime(time);
    }
}
