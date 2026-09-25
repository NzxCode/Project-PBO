package com.bengkel;

public class Jasa extends ItemLayanan {
    private String kategori;
    private int estimasiWaktu; 

    public Jasa(String kode, String nama, double harga, String kategori, int estimasiWaktu) {
        super(kode, nama, harga);
        setKategori(kategori);
        setEstimasiWaktu(estimasiWaktu);
    }

    public Jasa() {
        super(); 
        setKategori(Input.bacaString("Kategori = "));
        setEstimasiWaktu(Input.bacaInt("Estimasi Waktu (menit) = ", 0, Integer.MAX_VALUE));
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    public void setEstimasiWaktu(int estimasiWaktu) {
        if (estimasiWaktu < 0) {
            throw new IllegalArgumentException("Estimasi waktu tidak boleh negatif: " + estimasiWaktu);
        }
        this.estimasiWaktu = estimasiWaktu;
    }
    public String getKategori() {
        return kategori;
    }
    public int getEstimasiWaktu() {
        return estimasiWaktu;
    }

    @Override
    public String getJenisItem() {
        return "Jasa";
    }
}
