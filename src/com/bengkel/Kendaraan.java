package com.bengkel;

/*
 * Abstract (Parent) Class: Kendaraan
 */
public abstract class Kendaraan {
    private String noPolisi;
    private String merk;
    private String tipe;
    private int tahun;
    private String warna;
    private int km;
    private String noRangka;
    private String noMesin;

    //Constructor #1
    public Kendaraan(String noPolisi, String merk, String tipe, int tahun,
                     String warna, int km, String noRangka, String noMesin) {
        setNoPolisi(noPolisi);
        setMerk(merk);
        setTipe(tipe);
        setTahun(tahun);
        setWarna(warna);
        setKm(km);
        setNoRangka(noRangka);
        setNoMesin(noMesin);
    }

    //Constructor #2: ask user to input each attribute value
    public Kendaraan() {
        //ask input for each attribute and call its setter
        setNoPolisi(Input.bacaString("No Polisi = "));
        setMerk(Input.bacaString("Merk = "));
        setTipe(Input.bacaString("Tipe = "));
        setTahun(Input.bacaInt("Tahun = ", 1900, 2100));
        setWarna(Input.bacaString("Warna = "));
        setKm(Input.bacaInt("KM = ", 0, Integer.MAX_VALUE));
        setNoRangka(Input.bacaString("No Rangka = "));
        setNoMesin(Input.bacaString("No Mesin = "));
    }

    //setters - public
    public void setNoPolisi(String noPolisi) {
        this.noPolisi = noPolisi;
    }
    public void setMerk(String merk) {
        this.merk = merk;
    }
    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
    public void setTahun(int tahun) {
        if (tahun < 1900) {
            throw new IllegalArgumentException("Tahun tidak valid: " + tahun);
        }
        this.tahun = tahun;
    }
    public void setWarna(String warna) {
        this.warna = warna;
    }
    public void setKm(int km) {
        if (km < 0) {
            throw new IllegalArgumentException("KM tidak boleh negatif: " + km);
        }
        this.km = km;
    }
    public void setNoRangka(String noRangka) {
        this.noRangka = noRangka;
    }
    public void setNoMesin(String noMesin) {
        this.noMesin = noMesin;
    }

    //getters - public
    public String getNoPolisi() {
        return noPolisi;
    }
    public String getMerk() {
        return merk;
    }
    public String getTipe() {
        return tipe;
    }
    public int getTahun() {
        return tahun;
    }
    public String getWarna() {
        return warna;
    }
    public int getKm() {
        return km;
    }
    public String getNoRangka() {
        return noRangka;
    }
    public String getNoMesin() {
        return noMesin;
    }

    //Abstract Method
    public abstract String getJenisKendaraan();
}
