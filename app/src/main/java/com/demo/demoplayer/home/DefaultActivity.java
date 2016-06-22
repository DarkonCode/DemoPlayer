package com.demo.demoplayer.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.demo.demoplayer.R;
import com.demo.demoplayer.functionOne.FunctionOneMainActivity;
import com.demo.demoplayer.functionTwo.FunctionTwoMainActivity;
import com.demo.demoplayer.utils.DemoConstants;

public class DefaultActivity extends AppCompatActivity implements Button.OnClickListener {

    private final int ACTIVITY_FUNCTION_ONE = 1;
    private final int ACTIVITY_FUNCTION_TWO = 2;
    private static final int ACTIVITY_FUNCTION_TWO_MAIN_REQUEST = 1;
    public Button btnFunctionOne;
    public Button btnFunctionTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnFunctionOne = (Button) findViewById(R.id.activity_home_btnFunctionOne);
        btnFunctionTwo = (Button) findViewById(R.id.activity_home_btnFunctionTwo);
        btnFunctionOne.setOnClickListener(this);
        btnFunctionTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View btn) {

        switch (btn.getId()) {

            case R.id.activity_home_btnFunctionOne:

                //go to function one
                goToActivity(ACTIVITY_FUNCTION_ONE);

                break;

            case R.id.activity_home_btnFunctionTwo:

                //go to function two
                goToActivity(ACTIVITY_FUNCTION_TWO);

                break;
        }
    }

    public void goToActivity(int activity) {

        Intent activityIntent = new Intent();

        if (activity == ACTIVITY_FUNCTION_ONE) {

            activityIntent.setClass(this, FunctionOneMainActivity.class);
            startActivity(activityIntent);

        } else if (activity == ACTIVITY_FUNCTION_TWO) {

            activityIntent.setClass(this, FunctionTwoMainActivity.class);
            startActivityForResult(activityIntent,ACTIVITY_FUNCTION_TWO_MAIN_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == DemoConstants.RESULT_CLOSE) {

            finish();
            System.exit(0);
        }
    }
}
