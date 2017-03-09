package com.example.chengboying.demo;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.chengboying.demo.adapter.NormalRecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private List<String> mDatas;
    NormalRecyclerViewAdapter recycleAdapter;
    private  boolean isSingleline=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
//        initData();
        recycleAdapter= new NormalRecyclerViewAdapter(MainActivity.this , mDatas );
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(MainActivity.this,mDatas));
        findViewById(R.id.change_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingleline=!isSingleline;
                ((NormalRecyclerViewAdapter)mRecyclerView.getAdapter()).changecount(isSingleline);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHub service = retrofit.create(GitHub.class);
        Call<List<Contributor>> call = service.listRepos("octocat");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try{
            Response<List<Contributor>> response = call.execute(); // 同步

            AdapterDataHelper.getInstance().setData(response.body());


            Log.d("hhh", "response:" + response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }



        // clone
        Call<List<Contributor>> call1 = call.clone();
// 5. 请求网络，异步

        call1.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
                AdapterDataHelper.getInstance().setData(response.body());
                Log.e("HHH","response:" + response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    public void initData(){
        mDatas = new ArrayList<String>();
        mDatas = new ArrayList<>();
        for(int i=0;i<20;i++){
            mDatas.add("Item "+i);
        }



    }
}
