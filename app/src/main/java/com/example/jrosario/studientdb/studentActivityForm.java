package com.example.jrosario.studientdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jrosario.studientdb.databaseThings.Library;
import com.example.jrosario.studientdb.studentThings.Estudiante;

public class studentActivityForm extends AppCompatActivity {

    TextView txtNombre;
    TextView txtMateria;
    TextView txtCalificacion;
    Boolean toUpdate = false;
    Estudiante studentSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

          txtNombre        = findViewById(R.id.editNombre);
          txtMateria       = findViewById(R.id.editMateria);
          txtCalificacion  = findViewById(R.id.editCalificacion);
          studentSelected  = (Estudiante)getIntent().getSerializableExtra("toUpdate");

        if(getIntent().getSerializableExtra("toUpdate")!= null){

            //to update
            txtNombre.setText(studentSelected.getNombres());
            txtMateria.setText(studentSelected.getMateria());
            txtCalificacion.setText(""+studentSelected.getCalificacion());
            toUpdate = true;
        }

        Button btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = txtNombre.getText().toString();
                String materia = txtMateria.getText().toString();
                String calificacion = txtCalificacion.getText().toString();

                if(!nombre.equals("")
                        && !materia.equals("")
                        && !calificacion.equals("")){

                    Library dbDo = new Library(getApplicationContext());

                    if(toUpdate){
                        Estudiante eup
                                = new Estudiante(nombre,
                                "",
                                materia,
                                Integer.parseInt(calificacion));
                        eup.setId(studentSelected.getId());
                        dbDo.updateSelectedStudent(eup);
                    }else{

                        dbDo.insertStudentToDb(new Estudiante(nombre,"",materia,
                                Integer.parseInt(calificacion)
                        ));

                    }
                    startActivity(
                            new Intent(studentActivityForm.this,MainActivity.class));

                }else{
                    Toast.makeText(
                            studentActivityForm.this,
                            "Faltan campos por llenar!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
