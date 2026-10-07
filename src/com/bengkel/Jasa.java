package com.bengkel;

public class Jasa extends ItemLayanan {
    private String kategori;
    private int estimasiWaktu;

    public Jasa(String kode, String nama, double harga, String kategori, int estimasiWaktu) {
        super(kode, nama, harga);
        setKategori(kategori);
        setEstimasiWaktu(estimasiWaktu);
    }

    public Jasa() { super(); }

    public void setKategori(String kategori) { this.kategori = Validasi.wajibIsi(kategori, "Kategori"); }
    public void setEstimasiWaktu(int estimasiWaktu) { this.estimasiWaktu = Validasi.tidakNegatif(estimasiWaktu, "Estimasi waktu"); }

    public String getKategori() { return kategori; }
    public int getEstimasiWaktu() { return estimasiWaktu; }

    @Override
    public String getJenisItem() { return "JASA"; }
}
