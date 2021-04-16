package com.example.timebank;

public class User {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String localidad;
    private String profesion;
    private String[] messages;
    private String tlf_fijo;
    private String tlf_movil;
    private String serviciosOfr;
    private int horas = 0;
    private int totalHoras = 0;
    private int timesHelped = 0;
    private int timesHelpedBy = 0;

    public User(String nombre, String apellido1) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
    }

    public User(String dni, String nombre, String apellido1, String apellido2, String localidad,
                String serviciosOfr, String tlf_fijo, String tlf_movil) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.localidad = localidad;
        this.serviciosOfr = serviciosOfr;
    }

    public User() {
    }
}
