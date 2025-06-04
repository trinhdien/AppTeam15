package com.utc.asm_mob_java.dialog;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.dialog.dialogconfirm.ConfirmDialog;

import java.util.Calendar;

public class DialogUtils {
    public static void showErrDialog(AppCompatActivity activity, String err) {
        new ConfirmDialog(null, activity.getResources()
                .getString(R.string.error), err,
                true, "", activity.getResources().getString(R.string.ok)).show(activity.getSupportFragmentManager(), "");
    }

    public static void showConfirmDialog(BaseListener confirmListener, AppCompatActivity activity, String title, String mess) {
        new ConfirmDialog(confirmListener, title == null ? activity.getResources().getString(R.string.confirm) : title, mess).show(activity.getSupportFragmentManager(), "");
    }

    public static void showDatePickerDialog(Context context, OnDateSelectedListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePicker, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDayStr = String.valueOf(selectedDay);
            String selectedMonthStr = String.valueOf(selectedMonth + 1);
            if (selectedDay < 10) {
                selectedDayStr = "0" + selectedDayStr;
            }
            if ((selectedMonth + 1) < 10) {
                selectedMonthStr = "0" + selectedMonthStr;
            }
            String selectedDate = selectedDayStr + "/" + selectedMonthStr + "/" + selectedYear;
            listener.onDateSelected(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

}
