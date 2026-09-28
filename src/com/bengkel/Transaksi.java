package com.bengkel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*
 * Class Transaksi (PKB / Perintah Kerja Bengkel)
 * extends: -    implements: Cetak
 */
public class Transaksi implements Cetak {
    private String noPKB;
    private String tanggalWaktu;
    private int kmSaatIni;
    /** Association **/
    private Kendaraan kendaraan;
    private Customer customer;
    private Mekanik mekanik;
    private List<ItemLayanan> listDetail; //1..* ItemLayanan (Part / Jasa)
    /****************/
    private String saranPerbaikan;
    private String garansi;
    private double totalJasa;
    private double totalPart;
    private double grandTotal;

    private static final Locale ID = Locale.forLanguageTag("id-ID");
    private static final int LEBAR = 66;

    //Constructor #1
    public Transaksi(String noPKB, String tanggalWaktu, int kmSaatIni,
                     Kendaraan kendaraan, Customer customer, Mekanik mekanik,
                     String saranPerbaikan, String garansi) {
        listDetail = new ArrayList<ItemLayanan>();
        setNoPKB(noPKB);
        setTanggalWaktu(tanggalWaktu);
        setKmSaatIni(kmSaatIni);
        setKendaraan(kendaraan);
        setCustomer(customer);
        setMekanik(mekanik);
        setSaranPerbaikan(saranPerbaikan);
        setGaransi(garansi);
    }

    //Constructor #2: ask user to input each attribute value
    //(kendaraan, customer, mekanik diisi dari data yang sudah ada via setter)
    public Transaksi() {
        listDetail = new ArrayList<ItemLayanan>();
        setNoPKB(Input.bacaString("No PKB = "));
        String waktu = Input.bacaStringKosong("Tanggal & Waktu dd-MM-yyyy HH:mm (kosong = sekarang) = ");
        if (waktu.isEmpty()) {
            waktu = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        }
        setTanggalWaktu(waktu);
        setKmSaatIni(Input.bacaInt("KM Saat Ini = ", 0, Integer.MAX_VALUE));
        setSaranPerbaikan(Input.bacaString("Saran Perbaikan = "));
        setGaransi(Input.bacaString("Garansi = "));
    }

    //setters - public
    public void setNoPKB(String noPKB) {
        this.noPKB = noPKB;
    }
    public void setTanggalWaktu(String tanggalWaktu) {
        this.tanggalWaktu = tanggalWaktu;
    }
    public void setKmSaatIni(int kmSaatIni) {
        if (kmSaatIni < 0) {
            throw new IllegalArgumentException("KM tidak boleh negatif: " + kmSaatIni);
        }
        this.kmSaatIni = kmSaatIni;
    }
    public void setKendaraan(Kendaraan kendaraan) {
        this.kendaraan = kendaraan;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setMekanik(Mekanik mekanik) {
        this.mekanik = mekanik;
    }
    public void setSaranPerbaikan(String saranPerbaikan) {
        this.saranPerbaikan = saranPerbaikan;
    }
    public void setGaransi(String garansi) {
        this.garansi = garansi;
    }

    //getters - public
    public String getNoPKB() {
        return noPKB;
    }
    public String getTanggalWaktu() {
        return tanggalWaktu;
    }
    public int getKmSaatIni() {
        return kmSaatIni;
    }
    public Kendaraan getKendaraan() {
        return kendaraan;
    }
    public Customer getCustomer() {
        return customer;
    }
    public Mekanik getMekanik() {
        return mekanik;
    }
    public String getSaranPerbaikan() {
        return saranPerbaikan;
    }
    public String getGaransi() {
        return garansi;
    }
    public double getTotalJasa() {
        return totalJasa;
    }
    public double getTotalPart() {
        return totalPart;
    }
    public double getGrandTotal() {
        return grandTotal;
    }
    public List<ItemLayanan> getListDetail() {
        return listDetail;
    }

    //Tambah satu item (Part / Jasa) ke transaksi, lalu hitung ulang total
    public void addDetail(ItemLayanan item) {
        if (item == null) {
            throw new IllegalArgumentException("Item tidak boleh kosong");
        }
        listDetail.add(item);
        hitungTotal();
    }

    //Tambah item sebanyak qty (item yang sama dicatat qty kali di listDetail)
    public void addDetail(ItemLayanan item, int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Qty harus lebih dari 0");
        }
        for (int i = 0; i < qty; i++) {
            addDetail(item);
        }
    }

    //Hitung totalJasa, totalPart, dan grandTotal
    public double hitungTotal() {
        totalJasa = 0;
        totalPart = 0;
        for (ItemLayanan item : listDetail) {
            if (item instanceof Jasa) {
                totalJasa += item.getHarga();
            } else if (item instanceof Part) {
                totalPart += item.getHarga();
            }
        }
        grandTotal = totalJasa + totalPart;
        return grandTotal;
    }

    /*
     * method overriding (implementasi method dari interface Cetak)
     * @overriding struk dari interface "Cetak"
     */
    @Override
    public void struk() {
        hitungTotal();
        String garis1 = "=".repeat(LEBAR);
        String garis2 = "-".repeat(LEBAR);

        System.out.println(garis1);
        System.out.println(tengah(TOKO));
        System.out.println(tengah(ALAMAT));
        System.out.println(garis1);
        System.out.println("No. PKB       : " + noPKB);
        System.out.println("Tanggal/Waktu : " + tanggalWaktu);
        if (customer != null) {
            System.out.println("Customer      : " + customer.getNama() + " (" + customer.getNoCustomer()
                    + ") / " + customer.getNoHP());
        }
        if (kendaraan != null) {
            System.out.println("Kendaraan     : " + kendaraan.getNoPolisi() + " - " + kendaraan.getMerk()
                    + " " + kendaraan.getTipe() + " (" + kendaraan.getTahun() + "), "
                    + kendaraan.getWarna() + " [" + kendaraan.getJenisKendaraan() + "]");
        }
        System.out.println("KM Saat Ini   : " + String.format(ID, "%,d", kmSaatIni) + " km");
        if (mekanik != null) {
            System.out.println("Mekanik       : " + mekanik.getNama() + " (" + mekanik.getSpesialisasi() + ")");
        }
        System.out.println(garis2);
        System.out.printf(ID, "%-3s %-28s %4s %13s %14s%n", "No", "Item", "Qty", "Harga", "Subtotal");
        System.out.println(garis2);
        //Item yang sama (kode sama) digabung jadi satu baris dengan Qty
        Map<String, ItemLayanan> itemUnik = new LinkedHashMap<String, ItemLayanan>();
        Map<String, Integer> jumlah = new LinkedHashMap<String, Integer>();
        for (ItemLayanan item : listDetail) {
            itemUnik.putIfAbsent(item.getKode(), item);
            jumlah.merge(item.getKode(), 1, Integer::sum);
        }
        int no = 1;
        for (String kode : itemUnik.keySet()) {
            ItemLayanan item = itemUnik.get(kode);
            int qty = jumlah.get(kode);
            System.out.printf(ID, "%-3d %-28s %4d %13s %14s%n",
                    no++,
                    potong(item.getNama() + " [" + item.getJenisItem() + "]", 28),
                    qty,
                    String.format(ID, "%,.0f", item.getHarga()),
                    String.format(ID, "%,.0f", item.getHarga() * qty));
        }
        System.out.println(garis2);
        System.out.printf(ID, "%-40s %25s%n", "Total Jasa", "Rp " + String.format(ID, "%,.0f", totalJasa));
        System.out.printf(ID, "%-40s %25s%n", "Total Part", "Rp " + String.format(ID, "%,.0f", totalPart));
        System.out.printf(ID, "%-40s %25s%n", "GRAND TOTAL", "Rp " + String.format(ID, "%,.0f", grandTotal));
        System.out.println(garis2);
        System.out.println("Saran Perbaikan : " + saranPerbaikan);
        System.out.println("Garansi         : " + garansi);
        System.out.println(garis1);
        System.out.println(tengah("Terima kasih atas kepercayaan Anda"));
        System.out.println(garis1);
    }

    //Helper: teks rata tengah
    private String tengah(String teks) {
        int spasi = (LEBAR - teks.length()) / 2;
        return " ".repeat(Math.max(spasi, 0)) + teks;
    }

    //Helper: potong teks yang terlalu panjang
    private String potong(String teks, int maks) {
        if (teks.length() <= maks) {
            return teks;
        }
        return teks.substring(0, maks - 1) + ".";
    }
}