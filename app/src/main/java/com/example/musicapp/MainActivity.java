package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicapp.MyService;
import com.example.musicapp.R;


public class MainActivity extends AppCompatActivity {

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView btntatam = findViewById(R.id.btnTatam);
        final ImageView btOn = findViewById(R.id.btnplay);
        final ImageView btOff =  findViewById(R.id.btnOff);

//        final Button btFast = (Button) findViewById(R.id.btTua);

        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                
                MyService.MyBinder binder = (MyService.MyBinder) service;
                myService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };

        // Khởi tạo intent
        final Intent intent =
                new Intent(MainActivity.this,
                        MyService.class);

        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bắt đầu một service sủ dụng bin
                bindService(intent, connection,
                        Context.BIND_AUTO_CREATE);

                // Đối thứ ba báo rằng Service sẽ tự động khởi tạo
            }

        });
        btntatam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBound){
                    myService.muted();
                    Toast.makeText(myService, "muted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btOff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Nếu Service đang hoạt động
                if(isBound){
                    Toast.makeText(myService, "Tat nghe nhac", Toast.LENGTH_SHORT).show();
                    // Tắt Service
                    unbindService(connection);
                    isBound = false;
                }
            }
        });
        findViewById(R.id.btnSkipNext).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                Toast.makeText(myService, "Chuyen bai", Toast.LENGTH_SHORT).show();
                myService.next();
            }
        });

        findViewById(R.id.btForward).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if(isBound){
                    Toast.makeText(myService, "Tua ve cuoi bai nhac", Toast.LENGTH_SHORT).show();
                    // tua bài hát
                    myService.fastForward();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound){
                    // tua bài hát
                    Toast.makeText(myService, "Tua ve dau bai nhac", Toast.LENGTH_SHORT).show();
                    myService.fastStart();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        findViewById(R.id.tvx2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isBound){
//                    // tua bài hát
//                    Toast.makeText(myService, "Tua ve dau bai nhac", Toast.LENGTH_SHORT).show();
//                    myService.
//                }else{
//                    Toast.makeText(MainActivity.this,
//                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}