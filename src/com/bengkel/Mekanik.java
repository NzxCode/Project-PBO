// File: Mekanik.java
package com.bengkel;

// Class Mekanik adalah turunan langsung (child class) dari class Person
public class Mekanik extends Person {
    // Variabel eksklusif untuk mendeskripsikan mekanik
    private String idMekanik;
    private String spesialisasi;

    // Constructor utama dengan semua variabel parent dan child terdefinisi
    public Mekanik(String id, String nama, String noHP, String alamat,
                   String idMekanik, String spesialisasi) {
        // Melempar variabel dasar ke constructor milik parent
        super(id, nama, noHP, alamat);
        // Menetapkan sisa properti lokal melalui setter
        setIdMekanik(idMekanik);
        setSpesialisasi(spesialisasi);
    }

    // Constructor interaktif tanpa passing parameter
    public Mekanik() {
        // Melaksanakan prosedur tanya jawab dari parent class
        super(); 
        // Melanjutkan pertanyaan atribut yang spesifik mekanik
        setIdMekanik(Input.bacaString("ID Mekanik = "));
        setSpesialisasi(Input.bacaString("Spesialisasi = "));
    }

    // Kumpulan setter mutator untuk properti milik class ini
    public void setIdMekanik(String idMekanik) {
        this.idMekanik = idMekanik;
    }
    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }
    // Kumpulan getter accessor
    public String getIdMekanik() {
        return idMekanik;
    }
    public String getSpesialisasi() {
        return spesialisasi;
    }

    // Overriding method bawaan Person untuk me-return konfirmasi identitasnya
    @Override
    public String getPeran() {
        return "Mekanik";
    }
}