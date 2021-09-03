package com.example.firebasecrudoperation;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {
    private ArrayList<CourseRVModal> courseRVModalArrayList;
    private Context context;
    int lastPos=-1;
    private CourseClickInterface courseClickInterface;

    public CourseRVAdapter(ArrayList<CourseRVModal> courseRVModalArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.courseRVModalArrayList = courseRVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }
    //Done till here

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position) {
        CourseRVModal courseRVModal=courseRVModalArrayList.get(position);
        holder.CourseName.setText(courseRVModal.getCourse());
        holder.CoursePrice.setText(courseRVModal.getPrice());
        Picasso.get().load(courseRVModal.getImage()).into(holder.CourseImage);
        setAnimation(holder.itemView,position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseClickInterface.onCourseClick(position);
            }
        });

    }
    private void setAnimation(View view, int position)
    {
        if(position>lastPos)
        {
            Animation animation= AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            view.setAnimation(animation);
            lastPos=position;
        }
    }

    @Override
    public int getItemCount() {
        return courseRVModalArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView CourseName,CoursePrice;
        private ImageView CourseImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CourseName=itemView.findViewById(R.id.CourseName);
            CoursePrice=itemView.findViewById(R.id.CoursePrice);
            CourseImage=itemView.findViewById(R.id.CourseImage);

        }
    }

    public  interface CourseClickInterface
    {
        void onCourseClick(int position);
    }
}
