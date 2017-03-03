package com.example.yhdj.ad0302student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

 private RecyclerView mRecyclerView;
    private final String TAG = "MainActivity";
    String url = "http://192.168.134.79:8080/Student/findAllStu2";
    private AllStudentListBean mAllStudentListBean;
    private MyAdapter mMyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {
      mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
                        mMyAdapter = new MyAdapter(mAllStudentListBean);
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(mMyAdapter);
                        mMyAdapter.notifyDataSetChanged();
                    }
                });
            }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private AllStudentListBean mAllStudentListBean;
        public MyAdapter(AllStudentListBean mAllStudentListBean){
            this.mAllStudentListBean = mAllStudentListBean;
        }
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allstulist_item,parent,false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            AllStudentListBean.StuListBean stuListBean = mAllStudentListBean.getStuList().get(position);
            holder.tv_id.setText(String.valueOf(stuListBean.getId()));
            holder.tv_name.setText(stuListBean.getName());
            holder.tv_age.setText(String.valueOf(stuListBean.getAge()));
            holder.tv_sex.setText(stuListBean.getSex());
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
