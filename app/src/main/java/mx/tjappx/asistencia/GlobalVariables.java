package mx.tjappx.asistencia;

public class GlobalVariables {
    private static String strDeviceID;
    private static String strOsVersion;
    private static String strUsuarioDispositivo;
    private static int intUsuarioDispositivoID;

    public static String getStrDeviceID() {
        return strDeviceID;
    }

    public static void setStrDeviceID(String strDeviceID) {
        GlobalVariables.strDeviceID = strDeviceID;
    }

    public static String getStrOsVersion() {
        return strOsVersion;
    }

    public static void setStrOsVersion(String strOsVersion) {
        GlobalVariables.strOsVersion = strOsVersion;
    }

    public static String getStrUsuarioDispositivo() {
        return strUsuarioDispositivo;
    }

    public static void setStrUsuarioDispositivo(String strUsuarioDispositivo) {
        GlobalVariables.strUsuarioDispositivo = strUsuarioDispositivo;
    }

    public static int getIntUsuarioDispositivoID() {
        return intUsuarioDispositivoID;
    }

    public static void setIntUsuarioDispositivoID(int intUsuarioDispositivoID) {
        GlobalVariables.intUsuarioDispositivoID = intUsuarioDispositivoID;
    }
}