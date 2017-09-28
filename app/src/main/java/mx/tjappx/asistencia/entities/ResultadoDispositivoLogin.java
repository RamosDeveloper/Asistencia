package mx.tjappx.asistencia.entities;

public class ResultadoDispositivoLogin {
    private int Valid;
    private String Mensaje;
    private int UsuarioDispositivoID;
    private String UsuarioDispositivo;

    public int getValid() {
        return Valid;
    }

    public void setValid(int valid) {
        Valid = valid;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public int getUsuarioDispositivoID() {
        return UsuarioDispositivoID;
    }

    public void setUsuarioDispositivoID(int usuarioDispositivoID) {
        UsuarioDispositivoID = usuarioDispositivoID;
    }

    public String getUsuarioDispositivo() {
        return UsuarioDispositivo;
    }

    public void setUsuarioDispositivo(String usuarioDispositivo) {
        UsuarioDispositivo = usuarioDispositivo;
    }
}