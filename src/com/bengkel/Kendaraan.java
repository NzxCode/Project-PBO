package com.bengkel;

public abstract class Kendaraan {
    private String noPolisi;
    private String merk;
    private String tipe;
    private int tahun;
    private String warna;
    private int km;
    private String noRangka;
    private String noMesin;

    public Kendaraan(String noPolisi, String merk, String tipe, int tahun, String warna, int km, String noRangka, String noMesin) {
        this.noPolisi = noPolisi;
        this.merk = merk;
        this.tipe = tipe;
        this.tahun = tahun;
        this.warna = warna;
        this.km = km;
        this.noRangka = noRangka;
        this.noMesin = noMesin;
    }

    public Kendaraan() {}

    public String getNoPolisi() { return noPolisi; }
    public void setNoPolisi(String noPolisi) { this.noPolisi = noPolisi; }
    public String getMerk() { return merk; }
    public void setMerk(String merk) { this.merk = merk; }
    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }
    public int getTahun() { return tahun; }
    public void setTahun(int tahun) { this.tahun = tahun; }
    public String getWarna() { return warna; }
    public void setWarna(String warna) { this.warna = warna; }
    public int getKm() { return km; }
    public void setKm(int km) { this.km = km; }
    public String getNoRangka() { return noRangka; }
    public void setNoRangka(String noRangka) { this.noRangka = noRangka; }
    public String getNoMesin() { return noMesin; }
    public void setNoMesin(String noMesin) { this.noMesin = noMesin; }

    public abstract String getJenisKendaraan();
}