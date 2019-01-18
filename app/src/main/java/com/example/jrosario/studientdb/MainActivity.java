package com.example.jrosario.studientdb;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jrosario.studientdb.databaseThings.Library;
import com.example.jrosario.studientdb.studentThings.Estudiante;
import com.example.jrosario.studientdb.studentThings.listAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    listAdapter adapterStudent;
    private ActionMode mActionMode;
    private ArrayList<Estudiante> listaEstudiantes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_student);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final ListView listViewStudent = findViewById(R.id.list);

        ArrayList<Estudiante> listEstudiantesDefault = new ArrayList<>();
        listEstudiantesDefault.add(new Estudiante(
                "",
                "",
                "No user availables",
                0));

        //listFromDb

            ArrayList<Estudiante> listFromDb = new ArrayList<>();
            Library l = new Library(this);
            listFromDb = l.getAllStudentsFromDb();


        listaEstudiantes = (listFromDb.size()==0)?listEstudiantesDefault:listFromDb;
             adapterStudent = new listAdapter(
                    this,
                    R.layout.listview_row,
                     listaEstudiantes
            );


        //using asyntasks
        new  AsyncTask<Void, Integer, String>() {

            @Override
            protected void onPreExecute() {
                listViewStudent.setAdapter(null);
            }

            @Override
            protected String doInBackground(Void... params) {
                int i;
                int numbers =3;
                for(i=0;i<numbers;i++){
                   publishProgress((i+1)*100/numbers);
                }
                return "";
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                Toast.makeText(MainActivity.this, "Cargado estudiantes con Asyntask... "+values[0]+"%",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String Long) {}
        }.execute(null,null,null);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listViewStudent.setAdapter(adapterStudent);
            };
        },7550);

        //changing font

        FloatingActionButton changeFont = findViewById(R.id.changeFont);
        changeFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Digite el tamaño");
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                alertDialog.setView(input);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String textSize = input.getText().toString();
                                if(!textSize.equals(""))adapterStudent.setTextSize(textSize);
                                dialog.dismiss();

                            }
                        });
                alertDialog.show();
            }
        });

        //new btn

            FloatingActionButton addStudentBtn = findViewById(R.id.fab);
            addStudentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  startActivity(
                          new Intent(MainActivity.this,studentActivityForm.class));
                }
            });

        //show menu on long press



        listViewStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,
                                           View view, int position, long l) {


                if(mActionMode == null) {
                    mActionMode = startActionMode(new ActionModeCallback());
                    adapterStudent.RowSelecionado(position);

                    view.setBackgroundColor(0x00000000);
                    if(adapterStudent.getSelectedIds().get(position)){
                        view.setBackgroundResource(R.color.shadowList);
                    }
                    mActionMode.setTitle(
                            String.valueOf(adapterStudent.getSelectedCount()) + " selected");
                 }
                 return true;
            }
        });




        //selectin items when clicked

        listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long l) {

                if (mActionMode != null){
                    adapterStudent.RowSelecionado(position);
                    if(adapterStudent.getSelectedCount() == 0){
                        mActionMode.finish();
                    }else{
                        if(adapterStudent.getSelectedCount()>1){

                            mActionMode.getMenu().getItem(0).setVisible(false);
                        }else{
                            mActionMode.getMenu().getItem(0).setVisible(true);
                        }
                        mActionMode.setTitle(
                                String.valueOf(
                                        adapterStudent.getSelectedCount()) + " selected"
                        );
                    }

                    view.setBackgroundColor(0x00000000);
                    if(adapterStudent.getSelectedIds().get(position)){
                        view.setBackgroundResource(R.color.shadowList);
                    }
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        //adding search to menu

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterStudent.filter(s);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        System.out.println("clicked");
        return super.onOptionsItemSelected(item);


    }

    public void onCreateContextMenu(final ContextMenu menu,
                                    final View v,
                                    final ContextMenu.ContextMenuInfo menuInfo) {

    }

    private class ActionModeCallback  implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // inflate contextual menu
            mode.getMenuInflater().inflate(R.menu.update_delete, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            SparseBooleanArray selected = adapterStudent.getSelectedIds();
            Library actionToDb = new Library(MainActivity.this);

            //deleting element
            switch (item.getItemId()){

                case R.id.delete:
                    for (int i = (selected.size() - 1); i >= 0; i--) {
                        if (selected.valueAt(i)) {
                            Estudiante studentSelected = adapterStudent.getItem(selected.keyAt(i));
                            adapterStudent.remove(studentSelected);
                            actionToDb.deleteSelectedStudents(studentSelected);
                        }
                    }

                 break;

                case R.id.update:
                    System.out.println("printing");
                    Estudiante studentSelected = adapterStudent.getItem(selected.keyAt(0));
                    startActivity(
                            new Intent(MainActivity.this,studentActivityForm.class)
                                    .putExtra("toUpdate",studentSelected));

                 break;

                default:

                    return false;
            }


            mode.finish();

            return true;

         }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // remove selection
            adapterStudent.removeSelection();
            mActionMode = null;
        }



    }
}


