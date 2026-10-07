package com.bengkel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Transaksi implements Cetak {
    private static final DateTimeFormatter FORMAT_TANGGAL = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final DateTimeFormatter FORMAT_PKB = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Locale ID = Locale.forLanguageTag("id-ID");
    private static final String NL = System.lineSeparator();

    /** Satu baris detail: item + jumlah. */
    public static class Baris {
        private final ItemLayanan item;
        private int qty;

        private Baris(ItemLayanan item, int qty) {
            this.item = item;
            this.qty = qty;
        }

        public ItemLayanan getItem() { return item; }
        public int getQty() { return qty; }
        public double getSubtotal() { return item.getHarga() * qty; }
    }

    private String noPKB;
    private String tanggalWaktu;
    private int kmSaatIni;
    private Kendaraan kendaraan;
    private Customer customer;
    private Mekanik mekanik;
    private String saranPerbaikan;
    private String garansi;
    private double totalJasa;
    private double totalPart;
    private double grandTotal;

    private final List<Baris> daftarItemLayanan = new ArrayList<>();

    public Transaksi(String noPKB, String tanggalWaktu, int kmSaatIni, Kendaraan kendaraan, Customer customer, Mekanik mekanik, String saranPerbaikan, String garansi) {
        setNoPKB(noPKB);
        setTanggalWaktu(tanggalWaktu);
        setKendaraan(kendaraan);
        setKmSaatIni(kmSaatIni);
        setCustomer(customer);
        setMekanik(mekanik);
        setSaranPerbaikan(saranPerbaikan);
        setGaransi(garansi);
        hitungUlangTotal();
    }

    public Transaksi() {
        this.noPKB = "-";
        this.tanggalWaktu = "-";
        this.saranPerbaikan = "-";
        this.garansi = "-";
        this.totalJasa = 0;
        this.totalPart = 0;
        this.grandTotal = 0;
    }

    /** Format nomor PKB: PKB-yyyyMMdd-NNN. */
    public static String buatNoPKB(LocalDateTime waktu, int urut) {
        return String.format("PKB-%s-%03d", waktu.format(FORMAT_PKB), urut);
    }

    public static String formatTanggal(LocalDateTime waktu) {
        return waktu.format(FORMAT_TANGGAL);
    }

    public void tambahItem(ItemLayanan item) {
        tambahItem(item, 1);
    }

    public void tambahItem(ItemLayanan item, int qty) {
        if (item == null) throw new IllegalArgumentException("Item tidak boleh null.");
        if (qty <= 0) throw new IllegalArgumentException("Jumlah harus lebih dari 0.");
        // Part: stok dikurangi dulu; kalau kurang, exception dan item tidak ditambahkan.
        if (item instanceof Part) {
            ((Part) item).kurangiStok(qty);
        }
        for (Baris b : daftarItemLayanan) {
            if (b.item == item) {
                b.qty += qty;
                hitungUlangTotal();
                return;
            }
        }
        daftarItemLayanan.add(new Baris(item, qty));
        hitungUlangTotal();
    }

    public List<Baris> getDaftarItemLayanan() {
        return Collections.unmodifiableList(daftarItemLayanan);
    }

    private double totalPerJenis(String jenis) {
        double total = 0;
        for (Baris b : daftarItemLayanan) {
            if (b.item.getJenisItem().equals(jenis)) total += b.getSubtotal();
        }
        return total;
    }

    private void hitungUlangTotal() {
        totalJasa = totalPerJenis("JASA");
        totalPart = totalPerJenis("PART");
        grandTotal = totalJasa + totalPart;
    }

    public double hitungTotal() {
        hitungUlangTotal();
        return grandTotal;
    }

    private static String teks(String s) { return (s == null || s.isEmpty()) ? "-" : s; }

    private static String rupiah(double nilai) {
        return String.format(ID, "Rp %,.0f", nilai);
    }

    @Override
    public void struk() {
        String garis = "=".repeat(54);
        String strip = "-".repeat(54);
        StringBuilder sb = new StringBuilder();
        sb.append(garis).append(NL);
        sb.append(String.format("%-54s", TOKO)).append(NL);
        sb.append(ALAMAT).append(NL);
        sb.append(garis).append(NL);
        sb.append(String.format("No PKB      : %s%n", teks(noPKB)));
        sb.append(String.format("Tanggal     : %s%n", teks(tanggalWaktu)));
        sb.append(String.format("Pelanggan   : %s (No. Customer: %s)%n",
                customer == null ? "-" : teks(customer.getNama()),
                customer == null ? "-" : teks(customer.getNoCustomer())));
        sb.append(String.format("Mekanik     : %s (Keahlian: %s)%n",
                mekanik == null ? "-" : teks(mekanik.getNama()),
                mekanik == null ? "-" : teks(mekanik.getSpesialisasi())));
        sb.append(String.format("Kendaraan   : %s%n", kendaraan == null ? "-"
                : kendaraan.getNoPolisi() + " - " + kendaraan.getMerk() + " " + kendaraan.getTipe()
                  + " (" + kendaraan.getJenisKendaraan() + ")"));
        sb.append(String.format("KM Masuk    : %,d%n", kmSaatIni));
        sb.append(strip).append(NL);
        sb.append(String.format("%-7s %-20s %3s %10s %10s%n", "Jenis", "Nama", "Qty", "Harga", "Subtotal"));
        sb.append(strip).append(NL);
        for (Baris b : daftarItemLayanan) {
            sb.append(String.format(ID, "%-7s %-20.20s %3d %,10.0f %,10.0f%n",
                    b.item.getJenisItem(), b.item.getNama(), b.qty, b.item.getHarga(), b.getSubtotal()));
        }
        sb.append(strip).append(NL);
        sb.append(String.format("Total Jasa  : %s%n", rupiah(getTotalJasa())));
        sb.append(String.format("Total Part  : %s%n", rupiah(getTotalPart())));
        sb.append(garis).append(NL);
        sb.append(String.format("GRAND TOTAL : %s%n", rupiah(getGrandTotal())));
        sb.append(String.format("Saran       : %s%n", teks(saranPerbaikan)));
        sb.append(String.format("Garansi     : %s%n", teks(garansi)));
        sb.append(garis).append(NL);
        System.out.print(sb);
    }

    public void setNoPKB(String noPKB) { this.noPKB = Validasi.wajibIsi(noPKB, "No PKB"); }
    public void setTanggalWaktu(String tanggalWaktu) { this.tanggalWaktu = Validasi.wajibIsi(tanggalWaktu, "Tanggal"); }
    public void setKmSaatIni(int kmSaatIni) {
        Validasi.tidakNegatif(kmSaatIni, "KM");
        if (kendaraan != null && kmSaatIni < kendaraan.getKm()) {
            throw new IllegalArgumentException("KM (" + kmSaatIni + ") tidak boleh lebih kecil dari KM terakhir kendaraan (" + kendaraan.getKm() + ").");
        }
        this.kmSaatIni = kmSaatIni;
    }
    public void setKendaraan(Kendaraan kendaraan) {
        if (kendaraan == null) throw new IllegalArgumentException("Kendaraan tidak boleh null.");
        this.kendaraan = kendaraan;
    }
    public void setCustomer(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer tidak boleh null.");
        this.customer = customer;
    }
    public void setMekanik(Mekanik mekanik) {
        if (mekanik == null) throw new IllegalArgumentException("Mekanik tidak boleh null.");
        this.mekanik = mekanik;
    }
    public void setSaranPerbaikan(String saranPerbaikan) { this.saranPerbaikan = teks(saranPerbaikan); }
    public void setGaransi(String garansi) { this.garansi = teks(garansi); }

    public String getNoPKB() { return noPKB; }
    public String getTanggalWaktu() { return tanggalWaktu; }
    public int getKmSaatIni() { return kmSaatIni; }
    public Kendaraan getKendaraan() { return kendaraan; }
    public Customer getCustomer() { return customer; }
    public Mekanik getMekanik() { return mekanik; }
    public String getSaranPerbaikan() { return saranPerbaikan; }
    public String getGaransi() { return garansi; }
    public double getTotalJasa() { return totalJasa; }
    public double getTotalPart() { return totalPart; }
    public double getGrandTotal() { return grandTotal; }
}
