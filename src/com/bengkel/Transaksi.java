package com.bengkel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Konsep: Interface Implementation
// Class Transaksi terikat kontrak dengan interface 'Cetak', yang artinya 
// class ini WAJIB menyediakan implementasi (override) dari method struk().
public class Transaksi implements Cetak {
    
    // Properti dasar milik transaksi
    private String noPKB;
    private String tanggalWaktu;
    private int kmSaatIni;
    
    // Konsep: Aggregation / Composition (Has-a relationship)
    // Transaksi TIDAK menduplikasi data, melainkan menyimpan "alamat memori" (referensi) 
    // dari object Kendaraan, Customer, dan Mekanik yang sudah ada di database RAM.
    private Kendaraan kendaraan;
    private Customer customer;
    private Mekanik mekanik;
    
    // Konsep: Collection & Polymorphism
    // Menggunakan tipe List dengan parameter parent (ItemLayanan).
    // Array ini bisa diisi kombinasi object dari tipe 'Part' maupun 'Jasa' sekaligus.
    private List<ItemLayanan> listItem; 
    
    // Properti pendukung logistik perbaikan
    private String saranPerbaikan;
    private String garansi;
    
    // Variabel untuk menampung cache kalkulasi uang
    private double totalJasa;
    private double totalPart;
    private double grandTotal;

    // Set konstanta pelokalan ke Indonesia untuk formatting pemisah ribuan Rupiah (Titik/Koma).
    private static final Locale ID = Locale.forLanguageTag("id-ID");
    // Konstanta penentu ukuran lebar struk saat di-print di terminal console.
    private static final int LEBAR = 66;

    // Constructor Parameterized: Menerima data utuh saat load file txt di awal aplikasi jalan (Booting).
    public Transaksi(String noPKB, String tanggalWaktu, int kmSaatIni,
                     Kendaraan kendaraan, Customer customer, Mekanik mekanik,
                     String saranPerbaikan, String garansi) {
        // List harus di-instansiasi agar tidak memicu NullPointerException saat diisi.
        listItem = new ArrayList<ItemLayanan>();
        
        // Mapping argument ke encapsulation
        setNoPKB(noPKB);
        setTanggalWaktu(tanggalWaktu);
        setKmSaatIni(kmSaatIni);
        setKendaraan(kendaraan);
        setCustomer(customer);
        setMekanik(mekanik);
        setSaranPerbaikan(saranPerbaikan);
        setGaransi(garansi);
    }

    // Constructor Default: Berjalan saat petugas kasir ingin membuat PKB baru secara live.
    public Transaksi() {
        listItem = new ArrayList<ItemLayanan>();
        setNoPKB(Input.bacaString("No PKB = "));
        
        // Logika Auto-Timestamp: Kalau kasir membiarkan kosong, sistem menarik waktu dari OS.
        String waktu = Input.bacaStringKosong("Tanggal & Waktu dd-MM-yyyy HH:mm (kosong = sekarang) = ");
        if (waktu.isEmpty()) {
            waktu = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        }
        setTanggalWaktu(waktu);
        
        // Guard input agar angka KM wajar (minimal 0)
        setKmSaatIni(Input.bacaInt("KM Saat Ini = ", 0, Integer.MAX_VALUE));
        setSaranPerbaikan(Input.bacaString("Saran Perbaikan = "));
        setGaransi(Input.bacaString("Garansi = "));
    }

    // Setter Standard
    public void setNoPKB(String noPKB) { this.noPKB = noPKB; }
    public void setTanggalWaktu(String tanggalWaktu) { this.tanggalWaktu = tanggalWaktu; }
    
    // Konsep: Exception Throwing dalam Mutator
    public void setKmSaatIni(int kmSaatIni) {
        if (kmSaatIni < 0) {
            // Mencegah data corrupt karena odometer kendaraan tidak bisa mundur.
            throw new IllegalArgumentException("KM tidak boleh negatif: " + kmSaatIni);
        }
        this.kmSaatIni = kmSaatIni;
    }
    
    // Dependency Injection: Method untuk menyematkan object rill dari luar ke dalam transaksi ini.
    public void setKendaraan(Kendaraan kendaraan) { this.kendaraan = kendaraan; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setMekanik(Mekanik mekanik) { this.mekanik = mekanik; }
    
    public void setSaranPerbaikan(String saranPerbaikan) { this.saranPerbaikan = saranPerbaikan; }
    public void setGaransi(String garansi) { this.garansi = garansi; }

    // Getter Standard
    public String getNoPKB() { return noPKB; }
    public String getTanggalWaktu() { return tanggalWaktu; }
    public int getKmSaatIni() { return kmSaatIni; }
    
    // Me-return referensi memory address dari objek terkait
    public Kendaraan getKendaraan() { return kendaraan; }
    public Customer getCustomer() { return customer; }
    public Mekanik getMekanik() { return mekanik; }
    
    public String getSaranPerbaikan() { return saranPerbaikan; }
    public String getGaransi() { return garansi; }
    public double getTotalJasa() { return totalJasa; }
    public double getTotalPart() { return totalPart; }
    public double getGrandTotal() { return grandTotal; }
    
    // Getter list ini sangat krusial saat modul DataFile melakukan Serialisasi (menyimpan transaksi ke file txt)
    public List<ItemLayanan> getListItem() { return listItem; }

    // Method untuk push object belanjaan (Part/Jasa) ke dalam list.
    public void addItem(ItemLayanan item) {
        listItem.add(item);
        // Desain Reaktif: Tiap ada part yang masuk keranjang, jalankan ulang kalkulasi secara otomatis.
        hitungTotal();
    }

    // Konsep: Runtime Polymorphism / Type Identification
    public double hitungTotal() {
        totalJasa = 0;
        totalPart = 0;
        
        // Kita melooping abstract class (ItemLayanan).
        for (ItemLayanan item : listItem) {
            // Evaluasi instanceof: JVM akan memeriksa apa jati diri/tipe asli object ini di dalam heap memory.
            if (item instanceof Jasa) {
                totalJasa += item.getHarga(); // Pisahkan pembukuan untuk jasa layanan
            } else if (item instanceof Part) {
                totalPart += item.getHarga(); // Pisahkan pembukuan untuk suku cadang fisik
            }
        }
        grandTotal = totalJasa + totalPart;
        return grandTotal;
    }

    // Konsep: Overriding
    // Mengeksekusi algoritma cetak struk untuk memenuhi kewajiban implements interface Cetak.
    @Override
    public void struk() {
        // Redundansi keamanan: Menghitung total ulang sebelum rendering nota, memastikan tidak ada selisih.
        hitungTotal();
        
        // Membantu merender garis pemisah UI sepanjang batasan LEBAR.
        String garis1 = "=".repeat(LEBAR);
        String garis2 = "-".repeat(LEBAR);

        System.out.println(garis1);
        // Mengakses konstanta static dari Interface secara langsung (TOKO dan ALAMAT).
        System.out.println(tengah(TOKO)); 
        System.out.println(tengah(ALAMAT));
        System.out.println(garis1);
        
        System.out.println("No. PKB       : " + noPKB);
        System.out.println("Tanggal/Waktu : " + tanggalWaktu);
        
        // Null checks: Selalu pastikan pointer object tidak null sebelum memanggil getternya.
        // Jika tidak di-check, bisa menyebabkan aplikasi meledak (NullPointerException).
        if (customer != null) {
            System.out.println("Customer      : " + customer.getNama() + " (" + customer.getNoCustomer() + ") / " + customer.getNoHP());
        }
        if (kendaraan != null) {
            // Polymorphic call pada 'getJenisKendaraan()' (akan me-return Motor atau jenis lainnya).
            System.out.println("Kendaraan     : " + kendaraan.getNoPolisi() + " - " + kendaraan.getMerk()
                    + " " + kendaraan.getTipe() + " (" + kendaraan.getTahun() + "), "
                    + kendaraan.getWarna() + " [" + kendaraan.getJenisKendaraan() + "]");
        }
        
        // Menginjeksi Format mata uang/ribuan (Locale ID) ke dalam format string.
        System.out.println("KM Saat Ini   : " + String.format(ID, "%,d", kmSaatIni) + " km");
        
        if (mekanik != null) {
            System.out.println("Mekanik       : " + mekanik.getNama() + " (" + mekanik.getSpesialisasi() + ")");
        }
        
        System.out.println(garis2);
        System.out.printf(ID, "%-3s %-34s %-6s %17s%n", "No", "Item", "Jenis", "Harga");
        System.out.println(garis2);
        
        int no = 1;
        // Looping untuk merender detail isi keranjang.
        for (ItemLayanan item : listItem) {
            System.out.printf(ID, "%-3d %-34s %-6s %17s%n",
                    no++,
                    potong(item.getNama(), 34),
                    item.getJenisItem(), // Polymorphic call (Tergantung child-nya Part atau Jasa)
                    String.format(ID, "%,.0f", item.getHarga()));
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

    // Encapsulation (Private Method): Helper murni logika internal class, tidak perlu diekspos ke public.
    // Tugasnya menata teks berada tepat di bagian tengah dari struk printer.
    private String tengah(String teks) {
        int spasi = (LEBAR - teks.length()) / 2;
        return " ".repeat(Math.max(spasi, 0)) + teks; // Menggeser pakai karakter whitespace
    }

    // Encapsulation (Private Method): Helper text-limiter.
    // Mencegah nama item yang terlalu panjang merusak grid layout tabel di terminal.
    private String potong(String teks, int maks) {
        if (teks.length() <= maks) {
            return teks;
        }
        return teks.substring(0, maks - 1) + "."; // Tambahkan titik untuk indikasi text terpotong
    }
}