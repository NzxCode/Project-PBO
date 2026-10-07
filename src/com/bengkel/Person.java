package com.bengkel;

// Mendeklarasikan abstract class Person yang berfungsi sebagai parent class
public abstract class Person {
    // Deklarasi atribut private untuk menerapkan prinsip encapsulation
    private String id;
    private String nama;
    private String noHP;
    private String alamat;

    // Constructor #1 dengan parameter lengkap untuk inisialisasi object secara langsung
    public Person(String id, String nama, String noHP, String alamat) {
        // Meneruskan nilai parameter ke setter untuk pengisian data
        setId(id);
        setNama(nama);
        setNoHP(noHP);
        setAlamat(alamat);
    }

    // Constructor #2 tanpa parameter untuk meminta input interaktif dari user di terminal
    public Person() {
        // Menggunakan helper Input untuk membaca string dan mengirimkannya ke setter
        setId(Input.bacaString("ID = "));
        setNama(Input.bacaString("Nama = "));
        setNoHP(Input.bacaString("No HP = "));
        setAlamat(Input.bacaString("Alamat = "));
    }

    // Kumpulan public setter untuk mengubah/mengisi nilai atribut (mutator)
    public void setId(String id) {
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // Kumpulan public getter untuk mengambil/membaca nilai atribut (accessor)
    public String getId() {
        return id;
    }
    public String getNama() {
        return nama;
    }
    public String getNoHP() {
        return noHP;
    }
    public String getAlamat() {
        return alamat;
    }

    // Abstract method yang memaksa child class untuk meng-override dan menentukan perannya
    public abstract String getPeran();
}