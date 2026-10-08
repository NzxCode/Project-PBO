package com.bengkel;

// Blueprint dasar otomotif. Dibuat abstract agar menahan instansiasi langsung menjadi object Kendaraan.
public abstract class Kendaraan {
    // Data di-hide (private) sesuai kaidah enkapsulasi ketat.
    private String noPolisi;
    private String merk;
    private String tipe;
    private int tahun;
    private String warna;
    private int km;
    private String noRangka;
    private String noMesin;

    // Constructor parameterized untuk injeksi data dari database/file teks.
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

    // Constructor interaktif untuk flow input pendaftaran kendaraan baru.
    public Kendaraan() {
        setNoPolisi(Input.bacaString("No Polisi = "));
        setMerk(Input.bacaString("Merk = "));
        setTipe(Input.bacaString("Tipe = "));
        setTahun(Input.bacaInt("Tahun = ", 1900, 2100)); // Validasi langsung di input prompt
        setWarna(Input.bacaString("Warna = "));
        setKm(Input.bacaInt("KM = ", 0, Integer.MAX_VALUE));
        setNoRangka(Input.bacaString("No Rangka = "));
        setNoMesin(Input.bacaString("No Mesin = "));
    }

    // Setter dasar
    public void setNoPolisi(String noPolisi) { this.noPolisi = noPolisi; }
    public void setMerk(String merk) { this.merk = merk; }
    public void setTipe(String tipe) { this.tipe = tipe; }
    
    // Setter dengan proteksi integritas data (Exception Handling).
    public void setTahun(int tahun) {
        // Melempar exception jika tahun pembuatan di luar batas logika otomotif.
        if (tahun < 1900) throw new IllegalArgumentException("Tahun tidak valid: " + tahun);
        this.tahun = tahun;
    }
    
    public void setWarna(String warna) { this.warna = warna; }
    
    // Setter untuk mencegah input jarak tempuh negatif yang bisa merusak kalkulasi.
    public void setKm(int km) {
        if (km < 0) throw new IllegalArgumentException("KM tidak boleh negatif: " + km);
        this.km = km;
    }
    
    public void setNoRangka(String noRangka) { this.noRangka = noRangka; }
    public void setNoMesin(String noMesin) { this.noMesin = noMesin; }

    // Accessor method (Getter)
    public String getNoPolisi() { return noPolisi; }
    public String getMerk() { return merk; }
    public String getTipe() { return tipe; }
    public int getTahun() { return tahun; }
    public String getWarna() { return warna; }
    public int getKm() { return km; }
    public String getNoRangka() { return noRangka; }
    public String getNoMesin() { return noMesin; }

    // Memaksa class turunan (seperti Motor atau Mobil) untuk me-return jenisnya secara spesifik.
    public abstract String getJenisKendaraan();
}