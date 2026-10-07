package com.bengkel;

public class Part extends ItemLayanan {
    private String merk;
    private int stok;

    public Part(String kode, String nama, double harga, String merk, int stok) {
        super(kode, nama, harga);
        setMerk(merk);
        setStok(stok);
    }

    public Part() { super(); }

    public void setMerk(String merk) { this.merk = Validasi.wajibIsi(merk, "Merk"); }
    public void setStok(int stok) { this.stok = Validasi.tidakNegatif(stok, "Stok"); }

    public String getMerk() { return merk; }
    public int getStok() { return stok; }

    /** Mengurangi stok. Melempar exception jika jumlah tidak valid atau stok kurang. */
    public void kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0.");
        }
        if (jumlah > stok) {
            throw new IllegalArgumentException("Stok " + getNama() + " tidak cukup (sisa " + stok + ", diminta " + jumlah + ").");
        }
        this.stok -= jumlah;
    }

    @Override
    public String getJenisItem() { return "PART"; }
}
