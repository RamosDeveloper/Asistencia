package mx.tjappx.asistencia.interfaces;

import java.util.List;

import mx.tjappx.asistencia.entities.Congregacion;
import mx.tjappx.asistencia.entities.Resultado;
import mx.tjappx.asistencia.entities.ResultadoDispositivoLogin;
import mx.tjappx.asistencia.entities.Reunion;
import mx.tjappx.asistencia.entities.Seccion;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("CerberusController.php")
    Call<ResultadoDispositivoLogin> DispositivoLogin(@Field("Parametros") String Parametros);

    @FormUrlEncoded
    @POST("DispositivosPermitidosController.php")
    Call<Resultado> RegistrarDispositivo(@Field("Parametros") String Parametros);

    @GET("AsociacionReunionesController.php")
    Call<List<Reunion>> ObtenerInformacionReuniones(@Query("Parametros") String Parametros);

    @GET("CongregacionesController.php")
    Call<List<Congregacion>> ObtenerCongregacionesActivas(@Query("Parametros") String Parametros);

    @GET("SeccionesController.php")
    Call<List<Seccion>> ObtenerSeccionesActivas(@Query("Parametros") String Parametros);

    @FormUrlEncoded
    @POST("AsistenciaController.php")
    Call<Resultado> CapturarAsistencia(@Field("Parametros") String Parametros);
}