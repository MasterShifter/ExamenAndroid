package com.example.csi2_23.examen2eva;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtAddIdentificador, edtAddDireccion, edtAddLatitude, edtAddLongitude, edtAddComentario;
    private Spinner spinAddTipo;
    private Button btnAddOk, btnAddCancel, btnAutoRellenar;

    List<String> listaTipos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtAddIdentificador = (EditText) findViewById(R.id.edtAddIdentificador);
        edtAddDireccion = (EditText) findViewById(R.id.edtAddDireccion);
        edtAddLatitude = (EditText) findViewById(R.id.edtAddLatitude);
        edtAddLongitude = (EditText) findViewById(R.id.edtAddLongitude);
        edtAddComentario = (EditText) findViewById(R.id.edtAddComentario);

        spinAddTipo = (Spinner) findViewById(R.id.spinAddTipo);

        btnAddOk = (Button) findViewById(R.id.btnAddOk);
        btnAddCancel = (Button) findViewById(R.id.btnAddCancel);
        btnAddOk.setOnClickListener(this);
        btnAddCancel.setOnClickListener(this);

        btnAutoRellenar = (Button) findViewById(R.id.btnAutoRellenar);
        btnAutoRellenar.setOnClickListener(this);

        cargaLista();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTipos);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAddTipo.setAdapter(spinnerArrayAdapter);
    }

    private void cargaLista() {
        listaTipos.add(getString(R.string.tipoCalle));
        listaTipos.add(getString(R.string.tipoParque));
        listaTipos.add(getString(R.string.tipoRestaurante));
        listaTipos.add(getString(R.string.tipoMuseo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddOk:
                if (edtAddIdentificador.getText().length() != 0 && edtAddDireccion.getText().length() != 0
                        && edtAddLatitude.getText().length() != 0 && edtAddLongitude.getText().length() != 0
                        && edtAddComentario.getText().length() != 0) {

                    Intent i = new Intent(AddActivity.this, MainActivity.class);

                    Integer id = Integer.parseInt(edtAddIdentificador.getText().toString());
                    Integer tipo = spinAddTipo.getSelectedItemPosition();
                    String dir = edtAddDireccion.getText().toString();
                    String com = edtAddComentario.getText().toString();
                    Double latitude = Double.parseDouble(edtAddLatitude.getText().toString());
                    Double longitude = Double.parseDouble(edtAddLongitude.getText().toString());

                    Localizacion l = new Localizacion(id, dir, tipo, com, new Date(), latitude, longitude);
                    i.putExtra("localizacion", l);

                    setResult(RESULT_OK, i);
                    finish();
                } else {
                    Toast.makeText(this, R.string.alertAddCampos, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnAddCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;

            case R.id.btnAutoRellenar:

                GPSTracker gps = new GPSTracker(AddActivity.this);

                // check if GPS enabled
                if (gps.canGetLocation()) {
                    edtAddLatitude.setText(String.format("%f", gps.getLatitude()));
                    edtAddLongitude.setText(String.format("%f", gps.getLongitude()));
                } else {
                    gps.showSettingsAlert();
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
