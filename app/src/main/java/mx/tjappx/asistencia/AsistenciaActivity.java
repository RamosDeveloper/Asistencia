package mx.tjappx.asistencia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.tjappx.asistencia.entities.Congregacion;
import mx.tjappx.asistencia.entities.Resultado;
import mx.tjappx.asistencia.entities.Reunion;
import mx.tjappx.asistencia.entities.Seccion;
import mx.tjappx.asistencia.helpers.RetrofitHelper;
import mx.tjappx.asistencia.interfaces.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsistenciaActivity extends AppCompatActivity {
    private RetrofitInterface RetrofitInterface;
    private ProgressBar pbAsistenciaModule;
    private Spinner cboReuniones;
    private Spinner cboCongregaciones;
    private Spinner cboSecciones;
    private EditText txtNumeroAsistentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        initUI();
        obtenerInformacionReuniones();
    }

    private void initUI() {
        pbAsistenciaModule = (ProgressBar) findViewById(R.id.pbAsistenciaModule);
        cboReuniones = (Spinner) findViewById(R.id.cboReuniones);
        cboCongregaciones = (Spinner) findViewById(R.id.cboCongregaciones);
        cboSecciones = (Spinner) findViewById(R.id.cboSecciones);
        txtNumeroAsistentes = (EditText) findViewById(R.id.txtNumeroAsistentes);

        pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);

        cboCongregaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Congregacion congregacionSeleccionada = (Congregacion) parent.getItemAtPosition(position);
                obtenerSeccionesActivas(congregacionSeleccionada.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void obtenerInformacionReuniones() {
        pbAsistenciaModule.setVisibility(ProgressBar.VISIBLE);

        JSONObject jsonObjectChild = new JSONObject();
        try {
            jsonObjectChild.put("opcion", "ObtenerInformacionReuniones");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strParametros = jsonObjectChild.toString();

        RetrofitInterface = RetrofitHelper.getApiClient().create(RetrofitInterface.class);
        Call<List<Reunion>> call = RetrofitInterface.ObtenerInformacionReuniones(strParametros);

        call.enqueue(new Callback<List<Reunion>>() {
            @Override
            public void onResponse(Call<List<Reunion>> call, Response<List<Reunion>> response) {
                List<Reunion> ListadoReuniones = new ArrayList<Reunion>();
                ListadoReuniones = response.body();

                if (ListadoReuniones.size() > 0) {
                    if (ListadoReuniones.get(0).getId() == 0) {
                        ListadoReuniones.clear();
                    }
                }

                Reunion reunionDefault = new Reunion();
                reunionDefault.setId(0);
                reunionDefault.setNombre("Selecciona una reunion:");
                reunionDefault.setEstatus("1");
                reunionDefault.setUsuarioId(1);

                ListadoReuniones.add(0, reunionDefault);
                ArrayAdapter<Reunion> adapter = new ArrayAdapter<Reunion>(getApplicationContext(), android.R.layout.simple_spinner_item, ListadoReuniones);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cboReuniones.setAdapter(adapter);

                pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);

                obtenerCongregacionesActivas();
            }

            @Override
            public void onFailure(Call<List<Reunion>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    private void obtenerCongregacionesActivas() {
        pbAsistenciaModule.setVisibility(ProgressBar.VISIBLE);

        JSONObject jsonObjectChild = new JSONObject();
        try {
            jsonObjectChild.put("opcion", "ObtenerCongregacionesActivas");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strParametros = jsonObjectChild.toString();

        RetrofitInterface = RetrofitHelper.getApiClient().create(RetrofitInterface.class);
        Call<List<Congregacion>> call = RetrofitInterface.ObtenerCongregacionesActivas(strParametros);

        call.enqueue(new Callback<List<Congregacion>>() {
            @Override
            public void onResponse(Call<List<Congregacion>> call, Response<List<Congregacion>> response) {
                List<Congregacion> ListadoCongregaciones = new ArrayList<Congregacion>();
                ListadoCongregaciones = response.body();

                if (ListadoCongregaciones.size() > 0) {
                    if (ListadoCongregaciones.get(0).getId() == 0) {
                        ListadoCongregaciones.clear();
                    }
                }

                Congregacion congregacionDefault = new Congregacion();
                congregacionDefault.setId(0);
                congregacionDefault.setNombre("Selecciona una congregacion:");
                congregacionDefault.setEstatus("1");
                congregacionDefault.setUsuarioId(1);

                ListadoCongregaciones.add(0, congregacionDefault);

                ArrayAdapter<Congregacion> adapter = new ArrayAdapter<Congregacion>(getApplicationContext(), android.R.layout.simple_spinner_item, ListadoCongregaciones);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cboCongregaciones.setAdapter(adapter);

                pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Congregacion>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    private void obtenerSeccionesActivas(final int CongregacionID) {
        pbAsistenciaModule.setVisibility(ProgressBar.VISIBLE);

        JSONObject jsonObjectChild = new JSONObject();
        try {
            jsonObjectChild.put("opcion", "ObtenerSeccionesActivas");
            jsonObjectChild.put("CongregacionID", CongregacionID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strParametros = jsonObjectChild.toString();

        RetrofitInterface = RetrofitHelper.getApiClient().create(RetrofitInterface.class);
        Call<List<Seccion>> call = RetrofitInterface.ObtenerSeccionesActivas(strParametros);

        call.enqueue(new Callback<List<Seccion>>() {
            @Override
            public void onResponse(Call<List<Seccion>> call, Response<List<Seccion>> response) {
                List<Seccion> ListadoSecciones = new ArrayList<Seccion>();
                ListadoSecciones = response.body();

                if (ListadoSecciones.size() > 0) {
                    if (ListadoSecciones.get(0).getId() == 0) {
                        ListadoSecciones.clear();
                    }
                }

                Seccion seccionDefault = new Seccion();
                seccionDefault.setId(0);
                seccionDefault.setNombre("TODAS");
                seccionDefault.setCongregacionId(CongregacionID);
                seccionDefault.setEstatus("1");
                seccionDefault.setUsuarioId(1);

                ListadoSecciones.add(0, seccionDefault);

                ArrayAdapter<Seccion> adapter = new ArrayAdapter<Seccion>(getApplicationContext(), android.R.layout.simple_spinner_item, ListadoSecciones);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cboSecciones.setAdapter(adapter);

                pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Seccion>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    public void capturarAsistencia(View view) {
        pbAsistenciaModule.setVisibility(ProgressBar.VISIBLE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaCaptura = sdf.format(new Date());

        Reunion reunionSeleccionada = (Reunion) cboReuniones.getSelectedItem();
        Congregacion congregacionSeleccionada = (Congregacion) cboCongregaciones.getSelectedItem();
        Seccion seccionSeleccionada = (Seccion) cboSecciones.getSelectedItem();

        if (reunionSeleccionada.getId() != 0) {
            if (congregacionSeleccionada.getId() != 0) {
                if (txtNumeroAsistentes.getText().length() > 0) {
                    int numeroAsistentes = Integer.parseInt(txtNumeroAsistentes.getText().toString());

                    JSONObject jsonObjectChild = new JSONObject();
                    try {
                        jsonObjectChild.put("opcion", "CapturarAsistencia");
                        jsonObjectChild.put("ReunionID", reunionSeleccionada.getId());
                        jsonObjectChild.put("CongregacionID", congregacionSeleccionada.getId());
                        jsonObjectChild.put("SeccionID", seccionSeleccionada.getId());
                        jsonObjectChild.put("NumeroAsistentes", numeroAsistentes);
                        jsonObjectChild.put("UsuarioID", GlobalVariables.getIntUsuarioDispositivoID());
                        jsonObjectChild.put("FechaCaptura", fechaCaptura);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String strParametros = jsonObjectChild.toString();

                    RetrofitInterface = RetrofitHelper.getApiClient().create(RetrofitInterface.class);
                    Call<Resultado> call = RetrofitInterface.CapturarAsistencia(strParametros);

                    call.enqueue(new Callback<Resultado>() {
                        @Override
                        public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                            Resultado resultadoCapturaAsistencia = new Resultado();
                            resultadoCapturaAsistencia = response.body();

                            Toast.makeText(AsistenciaActivity.this, resultadoCapturaAsistencia.getMensaje(), Toast.LENGTH_SHORT).show();
                            if (resultadoCapturaAsistencia.getValid() == 1) {
                                cboReuniones.setSelection(0);
                                cboCongregaciones.setSelection(0);
                                txtNumeroAsistentes.setText("");
                            }
                            pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<Resultado> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
                        }
                    });
                } else {
                    Toast.makeText(this, "Falta especificar el numero de asistentes", Toast.LENGTH_SHORT).show();
                    pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
                    txtNumeroAsistentes.requestFocus();
                }
            } else {
                Toast.makeText(this, "Falta especificar la congregacion", Toast.LENGTH_SHORT).show();
                pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
                cboCongregaciones.requestFocus();
            }
        } else {
            Toast.makeText(this, "Falta especificar la reunion", Toast.LENGTH_SHORT).show();
            pbAsistenciaModule.setVisibility(ProgressBar.INVISIBLE);
            cboReuniones.requestFocus();
        }
    }
}