package com.demo.demoplayer.functionTwo;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.demoplayer.R;
import com.demo.demoplayer.utils.DemoConstants;

/**
 * Created by Brandon Lee Roets on 2016/06/20.
 */
public class FunctionTwoUserActivity extends AppCompatActivity implements View.OnClickListener {

    public String user;
    private TextView lblWelcomeMessage;
    private Button btnDeleteUser;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_two_user_activity);
        setTitle(R.string.function_two_user);

        user = PreferenceManager.getDefaultSharedPreferences(this).getString(DemoConstants.PREF_USERNAME, "Unknown");
        lblWelcomeMessage = (TextView) findViewById(R.id.activity_functionTwo_user_lblWelcomeMessage);
        btnDeleteUser = (Button) findViewById(R.id.activity_functionTwo_user_btnDeleteUser);
        btnClose = (Button) findViewById(R.id.activity_functionTwo_user_btnCloseApp);

        btnDeleteUser.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        checkVisitation();
    }

    public void checkVisitation() {

        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(DemoConstants.PREF_USER_VISITED, false)) {

            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(DemoConstants.PREF_USER_VISITED, true).commit();
            lblWelcomeMessage.setText(getString(R.string.functionTwo_user_activity_welcome, user));
        } else {

            lblWelcomeMessage.setText(getString(R.string.functionTwo_user_activity_welcome_back, user));
        }
    }

    @Override

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_functionTwo_user_btnDeleteUser:

                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean(DemoConstants.PREF_USER_VISITED, false)
                        .putString(DemoConstants.PREF_USERNAME, null)
                        .commit();

                setResult(Activity.RESULT_OK);
                finish();

                break;

            case R.id.activity_functionTwo_user_btnCloseApp:

                setResult(DemoConstants.RESULT_CLOSE);
                finish();

                break;
        }
    }

    @Override
    public void onBackPressed() {

        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
