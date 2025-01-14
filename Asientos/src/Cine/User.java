package Cine;

public class User {
    private String nombre;
    private String apellido;
    private String cedula;

    public User(String apellido, String nombre, String cedula) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

}
