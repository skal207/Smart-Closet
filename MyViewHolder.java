package com.example.mycloset;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView icon;
    TextView title;
    TextView desc;

    public MyViewHolder(View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon);
        title = itemView.findViewById(R.id.title);
        desc = itemView.findViewById(R.id.desc);
    }
}

class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<MyData> dataList; // MyData는 SQLite에서 가져온 데이터의 모델 클래스

    public MyAdapter(List<MyData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyData data = dataList.get(position);

        Drawable imageDrawable = loadImageFromPath(data.getIconPath());
        holder.icon.setImageDrawable(imageDrawable);
        holder.title.setText(data.getTitle()); // 제목 설정
        holder.desc.setText(data.getDesc()); // 설명 설정
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private Drawable loadImageFromPath(String imagePath) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                return new BitmapDrawable(Resources.getSystem(), bitmap);
            } else {
                // 이미지 로드에 실패한 경우
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

class MyData {
    private String iconPath; // 이미지 경로를 저장
    private String title;
    private String desc;

    public MyData(String iconPath, String title, String desc) {
        this.iconPath = iconPath;
        this.title = title;
        this.desc = desc;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}





