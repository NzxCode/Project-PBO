package com.bengkel;

/*
 * Child Class: Mekanik
 */
public class Mekanik extends Person {
    private String idMekanik;
    private String spesialisasi;

    //Constructor #1
    public Mekanik(String id, String nama, String noHP, String alamat,
                   String idMekanik, String spesialisasi) {
        super(id, nama, noHP, alamat);
        setIdMekanik(idMekanik);
        setSpesialisasi(spesialisasi);
    }

    //Constructor #2: ask user to input each attribute value
    public Mekanik() {
        super(); //input atribut milik Person
        setIdMekanik(Input.bacaString("ID Mekanik = "));
        setSpesialisasi(Input.bacaString("Spesialisasi = "));
    }

    //setters and getters
    public void setIdMekanik(String idMekanik) {
        this.idMekanik = idMekanik;
    }
    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }
    public String getIdMekanik() {
        return idMekanik;
    }
    public String getSpesialisasi() {
        return spesialisasi;
    }

    /*
     * method overriding
     * @overriding getPeran dari parent class "Person"
     */
    @Override
    public String getPeran() {
        return "Mekanik";
    }
}
