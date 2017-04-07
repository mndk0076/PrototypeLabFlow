package vxestate.prototypelabflow;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class TimeSlotActivity extends AppCompatActivity {
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    String NAME, STUDENT_NUM;
    String DATE;

    String[] slot = {"Slot 1:", "Slot 2:", "Slot 3:"};
    String[] times = {"8:00 AM - 9:45 AM", "9:50 AM - 11:35 AM", "11:40 AM - 12:25 PM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);

        final MaterialCalendarView materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2016, 12, 31))
                .setMaximumDate(CalendarDay.from(2200, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TimeSlotActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_schedule, null);

                ListView listView = (ListView)view.findViewById(R.id.Listview);
                CustomAdapter customAdapter = new CustomAdapter();
                listView.setAdapter(customAdapter);

                mBuilder.setView(view);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                DATE = simpleDateFormat.format(date.getDate());
            }
        });

        mdrawerLayout = (DrawerLayout)findViewById(R.id.activity_time_slot);
        mDrawerToggle = new ActionBarDrawerToggle(this, mdrawerLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mdrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case R.id.nav_request:
                        Intent requestIntent = new Intent(getApplicationContext(), MyAppointmentActivity.class);
                        startActivity(requestIntent);
                        break;
                    case R.id.nav_scan:
                        Intent qrIntent = new Intent(getApplicationContext(), ScanQRActivity.class);
                        startActivity(qrIntent);
                        break;
                    case R.id.nav_schedule:
                        Intent scheduleIntent = new Intent(getApplicationContext(), TimeSlotActivity.class);
                        startActivity(scheduleIntent);
                        break;
                    case R.id.nav_about:
                        Intent usIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
                        startActivity(usIntent);
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return slot.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.slot_available, null);
            TextView textView_name = (TextView)view.findViewById(R.id.slot);
            TextView textView_description = (TextView)view.findViewById(R.id.myAppointment);
            Button btnBook = (Button)view.findViewById(R.id.bookBtn);

            textView_name.setText(slot[i]);
            textView_description.setText(times[i]);

            btnBook.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent scheduleIntent = new Intent(getApplicationContext(), BookTimeActivity.class);
                    Bundle scheduleBundle = new Bundle();
                    scheduleBundle.putString("date", DATE);
                    scheduleBundle.putString("time", times[i]);
                    scheduleIntent.putExtras(scheduleBundle);
                    startActivity(scheduleIntent);

                }
            });
            return view;
        }
    }
}
