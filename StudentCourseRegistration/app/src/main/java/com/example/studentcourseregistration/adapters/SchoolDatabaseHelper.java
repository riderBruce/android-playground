package com.example.studentcourseregistration.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.studentcourseregistration.models.Course;
import com.example.studentcourseregistration.models.CourseEnrollmentData;
import com.example.studentcourseregistration.models.Student;

import java.util.ArrayList;
import java.util.List;

public class SchoolDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "school.db";
    private static final int DATABSER_VERSION = 1;
    private static final String TABLE_NAME_STUDENT = "students";
    private static final String TABLE_NAME_COURSE = "courses";
    public static final String COL_STUDENT_ID = "student_id";
    public static final String COL_STUDENT_NAME = "student_name";
    public static final String COL_STUDENT_EMAIL = "student_email";
    public static final String COL_COURSE_ID = "course_id";
    public static final String COL_COURSE_NAME = "course_name";

    public SchoolDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSER_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStudentsTable =
                "CREATE TABLE " + TABLE_NAME_STUDENT +
                        "(" + COL_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_STUDENT_NAME + " TEXT, " +
                        COL_STUDENT_EMAIL + " TEXT)";

        String createCourseTable =
                "CREATE TABLE " + TABLE_NAME_COURSE +
                        "(" + COL_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_COURSE_NAME + " TEXT, " +
                        COL_STUDENT_ID + " INTEGER, " +
                        "FOREIGN KEY(" + COL_STUDENT_ID + ") REFERENCES " +
                        TABLE_NAME_STUDENT + "(" + COL_STUDENT_ID +"))";

        db.execSQL(createStudentsTable);
        db.execSQL(createCourseTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COURSE);
        onCreate(db);
    }

    public boolean addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_NAME, student.getStudentName());
        values.put(COL_STUDENT_EMAIL, student.getEmail());
        long num = db.insert(TABLE_NAME_STUDENT, null, values);
        return num>0;
    }

    public boolean updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_NAME, student.getStudentName()+"@");
        values.put(COL_STUDENT_EMAIL, student.getEmail()+"%");

        String[] selectionArg = {String.valueOf(student.getId())};
        int u = db.update(TABLE_NAME_STUDENT, values, "student_id=?", selectionArg);
        return u>0;
    }

    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArg = {String.valueOf(id)};
        int d = db.delete(TABLE_NAME_STUDENT, "student_id=?", selectionArg);
        return d>0;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_STUDENT;
        return db.rawQuery(query, null);
    }

    public List<Student> getAllStudentsAsList() {
        List<Student> students = new ArrayList<>();

        try (Cursor cursor = this.getAllStudents()) {
            int idIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_ID);
            int studentNameIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_NAME);
            int studentEmailIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_EMAIL);

            while (cursor.moveToNext()) {
                students.add(new Student(
                        cursor.getInt(idIndex),
                        cursor.getString(studentNameIndex),
                        cursor.getString(studentEmailIndex)
                ));
            }
        }

        return students;
    }

    public Student getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_STUDENT + " WHERE id = ?";
        String[] selectionArg = {String.valueOf(id)};
        try (Cursor cursor = db.rawQuery(query, selectionArg)) {
            if (!cursor.moveToFirst()) {
                return null;
            }
            int idIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_ID);
            int studentNameIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_NAME);
            int studentEmailIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_EMAIL);

            return new Student(
                    cursor.getInt(idIndex),
                    cursor.getString(studentNameIndex),
                    cursor.getString(studentEmailIndex)
            );
        }
    }


    public boolean addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_COURSE_NAME, course.getCourseName());
        values.put(COL_STUDENT_ID, course.getStudentId());

        long num = db.insert(TABLE_NAME_COURSE, null, values);
        return num > 0;
    }


    public boolean upadateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_COURSE_NAME, course.getCourseName() + "@");
        values.put(COL_STUDENT_ID, course.getStudentId());

        String[] selectionArg = {String.valueOf(course.getId())};
        int num = db.update(TABLE_NAME_COURSE, values, "course_id=?", selectionArg);
        return num > 0;
    }

    public boolean deleteCourse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] selectionArg = {String.valueOf(id)};
        int num = db.delete(TABLE_NAME_COURSE, "course_id=?", selectionArg);
        return num > 0;
    }

    public Cursor getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_COURSE;
        return db.rawQuery(query, null);
    }

    public List<Course> getAllCourseAsList() {
        List<Course> courses = new ArrayList<>();

        try (Cursor cursor = this.getAllCourses()) {
            int idIndex = cursor.getColumnIndexOrThrow(COL_COURSE_ID);
            int courseNameIndex = cursor.getColumnIndexOrThrow(COL_COURSE_NAME);
            int studentIdIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_ID);

            while (cursor.moveToNext()) {
                courses.add(new Course(
                    cursor.getInt(idIndex),
                    cursor.getString(courseNameIndex),
                    cursor.getInt(studentIdIndex)
                ));
            }
        }

        return courses;
    }

    public Cursor getCourseEnrollmentData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT c." + COL_COURSE_ID + ", c." + COL_COURSE_NAME
                + ", s." + COL_STUDENT_ID + ", s." + COL_STUDENT_NAME
                + ", s." + COL_STUDENT_EMAIL
                + " FROM " + TABLE_NAME_COURSE + " c"
                + " INNER JOIN " + TABLE_NAME_STUDENT + " s"
                + " ON c." + COL_STUDENT_ID + " = s." + COL_STUDENT_ID
                + " ORDER BY c." + COL_COURSE_ID;
        return db.rawQuery(query, null);
    }

    public List<CourseEnrollmentData> getAllEnrollmentAsList() {
        List<CourseEnrollmentData> enrollments = new ArrayList<>();
        try (Cursor cursor = getCourseEnrollmentData()) {
            int courseIdIndex = cursor.getColumnIndexOrThrow(COL_COURSE_ID);
            int courseNameIndex = cursor.getColumnIndexOrThrow(COL_COURSE_NAME);
            int studentIdIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_ID);
            int studentNameIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_NAME);
            int studentEmailIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_EMAIL);

            while (cursor.moveToNext()) {
                enrollments.add(new CourseEnrollmentData(
                        cursor.getInt(courseIdIndex),
                        cursor.getString(courseNameIndex),
                        cursor.getInt(studentIdIndex),
                        cursor.getString(studentNameIndex),
                        cursor.getString(studentEmailIndex)
                ));
            }
        }
        return enrollments;
    }

    public Course getCourseById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_COURSE + " WHERE id=?";
        String[] selectionArg = {String.valueOf(id)};

        try (Cursor cursor = db.rawQuery(query, selectionArg)) {
            if (!cursor.moveToFirst()) {
                return null;
            }

            int idIndex = cursor.getColumnIndexOrThrow(COL_COURSE_ID);
            int courseNameIndex = cursor.getColumnIndexOrThrow(COL_COURSE_NAME);
            int studentIdIndex = cursor.getColumnIndexOrThrow(COL_STUDENT_ID);

            return new Course(
                        cursor.getInt(idIndex),
                        cursor.getString(courseNameIndex),
                        cursor.getInt(studentIdIndex)
            );
        }
    }
}
