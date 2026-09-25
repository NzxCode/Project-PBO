package com.bengkel;

/*
 * Child Class: Motor
 */
public class Motor extends Kendaraan {
    private String jenisMotor;

    //Constructor #1
    public Motor(String noPolisi, String merk, String tipe, int tahun,
                 String warna, int km, String noRangka, String noMesin,
                 String jenisMotor) {
        super(noPolisi, merk, tipe, tahun, warna, km, noRangka, noMesin);
        setJenisMotor(jenisMotor);
    }

    //Constructor #2: ask user to input each attribute value
    public Motor() {
        super(); //input atribut milik Kendaraan
        setJenisMotor(Input.bacaString("Jenis Motor (Matic/Bebek/Sport) = "));
    }

    //setter and getter
    public void setJenisMotor(String jenisMotor) {
        this.jenisMotor = jenisMotor;
    }
    public String getJenisMotor() {
        return jenisMotor;
    }

    /*
     * method overriding
     * @overriding getJenisKendaraan dari parent class "Kendaraan"
     */
    @Override
    public String getJenisKendaraan() {
        return "Motor";
    }
}
