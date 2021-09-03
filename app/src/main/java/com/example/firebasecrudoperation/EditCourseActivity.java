package com.example.firebasecrudoperation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    private TextInputEditText CourseName,EditCoursePrice, EditCourseSuited, EditCourseImage,EditCourseLink,EditCourseDescription;
    private Button UpdateCourseButton, DeleteCourseButton;
    private ProgressBar CourseProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  String courseID;
    //varible for our class courseRVModal
    private CourseRVModal courseRVModal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        CourseName=findViewById(R.id.CourseName);
        EditCoursePrice=findViewById(R.id.EditCoursePrice);
        EditCourseSuited = findViewById(R.id.EditCourseSuited);
        EditCourseImage = findViewById(R.id.EditCourseImage);
        EditCourseLink= findViewById(R.id.EditCourseLink);
        EditCourseDescription = findViewById(R.id.EditCourseDescription);
        UpdateCourseButton=findViewById(R.id.UpdateCourseButton);
        DeleteCourseButton=findViewById(R.id.DeleteCourseButton);

        CourseProgressBar=findViewById(R.id.CourseProgressBar);
        firebaseDatabase=FirebaseDatabase.getInstance();

        courseRVModal=getIntent().getParcelableExtra("course");

        if(courseRVModal!=null)
        {
            CourseName.setText(courseRVModal.getCourse());
            EditCoursePrice.setText(courseRVModal.getPrice());
            EditCourseSuited.setText(courseRVModal.getSuited());
            EditCourseDescription.setText(courseRVModal.getCourseDescription());
            EditCourseImage.setText(courseRVModal.getImage());
            EditCourseLink.setText(courseRVModal.getCourseLink());
            courseID=courseRVModal.getCourseID();
        }
        databaseReference= firebaseDatabase.getReference("Courses").child(courseID);

        UpdateCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseProgressBar.setVisibility(View.VISIBLE);
                String Course= CourseName.getText().toString();
                String price=EditCoursePrice.getText().toString();
                String suited=EditCourseSuited.getText().toString();
                String image= EditCourseImage.getText().toString();
                String link= EditCourseLink.getText().toString();
                String description = EditCourseDescription.getText().toString();
                courseID=Course;
                Map<String, Object> map=new HashMap<>();
                map.put("Course",Course);
                map.put("price",price);
                map.put("suited",suited);
                map.put("image",image);
                map.put("courseLink",link);
                map.put("CourseDescription",description);
                map.put("courseID",courseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        CourseProgressBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditCourseActivity.this, "Edit Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        CourseProgressBar.setVisibility(View.GONE);
                        Toast.makeText(EditCourseActivity.this, "Failed to Update Course", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        DeleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCourse();
            }
        });
    }

    private void DeleteCourse()
    {
        databaseReference.removeValue();
        Toast.makeText(this, "Course Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));
    }
}