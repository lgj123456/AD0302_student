package com.example.yhdj.ad0302student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private Button mBtn_login;
    private EditText mEdt_username;
    private EditText mEdt_password;
    private final String TAG = "MainActivity";
    String url = "http://192.168.134.79:8080/Student/findAllStu2";
    private AllStudentListBean mAllStudentListBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {
        mBtn_login = (Button) findViewById(R.id.btn_login);
        mEdt_username = (EditText) findViewById(R.id.edi_username);
        mEdt_password = (EditText) findViewById(R.id.edi_password);
         OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        mAllStudentListBean  = gson.fromJson(response.toString(),AllStudentListBean.class);

                    }
                });
            }

}
