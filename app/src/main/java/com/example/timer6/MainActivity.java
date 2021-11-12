package com.example.timer6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvTimer;
    private int seconds = 0;
    private boolean isRunning = false;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTimer = findViewById(R.id.tvTimer);
        ///////
        if (savedInstanceState!= null) {
            seconds = savedInstanceState.getInt("seconds", seconds);
            isRunning = savedInstanceState.getBoolean("isRunning", isRunning);
        }
        runTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = isRunning;
        isRunning = false;
    }

    //помогает сохранять данные при повороте экрана
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds",seconds);
        outState.putBoolean("isRunning",isRunning);
    }

    public void setStart(View view) {
        isRunning = true;
    }

    public void setPause(View view) {
        isRunning = false;
    }

    public void setReset(View view) {
        isRunning = false;
        seconds = 0;
    }

    //создаем метод для обновления показаний секундомера
    private void runTimer() {
        final Handler handler = new Handler();
        //создаем анонимный класс new Runnable
        handler.post(new Runnable() {//выполниться только один раз
            @Override
            public void run() {
                int hours = seconds / 3600;//секунды в часы
                int minutes = (seconds % 3600) / 60;//оставшиеся секунды в минуты
                int secs = seconds % 60;// seconds

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
                tvTimer.setText(time);
                //когда таймер запущен - увеличить число секунд на 1
                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });

    }
}