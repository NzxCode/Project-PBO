package com.bengkel;

public class Jasa extends ItemLayanan {
    private String kategori;
    private int estimasiWaktu;

    public Jasa(String kode, String nama, double harga, String kategori, int estimasiWaktu) {
        super(kode, nama, harga);
        this.kategori = kategori;
        this.estimasiWaktu = estimasiWaktu;
    }

    public Jasa() { super(); }

    public void setKategori(String kategori) { this.kategori = kategori; }
    public void setEstimasiWaktu(int estimasiWaktu) { this.estimasiWaktu = estimasiWaktu; }

    public String getKategori() { return kategori; }
    public int getEstimasiWaktu() { return estimasiWaktu; }

    @Override
    public String getJenisItem() { return "JASA"; }
}