package mx.tjappx.asistencia;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import mx.tjappx.asistencia.entities.ResultadoDispositivoLogin;
import mx.tjappx.asistencia.helpers.RetrofitHelper;
import mx.tjappx.asistencia.interfaces.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RetrofitInterface RetrofitInterface;
    private ProgressBar pbMainModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        getDeviceInfo();
        dispositivoLogin();
    }

    private void initUI() {
        pbMainModule = (ProgressBar) findViewById(R.id.pbMainModule);
        pbMainModule.setVisibility(ProgressBar.INVISIBLE);
    }

    private void getDeviceInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        GlobalVariables.setStrDeviceID(telephonyManager.getDeviceId());
        GlobalVariables.setStrOsVersion(Build.VERSION.RELEASE);
    }

    private void dispositivoLogin() {
        pbMainModule.setVisibility(ProgressBar.VISIBLE);

        JSONObject jsonObjectChild = new JSONObject();
        try {
            jsonObjectChild.put("opcion", "DispositivoLogin");
            jsonObjectChild.put("DispositivoSerial", GlobalVariables.getStrDeviceID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strParametros = jsonObjectChild.toString();

        RetrofitInterface = RetrofitHelper.getApiClient().create(RetrofitInterface.class);
        Call<ResultadoDispositivoLogin> call = RetrofitInterface.DispositivoLogin(strParametros);

        call.enqueue(new Callback<ResultadoDispositivoLogin>() {
            @Override
            public void onResponse(Call<ResultadoDispositivoLogin> call, Response<ResultadoDispositivoLogin> response) {
                ResultadoDispositivoLogin resultadoDispositivoLogin = response.body();

                if (resultadoDispositivoLogin.getValid() == 1) {
                    GlobalVariables.setStrUsuarioDispositivo(resultadoDispositivoLogin.getUsuarioDispositivo());
                    GlobalVariables.setIntUsuarioDispositivoID(resultadoDispositivoLogin.getUsuarioDispositivoID());
                    goToAsistencia();
                } else {
                    if (resultadoDispositivoLogin.getMensaje().equals("No registrado")) {
                        goToRequestDeviceApproval();
                    } else {
                        goToWaitForApproval();
                    }
                }
                pbMainModule.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResultadoDispositivoLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                pbMainModule.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    private void goToRequestDeviceApproval() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void goToWaitForApproval() {
        Intent intent = new Intent(this, WaitForApprovalActivity.class);
        startActivity(intent);
    }

    private void goToAsistencia() {
        Intent intent = new Intent(this, AsistenciaActivity.class);
        startActivity(intent);
    }
}