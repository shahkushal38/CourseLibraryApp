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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity {
    private TextInputEditText CourseName,EditCoursePrice, EditCourseSuited, EditCourseImage,EditCourseLink,EditCourseDescription;
    private Button AddCourseButton;
    private ProgressBar CourseProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  String courseID;
    //private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        CourseName=findViewById(R.id.CourseName);
        EditCoursePrice=findViewById(R.id.EditCoursePrice);
        EditCourseSuited = findViewById(R.id.EditCourseSuited);
        EditCourseImage = findViewById(R.id.EditCourseImage);
        EditCourseLink= findViewById(R.id.EditCourseLink);

        EditCourseDescription = findViewById(R.id.EditCourseDescription);

        AddCourseButton=findViewById(R.id.AddCourseButton);

        CourseProgressBar=findViewById(R.id.CourseProgressBar);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Courses");

        AddCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Course= CourseName.getText().toString();
                String price=EditCoursePrice.getText().toString();
                String suited=EditCourseSuited.getText().toString();
                String image= EditCourseImage.getText().toString();
                String link= EditCourseLink.getText().toString();
                String description = EditCourseDescription.getText().toString();
                courseID=Course;
                CourseRVModal courseRVModal=new CourseRVModal(Course,description,suited,price,image,link,courseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(courseID).setValue(courseRVModal);
                        Toast.makeText(AddCourseActivity.this, "Course Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCourseActivity.this, MainActivity.class));
                        

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCourseActivity.this, "Error is :"+ error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}