package com.example.costall;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    Calendar mCalendar;
    int mCurrentYear, mCurrentMonth, mCurrentDay;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mCalendar = Calendar.getInstance();
        mCurrentYear = mCalendar.get(Calendar.YEAR);
        mCurrentMonth = mCalendar.get(Calendar.MONTH);
        mCurrentDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getContext(),(DatePickerDialog.OnDateSetListener) getContext(),mCurrentYear,mCurrentMonth,mCurrentDay);
    }
}
