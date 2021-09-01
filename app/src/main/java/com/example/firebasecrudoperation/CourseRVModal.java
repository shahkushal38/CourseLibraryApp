package com.example.firebasecrudoperation;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseRVModal implements Parcelable {
    private String Course;
    private  String CourseDescription;
    private  String suited;
    private String price;
    private String image;
    private  String courseLink;
    private String courseID;

    public CourseRVModal(){


    }

    public CourseRVModal(String course, String courseDescription, String suited, String price, String image, String courseLink, String courseID) {
        Course = course;
        CourseDescription = courseDescription;
        this.suited = suited;
        this.price = price;
        this.image = image;
        this.courseLink = courseLink;
        this.courseID = courseID;
    }

    protected CourseRVModal(Parcel in) {
        Course = in.readString();
        CourseDescription = in.readString();
        suited = in.readString();
        price = in.readString();
        image = in.readString();
        courseLink = in.readString();
        courseID = in.readString();
    }

    public static final Creator<CourseRVModal> CREATOR = new Creator<CourseRVModal>() {
        @Override
        public CourseRVModal createFromParcel(Parcel in) {
            return new CourseRVModal(in);
        }

        @Override
        public CourseRVModal[] newArray(int size) {
            return new CourseRVModal[size];
        }
    };

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        CourseDescription = courseDescription;
    }

    public String getSuited() {
        return suited;
    }

    public void setSuited(String suited) {
        this.suited = suited;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Course);
        dest.writeString(CourseDescription);
        dest.writeString(suited);
        dest.writeString(price);
        dest.writeString(image);
        dest.writeString(courseLink);
        dest.writeString(courseID);
    }
}
