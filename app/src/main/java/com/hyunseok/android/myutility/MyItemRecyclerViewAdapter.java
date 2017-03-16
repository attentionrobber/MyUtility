package com.hyunseok.android.myutility;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private List<String> datas;

    public MyItemRecyclerViewAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_five, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.imageUri = datas.get(position);
        //holder.imageView.setImageURI(holder.imageUri);
        Glide.with(context).load(holder.imageUri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public String imageUri;

        public ViewHolder(View view) {
            super(view);
            imageUri = null;

            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);

            imageView = (ImageView) view.findViewById(R.id.imageView);
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) imageView.getLayoutParams();
            params.width = (int) (metrics.widthPixels / 3);
            params.height = (int) (metrics.widthPixels / 3);  // 가로세로 길이 동일하게 하려고 여기도 widthPixels 넣어줌

            imageView.setLayoutParams(params);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭시 큰이미지로 보여주기
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("image", imageUri);
                    context.startActivity(intent);
                }
            });
        }
    }
}
