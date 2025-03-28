package com.utc.asm_mob_java.dialog;

import android.app.DatePickerDialog;
import android.content.Context;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.dialog.dialogconfirm.ConfirmDialog;

import java.util.Calendar;

public class DialogUtils {
    public static ConfirmDialog showErrDialog(Context context, String err) {
        return new ConfirmDialog(null, context.getResources()
                .getString(R.string.error), err,
                true, "", context.getResources().getString(R.string.ok));
    }

    public static ConfirmDialog showConfirmDialog(BaseListener confirmListener, Context context, String title, String mess) {
        return new ConfirmDialog(confirmListener, title == null ? context.getResources().getString(R.string.confirm) : title, mess);
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
