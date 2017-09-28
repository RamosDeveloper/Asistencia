package mx.tjappx.asistencia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import mx.tjappx.asistencia.entities.Resultado;
import mx.tjappx.asistencia.helpers.RetrofitHelper;
import mx.tjappx.asistencia.interfaces.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private RetrofitInterface RetrofitInterface;
    private ProgressBar pbRegisterModule;
    private EditText txtRequestedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
    }

    private void initUI() {
        pbRegisterModule = (ProgressBar) findViewById(R.id.pbRegisterModule);
        txtRequestedBy = (EditText) findViewById(R.id.txtRequestedBy);
        pbRegisterModule.setVisibility(ProgressBar.INVISIBLE);
    }

    public void registrarDispositivo(View view) {
        String strRequestedBy = txtRequestedBy.getText().toString();

        pbRegisterModule.setVisibility(ProgressBar.VISIBLE);
        if (!strRequestedBy.isEmpty()) {
            JSONObject jsonObjectChild = new JSONObject();
            try {
                jsonObjectChild.put("opcion", "RegistrarDispositivo");
                jsonObjectChild.put("DispositivoSerial", GlobalVariables.getStrDeviceID());
                jsonObjectChild.put("OsVersion", GlobalVariables.getStrOsVersion());
                jsonObjectChild.put("NombreDelSolicitante", strRequestedBy);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String strParametros = jsonObjectChild.toString();

            RetrofitInterface = RetrofitHelper.getApiClient().create(RetrofitInterface.class);
            Call<Resultado> call = RetrofitInterface.RegistrarDispositivo(strParametros);

            call.enqueue(new Callback<Resultado>() {
                @Override
                public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                    Resultado resultado = response.body();

                    if (resultado.getValid() == 1) {
                        goToWaitForApproval();
                    } else {
                        Toast.makeText(getApplicationContext(), resultado.getMensaje(), Toast.LENGTH_LONG).show();
                    }
                    pbRegisterModule.setVisibility(ProgressBar.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Resultado> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    pbRegisterModule.setVisibility(ProgressBar.INVISIBLE);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Por favor teclee quien solicita la aprobacion,", Toast.LENGTH_LONG).show();
            pbRegisterModule.setVisibility(ProgressBar.INVISIBLE);
        }

    }

    private void goToWaitForApproval() {
        Intent intent = new Intent(this, WaitForApprovalActivity.class);
        startActivity(intent);
    }
}
