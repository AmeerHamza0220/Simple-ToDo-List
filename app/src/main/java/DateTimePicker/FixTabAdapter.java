package DateTimePicker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FixTabAdapter extends FragmentPagerAdapter {
    int mPage_Count = 2;

    public FixTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DatePickerFrag();
            case 1:
                return new TimePickerFrag();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPage_Count;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Pick Date";
            case 1:
                return "Pick Time";
        }
        return null;
    }
}
