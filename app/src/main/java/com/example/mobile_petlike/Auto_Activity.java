package com.example.mobile_petlike;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Auto_Activity extends AppCompatActivity {

    EditText login, password;
    Button btn_login;

    TextView register;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);

        register = (TextView) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Auto_Activity.this, Register_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class checkLogin extends AsyncTask<String, String, String>{

        String z = null;
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            con = connectionClass(ConnectionClass.id.toString(), ConnectionClass.nm.toString(), ConnectionClass.lg.toString(), ConnectionClass.ps.toString());
            try{
                String sql = "SELECT * FROM Users WHERE Login = '"+login.getText()+"' AND Password = '"+password.getText()+"' ";
                Statement stat = con.CreateStatment();
                ResultSet rs = stat.executeQuery(sql);

                if (((ResultSet) rs).next()) {
                    Toast.makeText(Auto_Activity.this, "Login Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Auto_Activity.this, DataActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Auto_Activity.this, "Check email or password", Toast.LENGTH_LONG).show();
                    login.setText("");
                }
            }
            catch (Exception e)
            {
                isSuccess = false;
                Log.e("SQL Error: ", e.getMessage());
            }

            return z;
        }

    }

        @SuppressLint("NewApi")
        public Connection connectionClass(String ip, String database, String userName, String userPassword, String port){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Connection connection = null;
            String ConnectionURL = null;
            try{
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://"+ip+":"+port+";"+"databasename="+database+";user="+userName+";password="+userPassword+";";
                connection = DriverManager.getConnection(ConnectionURL);
            }
            catch (Exception ex)
            {
                Log.e("Error", ex.getMessage());
            }
            return connection;
        }
}