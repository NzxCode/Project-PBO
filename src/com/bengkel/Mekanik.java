package com.bengkel;

public class Mekanik extends Person {
    private String idMekanik;
    private String spesialisasi;

    public Mekanik(String id, String nama, String noHP, String alamat, String idMekanik, String spesialisasi) {
        super(id, nama, noHP, alamat);
        this.idMekanik = idMekanik;
        this.spesialisasi = spesialisasi;
    }

    public Mekanik() { super(); }

    public String getIdMekanik() { return idMekanik; }
    public void setIdMekanik(String idMekanik) { this.idMekanik = idMekanik; }
    public String getSpesialisasi() { return spesialisasi; }
    public void setSpesialisasi(String spesialisasi) { this.spesialisasi = spesialisasi; }

    @Override
    public String getPeran() { return "Mekanik"; }
}