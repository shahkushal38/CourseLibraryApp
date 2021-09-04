package com.example.firebasecrudoperation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  CourseRVAdapter.CourseClickInterface {

    private RecyclerView courses;
    private ProgressBar displayProgress;
    private FloatingActionButton AddButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<CourseRVModal> courseArrayList;
    private RelativeLayout bottomSheet;
    private FirebaseAuth mAuth;
    private CourseRVAdapter courseRVAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courses=findViewById(R.id.courses);
        displayProgress=findViewById(R.id.displayProgress);
        AddButton=findViewById(R.id.AddButton);
        bottomSheet=findViewById(R.id.bottomSheet);
        mAuth=FirebaseAuth.getInstance();
        //relative=findViewById(R.id.relative);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Courses");
        courseArrayList = new ArrayList<>();

        courseRVAdapter=new CourseRVAdapter(courseArrayList,this,this);

        courses.setLayoutManager(new LinearLayoutManager(this));
        courses.setAdapter(courseRVAdapter);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddCourseActivity.class));
            }
        });

        getAllCourses();
    }

    public void getAllCourses(){
        //read all courses from database

        courseArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                displayProgress.setVisibility(View.GONE);
                courseArrayList.add(snapshot.getValue(CourseRVModal.class));
                courseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                displayProgress.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                displayProgress.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                displayProgress.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onCourseClick(int position) {
        displayBottomSheet(courseArrayList.get(position));

    }
    private void displayBottomSheet(CourseRVModal courseRVModal)
    {
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View layout= LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, bottomSheet);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView bottomCourseName, bottomDescription, bottomSuited, bottomPrice;
        ImageView bottomImage;
        Button bottomEditButton, bottomViewDetails;

        bottomCourseName=layout.findViewById(R.id.bottomCourseName);
        bottomDescription=layout.findViewById(R.id.bottomDescription);
        bottomSuited=layout.findViewById(R.id.bottomSuited);
        bottomPrice=layout.findViewById(R.id.bottomPrice);
        bottomImage = layout.findViewById(R.id.bottomImage);
        bottomEditButton=layout.findViewById(R.id.bottomEditButton);
        bottomViewDetails=layout.findViewById(R.id.bottomViewDetails);

        bottomCourseName.setText(courseRVModal.getCourse());
        bottomDescription.setText(courseRVModal.getCourseDescription());
        bottomSuited.setText(courseRVModal.getSuited());
        bottomPrice.setText("Rs "+courseRVModal.getPrice());

        Picasso.get().load(courseRVModal.getImage()).into(bottomImage);

        bottomEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, EditCourseActivity.class);
                i.putExtra("course", courseRVModal);
                startActivity(i);
            }
        });

        bottomViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(courseRVModal.getCourseLink()));
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.logout:
                Toast.makeText(this, "User Logout Succesfull", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}