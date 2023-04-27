package com.android.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.aphy.caldavsyncadapter.android.entities.AccountName;
import com.aphy.caldavsyncadapter.authenticator.AuthenticatorActivity;
import com.aphy.provider.UserDBHelper;

import java.util.List;

import ch.punkt.mp02.dailyview.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isLogined()) {
            try {Thread.sleep(300);} catch (Exception e) {e.printStackTrace();} // duration for splash logo to display, otherwise too short, only when not logged in yet
            setContentView(R.layout.activity_login_sync);
            findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, AuthenticatorActivity.class));
                    finish();
                }
            });
        } else {
            startActivity(new Intent(this, AuthenticatorActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private boolean isLogined() {
        userDBHelper = UserDBHelper.getInstance(this, 1);
        try {
            userDBHelper.openWriteLink();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<AccountName> accountNameList = userDBHelper.queryAccount("1=1");
        return accountNameList.size() != 0;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        if (action == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    startActivity(new Intent(this, AuthenticatorActivity.class));
                    finish();
                    return true;
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}