package com.bengkel;

// Inheritance dari class Person untuk karyawan mekanik bengkel.
public class Mekanik extends Person {
    // Properti eksklusif pekerja.
    private String idMekanik;
    private String spesialisasi;

    public Mekanik(String id, String nama, String noHP, String alamat, String idMekanik, String spesialisasi) {
        // Forwarding inisialisasi properti dasar ke super class.
        super(id, nama, noHP, alamat);
        setIdMekanik(idMekanik);
        setSpesialisasi(spesialisasi);
    }

    public Mekanik() {
        // Eksekusi flow input data pribadi (nama, alamat) via parent.
        super();
        // Lanjutkan dengan flow input data kepagawaian mekanik.
        setIdMekanik(Input.bacaString("ID Mekanik = "));
        setSpesialisasi(Input.bacaString("Spesialisasi = "));
    }

    public void setIdMekanik(String idMekanik) { this.idMekanik = idMekanik; }
    public void setSpesialisasi(String spesialisasi) { this.spesialisasi = spesialisasi; }
    public String getIdMekanik() { return idMekanik; }
    public String getSpesialisasi() { return spesialisasi; }

    // Overriding perannya agar sistem mengenali object ini sebagai pekerja, bukan klien.
    @Override
    public String getPeran() {
        return "Mekanik";
    }
}