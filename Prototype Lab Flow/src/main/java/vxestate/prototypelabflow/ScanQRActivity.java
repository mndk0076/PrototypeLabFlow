package vxestate.prototypelabflow;
//Kenneth Mendoza(N00598007)
//Sukhdeep Sehra (N01046228)
//Matheus Almeida (N00739768)
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ScanQRActivity extends AppCompatActivity {

    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    TextView qrDisplay, student_num, test;
    String Student_Num;
    EditText text;
    Button gen_btn;
    ImageView image;
    String text2Qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        //qrDisplay = (TextView)findViewById(R.id.qrDisplay);





        student_num = (TextView)findViewById(R.id.student_num);
        Bundle bundle = getIntent().getExtras();
        student_num.setText(bundle.getString("student_num"));

        Student_Num = student_num.getText().toString();

        image = (ImageView) findViewById(R.id.image);


        text2Qr = Student_Num;
                //student_num.getText().toString().trim();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }


        mdrawerLayout = (DrawerLayout)findViewById(R.id.activity_scan_qr);
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
                        Bundle requestBundle = new Bundle();
                        requestBundle.putString("student_num", Student_Num);
                        requestIntent.putExtras(requestBundle);
                        startActivity(requestIntent);
                        break;
                    case R.id.nav_scan:
                        Intent qrIntent = new Intent(getApplicationContext(), ScanQRActivity.class);
                        Bundle qrBundle = new Bundle();
                        qrBundle.putString("student_num", Student_Num);
                        qrIntent.putExtras(qrBundle);
                        startActivity(qrIntent);
                        break;
                    case R.id.nav_about:
                        Intent usIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
                        Bundle usBundle = new Bundle();
                        usBundle.putString("student_num", Student_Num);
                        usIntent.putExtras(usBundle);
                        startActivity(usIntent);
                        break;
                    case R.id.nav_schedule:
                        Intent scheduleIntent = new Intent(getApplicationContext(), TimeSlotActivity.class);
                        Bundle scheduleBundle = new Bundle();
                        scheduleBundle.putString("student_num", Student_Num);
                        scheduleIntent.putExtras(scheduleBundle);
                        startActivity(scheduleIntent);
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

}