package com.bengkel;

// Child class dari Kendaraan. Digunakan jika bengkel mengekspansi layanannya ke tipe kendaraan lain (mobil/truk).
public class Motor extends Kendaraan {
    // Field penanda klasifikasi motor (matic, bebek, sport).
    private String jenisMotor;

    // Constructor untuk load state dari memory/file.
    public Motor(String noPolisi, String merk, String tipe, int tahun,
                 String warna, int km, String noRangka, String noMesin,
                 String jenisMotor) {
        // Binding data dasar kendaraan bermotor ke parent constructor.
        super(noPolisi, merk, tipe, tahun, warna, km, noRangka, noMesin);
        setJenisMotor(jenisMotor);
    }

    // Constructor input manual.
    public Motor() {
        super(); // Input plat, merk, km, dll.
        setJenisMotor(Input.bacaString("Jenis Motor (Matic/Bebek/Sport) = "));
    }

    public void setJenisMotor(String jenisMotor) { this.jenisMotor = jenisMotor; }
    public String getJenisMotor() { return jenisMotor; }

    // Memastikan polymorphism nanti berjalan benar saat ditanya jenis kendaraannya.
    @Override
    public String getJenisKendaraan() {
        return "Motor";
    }
}