package com.example.chengboying.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chengboying.demo.AdapterDataHelper;
import com.example.chengboying.demo.R;

import java.util.List;

/**
 * Created by chengboying on 2017/2/9.
 */

public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder>{
//    private final LayoutInflater mLayoutInflater;
    private List<String> mDatas;
    private Context mContext;
    private int count=9;

    public NormalRecyclerViewAdapter(Context context, List<String> datas) {
//        mLayoutInflater = LayoutInflater.from(context);
        this. mContext=context;
        mDatas=datas;

    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
//        View view = mLayoutInflater.inflate(R.layout. item_text,parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text,parent,false);
        NormalTextViewHolder holder= new NormalTextViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
//        holder.setShow();
//        holder.setName();
//        holder.textView.setText( mDatas.get(position));
        holder.textView.setText(AdapterDataHelper.getInstance().getData().get(position));
    }

    @Override
    public int getItemCount() {
        return  AdapterDataHelper.getInstance().getData().size();
//        return mDatas.size();
//        return  count;
    }

    public void changecount(boolean single){
        count=single?3:9;
        notifyDataSetChanged();


    }

    class NormalTextViewHolder extends RecyclerView.ViewHolder {
//        SimpleDraweeView draweeView;
        TextView textView;
        NormalTextViewHolder(View view) {
            super(view);

//             draweeView = (SimpleDraweeView) view.findViewById(R.id.my_image_view);
             textView = (TextView) view.findViewById(R.id.name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }

    }
}
