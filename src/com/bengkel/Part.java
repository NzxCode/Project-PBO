package com.bengkel;

// Sub-class dari ItemLayanan yang merepresentasikan suku cadang fisik.
public class Part extends ItemLayanan {
    // Memiliki perilaku stok karena barang fisik bisa habis.
    private String merk;
    private int stok;

    public Part(String kode, String nama, double harga, String merk, int stok) {
        // Set harga, nama, kode di parent.
        super(kode, nama, harga);
        setMerk(merk);
        setStok(stok);
    }

    public Part() {
        // Run interaksi terminal untuk atribut super.
        super();
        setMerk(Input.bacaString("Merk = "));
        setStok(Input.bacaInt("Stok = ", 0, Integer.MAX_VALUE));
    }

    public void setMerk(String merk) { this.merk = merk; }
    
    // Setter dengan proteksi integritas inventory.
    public void setStok(int stok) {
        if (stok < 0) throw new IllegalArgumentException("Stok tidak boleh negatif: " + stok);
        this.stok = stok;
    }
    
    public String getMerk() { return merk; }
    public int getStok() { return stok; }

    // Fungsionalitas bisnis khusus untuk memotong stok saat barang laku.
    public void kurangiStok(int jumlah) {
        // Guard clause untuk memblokir pengurangan dengan nilai 0 atau negatif.
        if (jumlah <= 0) throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        // Guard clause mencegah insiden overselling (minus inventory).
        if (jumlah > stok) throw new IllegalArgumentException("Stok " + getNama() + " tidak cukup (sisa " + stok + ")");
        stok -= jumlah; // Mutasi stok yang aman.
    }

    @Override
    public String getJenisItem() {
        return "Part";
    }
}