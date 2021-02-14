package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager;
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter;
import com.michalsvec.singlerowcalendar.utils.DateUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class JurnalActivity extends AppCompatActivity {

    ImageView back;
    private Calendar calendar = Calendar.getInstance();
    private int currentMonth = 0;
    TextView tv_date_calendar_item, tv_day_calendar_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal);

        //back = findViewById(R.id.iv_back);
        //back.setOnClickListener(view -> onBackPressed());

        calendar = Calendar.getInstance(TimeZone.getDefault());
        currentMonth = calendar.get(Calendar.MONTH);

        CalendarViewManager calendarViewManager = new CalendarViewManager() {
            @Override
            public int setCalendarViewResourceId(int i, @NotNull Date date, boolean isSelected) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                // if item is selected we return this layout items
                // in this example monday, wednesday and friday will have special item views and other days
                // will be using basic item view
                if (isSelected) {
                    switch (cal.get(Calendar.DAY_OF_WEEK)) {
                        case Calendar.MONDAY:
                            return R.layout.first_special_selected_calendar_item;
                        case Calendar.WEDNESDAY:
                            return R.layout.second_special_selected_calendar_item;
                        case Calendar.FRIDAY:
                            return R.layout.third_special_selected_calendar_item;
                        default:
                            return R.layout.selected_calendar_item;
                    }
                } else {
                    switch (cal.get(Calendar.DAY_OF_WEEK)) {
                        case Calendar.MONDAY:
                            return R.layout.first_special_calendar_item;
                        case Calendar.WEDNESDAY:
                            return R.layout.second_special_calendar_item;
                        case Calendar.FRIDAY:
                            return R.layout.third_special_calendar_item;
                        default:
                            return R.layout.calendar_item;
                    }
                }
            }

            @Override
            public void bindDataToCalendarView(@NotNull SingleRowCalendarAdapter.CalendarViewHolder calendarViewHolder, @NotNull Date date, int i, boolean b) {
                tv_date_calendar_item = calendarViewHolder.itemView.findViewById(R.id.tv_date_calendar_item);
                //DateUtils dateUtils = new DateUtils().getDayName(date);
                //tv_date_calendar_item.setText(dateUtils.getDayName(date));
                //calendarViewHolder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date);
            }
        };
    }
}
