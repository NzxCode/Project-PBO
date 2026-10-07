package com.bengkel;

public class Mekanik extends Person {
    private String idMekanik;
    private String spesialisasi;

    public Mekanik(String id, String nama, String noHP, String alamat, String idMekanik, String spesialisasi) {
        super(id, nama, noHP, alamat);
        setIdMekanik(idMekanik);
        setSpesialisasi(spesialisasi);
    }

    public Mekanik() { super(); }

    public String getIdMekanik() { return idMekanik; }
    public void setIdMekanik(String idMekanik) { this.idMekanik = Validasi.wajibIsi(idMekanik, "ID Mekanik"); }
    public String getSpesialisasi() { return spesialisasi; }
    public void setSpesialisasi(String spesialisasi) { this.spesialisasi = Validasi.wajibIsi(spesialisasi, "Spesialisasi"); }

    @Override
    public String getPeran() { return "Mekanik"; }
}
