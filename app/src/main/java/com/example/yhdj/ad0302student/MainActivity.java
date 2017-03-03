package com.example.yhdj.ad0302student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private final String TAG = "MainActivity";
    private final String ALL_STU_URL = "http://192.168.134.79:8080/Student/findAllStu2";
    private final String DEL_URL = "http://192.168.134.79:8080/Student/deleteStu";
    private final String INSERT_URL = "http://192.168.134.79:8080/Student/insertStu";
    private final String UPDATE_URL = "http://192.168.134.79:8080/Student/updateStu";
    private AllStudentListBean mAllStudentListBean;
    private MyAdapter mMyAdapter;
    private Button mbtn_insert;
    private Button mbtn_del;
    private Button mbtn_update;
    private EditText medt_id;
    private EditText medt_name;
    private EditText medt_age;
    private EditText medt_sex;
    private int id;
    private String name;
    private int age;
    private String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mbtn_insert = (Button) findViewById(R.id.btn_insert);
        mbtn_del = (Button) findViewById(R.id.btn_del);
        mbtn_update = (Button) findViewById(R.id.btn_update);
        medt_id = (EditText) findViewById(R.id.edt_id);
        medt_age = (EditText) findViewById(R.id.edt_age);
        medt_sex = (EditText) findViewById(R.id.edt_sex);
        medt_name = (EditText) findViewById(R.id.edt_name);

        getAllStu();
        mbtn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContent();
                insertStu(id, name, age, sex);
                clearEdt();
            }
        });

        mbtn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStu(id, name, age, sex);
            }
        });

        //删除
        mbtn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(medt_id.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity.this, "id不能为空！！！", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = Integer.parseInt(medt_id.getText().toString().trim());
                OkHttpUtils.get().url(DEL_URL).addParams("id",id+"").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                       //更新列表
                        getAllStu();
                    }
                });

            }
        });

        clearEdt();

    }

    private void clearEdt() {
        medt_id.setText("");
        medt_name.setText("");
        medt_sex.setText("");
        medt_age.setText("");
    }

    private void getContent() {


        if (medt_id.getText().toString().trim().isEmpty() || medt_name.getText().toString().trim().isEmpty() ||
                medt_age.getText().toString().trim().isEmpty() || medt_sex.getText().toString().trim().isEmpty()
                ) {
            Toast.makeText(MainActivity.this, "id,name,age,sex不能为空！！！", Toast.LENGTH_SHORT).show();
            return;
        }
        id = Integer.parseInt(medt_id.getText().toString().trim());
        name = medt_name.getText().toString().trim();
        age = Integer.parseInt(medt_age.getText().toString().trim());
        sex = medt_sex.getText().toString().trim();
    }

    //更新
    private void updateStu(int id, String name, int age, String sex) {
        getContent();
        Map params = new HashMap();
        params.put("id", id+"");
        params.put("name", name);
        params.put("age", age+"");
        params.put("sex", sex);

        OkHttpUtils.get().url(UPDATE_URL).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                getAllStu();
            }
        });
        clearEdt();
    }

    private void insertStu(int id, String name, int age, String sex) {


        Map params = new HashMap();
        params.put("id", id+"");
        params.put("name", name);
        params.put("age", age+"");
        params.put("sex", sex);

        OkHttpUtils.get().url(INSERT_URL).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                getAllStu();
            }
        });


    }

    private void getAllStu() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        OkHttpUtils.get().url(ALL_STU_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d(TAG, "onError: " + e.getMessage());

            }

            @Override
            public void onResponse(String response, int id) {
                Log.d(TAG, "onResponse: " + response.toString());
                Gson gson = new Gson();
                mAllStudentListBean = gson.fromJson(response.toString(), AllStudentListBean.class);
                mMyAdapter = new MyAdapter(mAllStudentListBean);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(mMyAdapter);
                mMyAdapter.notifyDataSetChanged();
            }
        });
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private AllStudentListBean mAllStudentListBean;

        public MyAdapter(AllStudentListBean mAllStudentListBean) {
            this.mAllStudentListBean = mAllStudentListBean;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allstulist_item, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            final AllStudentListBean.StuListBean stuListBean = mAllStudentListBean.getStuList().get(position);
            holder.tv_id.setText(String.valueOf(stuListBean.getId()));
            holder.tv_name.setText(stuListBean.getName());
            holder.tv_age.setText(String.valueOf(stuListBean.getAge()));
            holder.tv_sex.setText(stuListBean.getSex());
            holder.head_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent(MainActivity.this,AllimageList.class);
                    intent.putExtra("id",stuListBean.getId());
                    startActivity(intent);

                }
            });
            String url = stuListBean.getHeadimg();
            Glide.with(MainActivity.this).load(url).into(holder.head_img);
        }

        @Override
        public int getItemCount() {
            return mAllStudentListBean.getStuList().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView head_img;
            private TextView tv_id;
            private TextView tv_name;
            private TextView tv_age;
            private TextView tv_sex;

            public ViewHolder(View itemView) {
                super(itemView);
                head_img = (ImageView) itemView.findViewById(R.id.head_img);
                tv_id = (TextView) itemView.findViewById(R.id.tv_id);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_age = (TextView) itemView.findViewById(R.id.tv_age);
                tv_sex = (TextView) itemView.findViewById(R.id.tv_sex);
            }
        }
    }


}
