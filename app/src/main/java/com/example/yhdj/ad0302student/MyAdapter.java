package com.example.yhdj.ad0302student;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yhdj on 2017/2/26.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
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
    }

    @Override
    public int getItemCount() {
        return 0;
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
