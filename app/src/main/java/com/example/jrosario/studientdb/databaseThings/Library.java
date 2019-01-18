package com.example.jrosario.studientdb.databaseThings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jrosario.studientdb.studentThings.Estudiante;

import java.util.ArrayList;

public class Library {

    DBHelper dbHelper;

    public Library(Context c){
        dbHelper = new DBHelper(c);
    }

    public void insertStudentToDb(Estudiante data) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db != null) {

            ContentValues c = new ContentValues();
            c.put(DBHelper.FeedEntry.COLUM_TWO_NAME_TITLE, data.getNombres());
            c.put(DBHelper.FeedEntry.COLUM_THREE_NAME_TITLE, data.getMateria());
            c.put(DBHelper.FeedEntry.COLUM_FOUR_NAME_TITLE, data.getCalificacion());
            db.insert(DBHelper.FeedEntry.TABLE_NAME, null, c);

        }
    }

    public ArrayList<Estudiante> getAllStudentsFromDb(){

        ArrayList<Estudiante> allStudents =  new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
       // db.delete(DBHelper.FeedEntry.TABLE_NAME,null,null);

        Cursor cursor = db.rawQuery(
                "SELECT * FROM "+
                        DBHelper.FeedEntry.TABLE_NAME, null);


        if(cursor != null) {
            while (cursor.moveToNext()) {

                Estudiante d = new Estudiante(
                        cursor.getString(cursor.getColumnIndex(DBHelper.FeedEntry.COLUM_TWO_NAME_TITLE)),
                        "",
                        cursor.getString(cursor.getColumnIndex(DBHelper.FeedEntry.COLUM_THREE_NAME_TITLE)),
                        cursor.getInt(cursor.getColumnIndex("calificacion"))
                        );

                d.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex("_id"))));
                boolean favorite = cursor.getInt(cursor.getColumnIndex(DBHelper.FeedEntry.COLUM_FIVE_NAME_TITLE)) > 0;
                d.setFavorite( favorite);
                allStudents.add(d);
            }
        }
        cursor.close();

        return allStudents;
    }

    public void deleteSelectedStudents(Estudiante selected){

       SQLiteDatabase db = dbHelper.getWritableDatabase();
       db.delete(DBHelper.FeedEntry.TABLE_NAME,"_id=?",new String[]{""+selected.getId()});
    }


    public void updateSelectedStudent(Estudiante selected){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FeedEntry.COLUM_TWO_NAME_TITLE,selected.getNombres());
        cv.put(DBHelper.FeedEntry.COLUM_THREE_NAME_TITLE,selected.getMateria());
        cv.put(DBHelper.FeedEntry.COLUM_FOUR_NAME_TITLE,selected.getCalificacion()+"");
        cv.put(DBHelper.FeedEntry.COLUM_FIVE_NAME_TITLE,selected.getFavorite());

        db.update(DBHelper.FeedEntry.TABLE_NAME,cv,"_id=?",new String[]{""+selected.getId()});
    }
}
