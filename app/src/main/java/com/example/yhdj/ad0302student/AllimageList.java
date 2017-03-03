package com.example.yhdj.ad0302student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class AllimageList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImgMyAdapter mImgMyAdapter;
    private Gson mGson;
    private final String IMG_URL = "http://192.168.134.79:8080/Student/Img";
    private AllImgListBean imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);


        initViews();

    }

    private void getImgs() {

    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        OkHttpUtils.get().url(IMG_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mGson = new Gson();
                imgs = mGson.fromJson(response.toString(),AllImgListBean.class);
                mImgMyAdapter = new ImgMyAdapter(imgs);
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(mImgMyAdapter);
                mImgMyAdapter.notifyDataSetChanged();
            }
        });
        Intent intent = getIntent();
        int StuId = intent.getIntExtra("id", 1);






    }


class ImgMyAdapter extends RecyclerView.Adapter<ImgMyAdapter.ViewHolder>{
    private AllImgListBean mAllImgListBean;
    public ImgMyAdapter(AllImgListBean mAllImgListBean){
        this.mAllImgListBean = mAllImgListBean;
    }

    @Override
    public ImgMyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImgMyAdapter.ViewHolder holder, int position) {

        AllImgListBean.ImgListBean imgListBean = mAllImgListBean.getImgList().get(position);
        Glide.with(AllimageList.this).load(imgListBean.getImg()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mAllImgListBean.getImgList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}


}
