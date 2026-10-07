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
        setNoPolisi(noPolisi);
        setMerk(merk);
        setTipe(tipe);
        setTahun(tahun);
        setWarna(warna);
        setKm(km);
        setNoRangka(noRangka);
        setNoMesin(noMesin);
    }

    public Kendaraan() {}

    public String getNoPolisi() { return noPolisi; }
    public void setNoPolisi(String noPolisi) { this.noPolisi = Validasi.wajibIsi(noPolisi, "No Polisi").toUpperCase(); }
    public String getMerk() { return merk; }
    public void setMerk(String merk) { this.merk = Validasi.wajibIsi(merk, "Merk"); }
    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = Validasi.wajibIsi(tipe, "Tipe"); }
    public int getTahun() { return tahun; }
    public void setTahun(int tahun) { this.tahun = Validasi.tahun(tahun); }
    public String getWarna() { return warna; }
    public void setWarna(String warna) { this.warna = Validasi.wajibIsi(warna, "Warna"); }
    public int getKm() { return km; }
    public void setKm(int km) { this.km = Validasi.tidakNegatif(km, "KM"); }
    public String getNoRangka() { return noRangka; }
    public void setNoRangka(String noRangka) { this.noRangka = Validasi.wajibIsi(noRangka, "No Rangka"); }
    public String getNoMesin() { return noMesin; }
    public void setNoMesin(String noMesin) { this.noMesin = Validasi.wajibIsi(noMesin, "No Mesin"); }

    public abstract String getJenisKendaraan();
}
