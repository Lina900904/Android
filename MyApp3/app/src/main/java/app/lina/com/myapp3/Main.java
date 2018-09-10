package app.lina.com.myapp3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;

public class Main extends AppCompatActivity {
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
            class MyDate {String year, month, day;}
        final Context ctx = Main.this;

        TextView today = findViewById(R.id.today);
        CalendarView calender = findViewById(R.id.calender);
        TimePicker time =  findViewById(R.id.time);
        TextView hour = findViewById(R.id.hour);
        TextView minute = findViewById(R.id.minute);
        TextView year = findViewById(R.id.year);
        TextView month = findViewById(R.id.month);
        TextView day = findViewById(R.id.day);
        final MyDate m = new MyDate();

        TextView now = findViewById(R.id.now);
        now.setText("현재시간  "+new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date()));


        findViewById(R.id.rdoCalendar).setOnClickListener((View v)->{
            calender.setVisibility(View.VISIBLE);
            time.setVisibility(View.INVISIBLE);
        });
        findViewById(R.id.rdoTime).setOnClickListener((View v)->{
            calender.setVisibility(View.INVISIBLE);
            time.setVisibility(View.VISIBLE);
        });
        findViewById(R.id.btnEnd).setOnClickListener((View v) -> {
            year.setText(m.year);
            month.setText(m.month);
            day.setText(m.day);
            hour.setText(time.getHour()+"");
            minute.setText(time.getMinute()+"");
            Toast.makeText(ctx,"하이",Toast.LENGTH_LONG).show();
        });
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date =year+"-"+month+"-"+dayOfMonth+"-"+time.getHour()+"-"+time.getMinute();
                Toast.makeText(ctx,"날짜값"+date,Toast.LENGTH_LONG).show();
                m.year= year+"";
                m.month= month+"";
                m.day= dayOfMonth+"";
            }
        });




    }
}
