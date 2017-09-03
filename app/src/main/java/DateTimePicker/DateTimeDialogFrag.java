package DateTimePicker;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lablnet.simplenote.Data_Model;
import com.example.lablnet.simplenote.EditNoteActivity;
import com.example.lablnet.simplenote.MainActivity;
import com.example.lablnet.simplenote.R;

public class DateTimeDialogFrag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View view = inflater.inflate(R.layout.fragment_date_time_dialog, container);
        FixTabAdapter pagerAdapter=new FixTabAdapter(getChildFragmentManager());
        ViewPager viewPager=(ViewPager)view.findViewById(R.id.ViewPagerl);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.sliding_tabs);
        tabLayout.setBackgroundColor(Color.parseColor("#e0f2f1"));
        tabLayout.setupWithViewPager(viewPager);
        Button btn=(Button)view.findViewById(R.id.buttonOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= Data_Model.getId_FromWherePickerOpen();
                if(id==0){
                    saveMainActivity();
                }
                else if(id==1){
                    saveEditNoteActiity();
                }
            }
        });
        return view;
    }
    public void saveEditNoteActiity(){
        EditNoteActivity editNoteActivity=(EditNoteActivity)getActivity();
        editNoteActivity.afterPickerDialog();
        dismiss();
    }
    public void saveMainActivity(){
        MainActivity mainActivity=(MainActivity)getActivity();
        mainActivity.saveData();
        dismiss();
    }
}
