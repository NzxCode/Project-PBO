// File: Motor.java
package com.bengkel;

// Mendeklarasikan class Motor (child class) yang mengadopsi properti dari Kendaraan
public class Motor extends Kendaraan {
    // Deklarasi field khusus tambahan
    private String jenisMotor;

    // Constructor dengan kombinasi parameter penuh dari awal
    public Motor(String noPolisi, String merk, String tipe, int tahun,
                 String warna, int km, String noRangka, String noMesin,
                 String jenisMotor) {
        // Eksekusi konstruktor super class Kendaraan untuk atribut utamanya
        super(noPolisi, merk, tipe, tahun, warna, km, noRangka, noMesin);
        // Injeksi nilai khusus motor ke setternya sendiri
        setJenisMotor(jenisMotor);
    }

    // Constructor untuk perolehan data dari CLI
    public Motor() {
        // Mengarahkan alur ke constructor bawaan parent class 
        super(); 
        // Menggunakan helper Input untuk mengisi field jenisMotor
        setJenisMotor(Input.bacaString("Jenis Motor (Matic/Bebek/Sport) = "));
    }

    // Setter untuk melakukan update tipe badan motor
    public void setJenisMotor(String jenisMotor) {
        this.jenisMotor = jenisMotor;
    }
    // Getter pengambil data tipe motor
    public String getJenisMotor() {
        return jenisMotor;
    }

    // Menyediakan implementasi kongkret untuk abstract method parent
    @Override
    public String getJenisKendaraan() {
        return "Motor";
    }
}