package com.example.chengboying.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengboying.demo.adapter.NormalRecyclerViewAdapter;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSwipeLayout;
    private ArrayAdapter<String> mAdapter;
    private boolean isRefresh = false;//是否刷新中
    RecyclerView mRecyclerView;
    private ListView mListView;
    private List<String> mDatas;
    NormalRecyclerViewAdapter recycleAdapter;
    private  boolean isSingleline=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        //设置SwipeRefreshLayout
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
        //设置下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(this);
//        initData();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

//        recycleAdapter= new NormalRecyclerViewAdapter(MainActivity.this ,android.R.layout.simple_list_item_1, mDatas );
//        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(MainActivity.this,mDatas));
        //初始化adapter
        recycleAdapter = new NormalRecyclerViewAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mDatas);
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mDatas));
        //mListView.setAdapter(mAdapter);

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

//        call1.enqueue(new Callback<List<Contributor>>() {
//            @Override
//            public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
//                AdapterDataHelper.getInstance().setData(response.body());
//                Log.e("HHH","response:" + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });
    }


//    public void initData(){
//        mDatas = new ArrayList<String>();
//        mDatas = new ArrayList<>();
//        for(int i=0;i<20;i++){
//            mDatas.add("Item "+i);
//        }
//
//
//
//    }
    public void onRefresh() {
        //检查是否处于刷新状态
        if (!isRefresh) {
            isRefresh = true;
            //模拟加载网络数据，这里设置4秒，正好能看到4色进度条
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    //显示或隐藏刷新进度条
                    mSwipeLayout.setRefreshing(false);
                    //修改adapter的数据
//                    mDatas.add("这是新添加的数据");
//                    mAdapter.notifyDataSetChanged();
                    isRefresh = false;
                }
            }, 4000);
        }
    }
}
