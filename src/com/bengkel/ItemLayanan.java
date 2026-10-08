package com.bengkel;

// Super class untuk entitas barang/jasa yang bisa dimasukkan ke tagihan kasir.
public abstract class ItemLayanan {
    private String kode;
    private String nama;
    private double harga;

    // Constructor untuk inisialisasi by system (contoh: load dari txt file).
    public ItemLayanan(String kode, String nama, double harga) {
        setKode(kode);
        setNama(nama);
        setHarga(harga);
    }

    // Constructor interaktif. Penggunaan Input.bacaDouble menahan error NumberFormatException secara internal.
    public ItemLayanan() {
        setKode(Input.bacaString("Kode = "));
        setNama(Input.bacaString("Nama = "));
        setHarga(Input.bacaDouble("Harga = ", 0, Double.MAX_VALUE));
    }

    public void setKode(String kode) { this.kode = kode; }
    public void setNama(String nama) { this.nama = nama; }
    
    // Setter harga dengan penjagaan exception (mencegah harga minus).
    // Ini krusial agar tidak ada bug "diskon tak terduga" di perhitungan total.
    public void setHarga(double harga) {
        if (harga < 0) throw new IllegalArgumentException("Harga tidak boleh negatif: " + harga);
        this.harga = harga;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }

    // Klasifikasi dinamis untuk kebutuhan filter data nantinya.
    public abstract String getJenisItem();
}