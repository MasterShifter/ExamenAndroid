package com.example.csi2_23.examen2eva;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Localizacion> listaLocalizaciones = new ArrayList<>();

    private ListView lista;

    private AdaptadorLocalizacion adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i, 1);
            }
        });

        lista = (ListView)findViewById(R.id.lista);

        cargaLista();

        adaptador = new AdaptadorLocalizacion(this);
        lista.setAdapter(adaptador);
        registerForContextMenu(lista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case RESULT_OK:

                Localizacion loc = (Localizacion)data.getExtras().get("localizacion");
                listaLocalizaciones.add(loc);
                adaptador.notifyDataSetChanged();

                Toast.makeText(this, R.string.alertAddOk, Toast.LENGTH_SHORT).show();
                break;

            case RESULT_CANCELED:
                Toast.makeText(this, R.string.alertAddCanceled, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_borrar, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();

        //Dado que solo hay una opción no hay que comprobar cual se ha pulsado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.alertBorrar));

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listaLocalizaciones.remove(info.position);
                adaptador.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Nada que hacer
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        return super.onContextItemSelected(item);
    }

    private void cargaLista(){
        listaLocalizaciones.add(new Localizacion(1, "C:/Petunias", 0, "En esta calle murió un gato negro", new Date(), 6.0, 7.0));
        listaLocalizaciones.add(new Localizacion(2, "C:/Goonia", 3, "Museo de las camisetas Pitagoonia", new Date(), 14.0, 30.0));
        listaLocalizaciones.add(new Localizacion(3, "C:/Valverde", 1, "Parque de los acuiferos", new Date(), 18.0, 19.0));
        listaLocalizaciones.add(new Localizacion(4, "C:/Don Tenorio", 2, "En esta calle sirvieron el gato negro", new Date(), 6.0, 14.0));
        listaLocalizaciones.add(new Localizacion(5, "C:/Juan Penurias", 2, "Aquí la comida son penurias", new Date(), 6.0, 7.0));
        listaLocalizaciones.add(new Localizacion(6, "C:/Albacete", 0, "Calle commemorativa al entrecejo", new Date(), 10.0, 7.0));
        listaLocalizaciones.add(new Localizacion(7, "C:/Luan", 2, "Restaurante con una estrella Michelin", new Date(), 6.0, 7.0));
    }

    private String dateFormat(Date date){
        return String.format("%d/%d/%d", date.getDay(), date.getMonth(), date.getYear());
    }



    class AdaptadorLocalizacion extends ArrayAdapter {

        Activity context;

        public AdaptadorLocalizacion(Activity context) {
            super(context, R.layout.fila_lista, listaLocalizaciones);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            ViewHolder holder;

            if (item == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.fila_lista, null);
                holder = new ViewHolder();
                holder.txtIdentificador = (TextView) item.findViewById(R.id.txtIdentificador);
                holder.txtDireccion = (TextView) item.findViewById(R.id.txtDireccion);
                holder.txtFechaInsercion = (TextView) item.findViewById(R.id.txtFechaInsercion);
                holder.txtLatitude = (TextView) item.findViewById(R.id.txtLatitude);
                holder.txtLongitude = (TextView) item.findViewById(R.id.txtLongitude);
                holder.txtComentario = (TextView) item.findViewById(R.id.txtComentario);
                holder.imgTipo = (ImageView) item.findViewById(R.id.imgTipo);
                item.setTag(holder);
            } else {
                holder = (ViewHolder) item.getTag();
            }

            holder.txtIdentificador.setText(String.format("#%d", listaLocalizaciones.get(position).getIdentificador()));
            holder.txtDireccion.setText(listaLocalizaciones.get(position).getDireccion().toString());
            holder.txtFechaInsercion.setText(dateFormat(listaLocalizaciones.get(position).getFechaInsercion()));
            holder.txtLongitude.setText(String.format("Lo:%f", listaLocalizaciones.get(position).getLongitude()));
            holder.txtLatitude.setText(String.format("La:%f", listaLocalizaciones.get(position).getLatitude()));
            holder.txtComentario.setText(listaLocalizaciones.get(position).getComentario().toString());
            switch (listaLocalizaciones.get(position).getTipoLocalizacion()) {
                case 0:
                    holder.imgTipo.setBackgroundResource(R.mipmap.street);
                    break;
                case 1:
                    holder.imgTipo.setBackgroundResource(R.mipmap.park);
                    break;
                case 2:
                    holder.imgTipo.setBackgroundResource(R.mipmap.restaurant);
                    break;
                case 3:
                    holder.imgTipo.setBackgroundResource(R.mipmap.museum);
                    break;
            }
            return item;
        }
    }

    static class ViewHolder {
        TextView txtIdentificador;
        TextView txtDireccion;
        TextView txtFechaInsercion;
        TextView txtLatitude;
        TextView txtLongitude;
        TextView txtComentario;
        ImageView imgTipo;
    }






}


/*



public class ListActivity extends AppCompatActivity {

    private ListView lista;

    private List<Alumno> listaAlumnos = new ArrayList<>();
    private List<String> subjetcNames = new ArrayList<>();

    private AdaptadorAlumno adaptador;

    private Alumno alumno = new Alumno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListActivity.this, EditActivity.class);
                alumno = new Alumno();
                i.putExtra("alumno", alumno);
                startActivityForResult(i, 1);
            }
        });


        subjetcNames.add(getString(R.string.subject0));
        subjetcNames.add(getString(R.string.subject1));
        subjetcNames.add(getString(R.string.subject2));
        subjetcNames.add(getString(R.string.subject3));



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de almunos");
        setSupportActionBar(toolbar);


        lista = (ListView)findViewById(R.id.lista);

        cargarDatos();

        adaptador = new AdaptadorAlumno(this);
        lista.setAdapter(adaptador);

        registerForContextMenu(lista);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.btnDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.alertDeleteAlum));

                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listaAlumnos.remove(info.position);
                        adaptador.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.btnEdit:

                Intent i = new Intent(ListActivity.this, EditActivity.class);
                i.putExtra("alumno", listaAlumnos.get(info.position));
                startActivityForResult(i, 2);

                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            alumno = (Alumno) data.getExtras().get("alumno");
            //Añadir si es nuevo, cambiar si es existente (se sabe por el ID)

            boolean esNuevo = true;

            for (int i = 0; i < listaAlumnos.size(); i++) {
                if (listaAlumnos.get(i).getIdAlumn().equals(alumno.getIdAlumn())) {
                    Toast.makeText(this, "EXISTENTE", Toast.LENGTH_SHORT).show();
                    listaAlumnos.remove(i);
                    listaAlumnos.add(i, alumno);
                    esNuevo = false;
                    break;
                }

            }

            if (esNuevo) {
                listaAlumnos.add(alumno);
            }


            adaptador.notifyDataSetChanged();
        }

    }

    private void cargarDatos() {
        listaAlumnos.add(new Alumno(1, "Alfonso", "Gutierrez Alfonsino", 954998801, "alfonso@gmail.com", 0, 10.0f));
        listaAlumnos.add(new Alumno(2, "David", "Fuentes Romero", 954998802, "david@gmail.com", 0, 9.0f));
        listaAlumnos.add(new Alumno(3, "Nuria", "López Otero", 954998803, "nuria@gmail.com", 1, 5.5f));
        listaAlumnos.add(new Alumno(4, "Nerea", "García López", 954998804, "nerea@gmail.com", 1, 4.0f));
        listaAlumnos.add(new Alumno(5, "Cristian", "Neruda García", 954998805, "cristian@gmail.com", 2, 2.0f));
        listaAlumnos.add(new Alumno(6, "Araceli", "Diaz Diaz", 954998806, "araceli@gmail.com", 0, 7.5f));
        listaAlumnos.add(new Alumno(7, "Diana", "Campofrio Elegante", 954998807, "diana@gmail.com", 0, 8.5f));
        listaAlumnos.add(new Alumno(8, "Aida", "Ruiz Fuentes", 954998808, "aida@gmail.com", 2, 5.0f));
        listaAlumnos.add(new Alumno(9, "Alejandro", "Navarro Pérez", 954998809, "alejandro@gmail.com", 0, 3.5f));
        listaAlumnos.add(new Alumno(10, "Pablo", "Caballero Lozano", 954998810, "pablo@gmail.com", 0, 6.0f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btnDeleteAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.alertDeleteAll));

            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listaAlumnos.clear();
                    adaptador.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        if (id == R.id.btnCloseSession){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.alertlogout);

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);

                    SharedPreferences.Editor speditor = sp.edit();
                    speditor.remove("user");
                    speditor.remove("password");
                    speditor.commit();

                    Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }









    class AdaptadorAlumno extends ArrayAdapter {

        Activity context;

        public AdaptadorAlumno(Activity context) {
            super(context, R.layout.fila_alumno, listaAlumnos);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;

           ViewHolder holder;

            if (item == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.fila_alumno, null);

                holder = new ViewHolder();
                holder.txtFullName = (TextView) item.findViewById(R.id.txtFullName);
                holder.txtEmail = (TextView) item.findViewById(R.id.txtEmail);
                holder.txtPhone = (TextView) item.findViewById(R.id.txtPhone);
                holder.txtMark = (TextView) item.findViewById(R.id.txtMark);
                holder.txtSubject = (TextView) item.findViewById(R.id.txtSubject);
                holder.imgSubject = (ImageView) item.findViewById(R.id.imgSubject);

                item.setTag(holder);
            } else {
                holder = (ViewHolder) item.getTag();
            }

            holder.txtFullName.setText(listaAlumnos.get(position).getSurname()+", "+listaAlumnos.get(position).getName());
            holder.txtEmail.setText(String.valueOf(listaAlumnos.get(position).getEmail()));
            holder.txtPhone.setText(String.valueOf(listaAlumnos.get(position).getPhonenumber()));
            holder.txtSubject.setText(String.valueOf(subjetcNames.get(listaAlumnos.get(position).getSubject())));
            holder.txtMark.setText(listaAlumnos.get(position).getMark().toString());
            switch (listaAlumnos.get(position).getSubject()) {
                case 0:
                    holder.imgSubject.setBackgroundResource(R.mipmap.generic);
                    break;
                case 1:
                    holder.imgSubject.setBackgroundResource(R.mipmap.programer);
                    break;
                case 2:
                    holder.imgSubject.setBackgroundResource(R.mipmap.administrator);
                    break;
                case 3:
                    holder.imgSubject.setBackgroundResource(R.mipmap.mecanic);
                    break;
            }

            return item;
        }
    }

    static class ViewHolder {
        TextView txtFullName;
        TextView txtEmail;
        TextView txtSubject;
        TextView txtPhone;
        TextView txtMark;
        ImageView imgSubject;
    }

}
 */