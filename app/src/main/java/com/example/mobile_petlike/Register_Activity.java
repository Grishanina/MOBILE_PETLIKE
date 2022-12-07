package com.example.mobile_petlike;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Register_Activity extends AppCompatActivity {

    EditText name, surname, login_reg, password_reg;
    Button btn_reg;
    TextView status;

    Connection con;
    Statement stat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        login_reg = (EditText)findViewById(R.id.login_reg);
        password_reg = (EditText)findViewById(R.id.password_reg);

        status = (TextView)findViewById(R.id.status);

        btn_reg =(Button) findViewById(R.id.btn_reg);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Register_Activity().registerUser().execute("");
            }
        });
    }

    public class registerUser extends AsyncTask<String, String, String> {

        String z = null;
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            status.setText("Sending Data to Database");
        }

        @Override
        protected void onPostExecute(String s){
            status.setText("Registration Successful");
            name.setText("");
            surname.setText("");
            login_reg.setText("");
            password_reg.setText("");
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                con = connectionClass(ConnectionClass.id.toString(), ConnectionClass.nm.toString(), ConnectionClass.lg.toString(), ConnectionClass.ps.toString());
                if(con==null){
                    z = "Check Your Internet Connection";
                }
                else{
                    String sql = "INSERT INTO Users (Name,Surname,Login,Password) VALUES ('"+name.getText()+"','"+surname.getText()+"','"+login_reg.getText()+"','"+password_reg.getText()+"')";
                    stat = con.createStatement();
                    stat.executeUpdate(sql);
                }
            }
            catch (Exception e)
            {
                isSuccess = false;
                z = e.getMessage();
            }

            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionClass(String ip, String database, String userName, String userPassword)
    {
        ip = "ngknn.ru";
        database = "PETLIKE";
        userName = "31П";
        userPassword = "12357";
        port = "1433";

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
            Log.e("SQL Connection Error", ex.getMessage());
        }
        return connection;
    }
}