package com.demo.demoplayer.functionTwo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.demo.demoplayer.R;
import com.demo.demoplayer.utils.DemoConstants;
import com.demo.demoplayer.utils.Utilities;

/**
 * Created by Brandon Lee Roets on 2016/06/20.
 */
public class FunctionTwoMainActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final int ACTIVITY_FUNCTION_TWO_USER_REQUEST = 2;
    private Button btnSubmitName;
    private EditText edtUserName;
    private CoordinatorLayout clMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.function_two);

        setContentView(R.layout.activity_function_two_main_activity);
        btnSubmitName = (Button) findViewById(R.id.activity_functionTwo_btnSubmitName);
        edtUserName = (EditText) findViewById(R.id.activity_functionTwo_edtName);
        clMainLayout = (CoordinatorLayout) findViewById(R.id.activity_functionTwo_mainLayout);
        btnSubmitName.setOnClickListener(this);

        checkUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.activity_functionTwo_btnSubmitName:

                if (!edtUserName.getText().toString().isEmpty()) {

                    submitUser();
                } else {

                    Utilities.showSnackBar("Please Enter A Name!", clMainLayout);
                }

                break;
        }
    }

    public void submitUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.DialogOkTitle);
        builder.setMessage(R.string.DialogOkMessage);

        String positiveText = "Ok";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(DemoConstants.PREF_USERNAME, edtUserName.getText().toString()).commit();
                        edtUserName.setText("");
                        Intent intent = new Intent(getApplicationContext(), FunctionTwoUserActivity.class);
                        startActivityForResult(intent, ACTIVITY_FUNCTION_TWO_USER_REQUEST);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkUser() {

        if (PreferenceManager.getDefaultSharedPreferences(this).getString(DemoConstants.PREF_USERNAME, null) != null) {

            Intent intent = new Intent(this, FunctionTwoUserActivity.class);
            startActivityForResult(intent, ACTIVITY_FUNCTION_TWO_USER_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == DemoConstants.RESULT_CLOSE) {

            setResult(DemoConstants.RESULT_CLOSE);
            finish();
        } else if(resultCode == Activity.RESULT_CANCELED){

            finish();
        }
    }
}
