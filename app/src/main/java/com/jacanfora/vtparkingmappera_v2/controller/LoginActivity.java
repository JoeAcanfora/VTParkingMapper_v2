package com.jacanfora.vtparkingmappera_v2.controller;

/**
 * Created by jacanfora on 8/22/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jacanfora.vtparkingmappera_v2.model.ApplicationSingleton;
import com.jacanfora.vtparkingmappera_v2.R;
import com.jacanfora.vtparkingmappera_v2.model.Credential;

import java.util.Date;

public class LoginActivity extends Activity {

    private EditText startDate;
    private EditText endDate;
    private Spinner credential;
    private Button submit;
    private SharedPreferences saver;

    @Override
    protected void onCreate(Bundle saverInstanceState) {
        super.onCreate(saverInstanceState);
        setContentView(R.layout.activity_login);
        saver = this.getSharedPreferences(((ApplicationSingleton)getApplication()).sharedPrefName,
                Context.MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            if (saver.contains("pass") && saver.contains("startDate") && saver.contains("endDate")) {

                setCredentials(saver.getString("pass", ""), saver.getString("startDate", ""),
                        saver.getString("endDate", ""));
            }
        }




        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        credential = (Spinner) findViewById(R.id.pass);

        startDate.setText("08/26/2016");
        endDate.setText("08/26/2017");

        String list[] = {"Resident", "Commuter", "Graduate", "Facility/Staff", "Bike Bus and Walk", "Visitor",
                "Carpool C/G", "Carpool F/S"};
        ArrayAdapter<String> passAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        credential.setAdapter(passAdapt);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                setCredentials(credential.getSelectedItem().toString(), startDate.getText().toString(),
                        endDate.getText().toString());
            }
        });
    }

    /**
     * Sets the credential variable in the ApplicationSingleton Class.
     */
    private void setCredentials(String credential, String startDate, String endDate) {
        if (startDate== ""
                || endDate == ""
                || credential == "") {
            Toast toasty = new Toast(this);
            toasty.setText("Please fill in all the forms");
            toasty.show();
            return;
        }
        Editor editor = saver.edit();
        editor.putString("pass", credential);
        editor.putString("startDate", startDate);
        editor.putString("endDate", endDate);
        editor.commit();

        Date s = new Date(startDate);
        Date e = new Date(endDate);
        String p = credential;//credential.getSelectedItem().toString();

        Credential pass = new Credential(s, e, p);
        ((ApplicationSingleton)(this.getApplication())).setPass(pass);
        Intent toMap = new Intent(this, MapsActivity.class);
        startActivity(toMap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
