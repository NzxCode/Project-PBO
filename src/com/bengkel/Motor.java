package com.bengkel;

public class Motor extends Kendaraan {
    private String jenisMotor;

    public Motor(String noPolisi, String merk, String tipe, int tahun, String warna, int km, String noRangka, String noMesin, String jenisMotor) {
        super(noPolisi, merk, tipe, tahun, warna, km, noRangka, noMesin);
        setJenisMotor(jenisMotor);
    }

    public Motor() { super(); }

    public void setJenisMotor(String jenisMotor) { this.jenisMotor = Validasi.wajibIsi(jenisMotor, "Jenis Motor"); }
    public String getJenisMotor() { return jenisMotor; }

    @Override
    public String getJenisKendaraan() { return "Motor " + jenisMotor; }
}
