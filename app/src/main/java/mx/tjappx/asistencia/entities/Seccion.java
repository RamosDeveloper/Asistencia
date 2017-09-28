package mx.tjappx.asistencia.entities;

public class Seccion {
    private int Id;
    private String Nombre;
    private int CongregacionId;
    private int UsuarioId;
    private String FechaCreacion;
    private String Estatus;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getCongregacionId() {
        return CongregacionId;
    }

    public void setCongregacionId(int congregacionId) {
        CongregacionId = congregacionId;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}