package com.bengkel;

// Import library bawaan Java untuk format tanggal, waktu, list, dan lokalisasi
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Class Transaksi (PKB) yang meng-implements interface Cetak
public class Transaksi implements Cetak {
    // Atribut dasar transaksi dengan access modifier private
    private String noPKB;
    private String tanggalWaktu;
    private int kmSaatIni;
    
    /** Association (Relasi antar class) **/
    // Relasi ke object Kendaraan
    private Kendaraan kendaraan;
    // Relasi ke object Customer
    private Customer customer;
    // Relasi ke object Mekanik
    private Mekanik mekanik;
    // Relasi 1..* (one-to-many) ke ItemLayanan menggunakan struktur data List
    private List<ItemLayanan> listItem; 
    /****************/
    
    // Atribut tambahan untuk detail penyelesaian transaksi
    private String saranPerbaikan;
    private String garansi;
    private double totalJasa;
    private double totalPart;
    private double grandTotal;

    // Konstanta static final untuk set lokalisasi format ke Indonesia (ID)
    private static final Locale ID = Locale.forLanguageTag("id-ID");
    // Konstanta static final untuk mengatur lebar karakter pada output struk
    private static final int LEBAR = 66;

    // Constructor #1 untuk inisialisasi object Transaksi dengan data yang sudah ada (bypass input)
    public Transaksi(String noPKB, String tanggalWaktu, int kmSaatIni,
                     Kendaraan kendaraan, Customer customer, Mekanik mekanik,
                     String saranPerbaikan, String garansi) {
        // Menginisialisasi empty ArrayList untuk menyimpan object ItemLayanan
        listItem = new ArrayList<ItemLayanan>();
        // Melakukan assign data ke dalam setter
        setNoPKB(noPKB);
        setTanggalWaktu(tanggalWaktu);
        setKmSaatIni(kmSaatIni);
        setKendaraan(kendaraan);
        setCustomer(customer);
        setMekanik(mekanik);
        setSaranPerbaikan(saranPerbaikan);
        setGaransi(garansi);
    }

    // Constructor #2 untuk pembuatan transaksi secara interaktif di terminal
    // (Relasi object kendaraan, customer, mekanik akan di-set dari luar setelah object ini dibuat)
    public Transaksi() {
        // Menyiapkan empty ArrayList
        listItem = new ArrayList<ItemLayanan>();
        
        // Meminta input nomor PKB
        setNoPKB(Input.bacaString("No PKB = "));
        
        // Meminta input tanggal. Jika user langsung menekan enter (kosong), gunakan waktu sistem saat ini
        String waktu = Input.bacaStringKosong("Tanggal & Waktu dd-MM-yyyy HH:mm (kosong = sekarang) = ");
        if (waktu.isEmpty()) {
            // Mem-format objek Date() bawaan sistem operasi menjadi string
            waktu = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        }
        setTanggalWaktu(waktu);
        
        // Meminta input KM dengan validasi angka minimal 0
        setKmSaatIni(Input.bacaInt("KM Saat Ini = ", 0, Integer.MAX_VALUE));
        setSaranPerbaikan(Input.bacaString("Saran Perbaikan = "));
        setGaransi(Input.bacaString("Garansi = "));
    }

    // Kumpulan public setter untuk enkapsulasi atribut
    public void setNoPKB(String noPKB) {
        this.noPKB = noPKB;
    }
    public void setTanggalWaktu(String tanggalWaktu) {
        this.tanggalWaktu = tanggalWaktu;
    }
    // Setter untuk kilometer dengan logic pelemparan exception jika nilai negatif
    public void setKmSaatIni(int kmSaatIni) {
        if (kmSaatIni < 0) {
            // Melempar exception karena jarak tempuh tidak mungkin mundur
            throw new IllegalArgumentException("KM tidak boleh negatif: " + kmSaatIni);
        }
        this.kmSaatIni = kmSaatIni;
    }
    // Setter untuk meng-inject referensi object Kendaraan
    public void setKendaraan(Kendaraan kendaraan) {
        this.kendaraan = kendaraan;
    }
    // Setter untuk meng-inject referensi object Customer
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // Setter untuk meng-inject referensi object Mekanik
    public void setMekanik(Mekanik mekanik) {
        this.mekanik = mekanik;
    }
    public void setSaranPerbaikan(String saranPerbaikan) {
        this.saranPerbaikan = saranPerbaikan;
    }
    public void setGaransi(String garansi) {
        this.garansi = garansi;
    }

    // Kumpulan public getter untuk membaca data atribut
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
    // Getter untuk mengekspos list ItemLayanan (berguna saat akan write/save ke dalam file TXT)
    public List<ItemLayanan> getListItem() {
        return listItem;
    }

    // Method untuk menambah satu object item (child Jasa/Part) ke dalam list transaksi
    public void addItem(ItemLayanan item) {
        // Memasukkan object ke ArrayList
        listItem.add(item);
        // Otomatis men-trigger fungsi kalkulasi ulang setiap kali keranjang bertambah
        hitungTotal();
    }

    // Method untuk menghitung pengeluaran berdasar daftar item
    public double hitungTotal() {
        // Me-reset counter nilai menjadi 0
        totalJasa = 0;
        totalPart = 0;
        
        // Looping (iterasi) setiap element di dalam listItem
        for (ItemLayanan item : listItem) {
            // Polymorphism check: jika object aslinya adalah Jasa
            if (item instanceof Jasa) {
                totalJasa += item.getHarga();
            // Polymorphism check: jika object aslinya adalah Part
            } else if (item instanceof Part) {
                totalPart += item.getHarga();
            }
        }
        // Mengakumulasikan grand total
        grandTotal = totalJasa + totalPart;
        // Me-return nilai final
        return grandTotal;
    }

    // Meng-override method struk() sebagai kontrak dari interface Cetak
    @Override
    public void struk() {
        // Pastikan total dihitung ulang sebelum di-print ke terminal
        hitungTotal();
        
        // String builder sederhana untuk mencetak garis pemisah UI di console
        String garis1 = "=".repeat(LEBAR);
        String garis2 = "-".repeat(LEBAR);

        // Print header struk
        System.out.println(garis1);
        System.out.println(tengah(TOKO)); // TOKO adalah konstanta dari interface Cetak
        System.out.println(tengah(ALAMAT)); // ALAMAT adalah konstanta dari interface Cetak
        System.out.println(garis1);
        
        // Print informasi transaksi dasar
        System.out.println("No. PKB       : " + noPKB);
        System.out.println("Tanggal/Waktu : " + tanggalWaktu);
        
        // Pengecekan relasi object (null check) sebelum diekstrak data getternya
        if (customer != null) {
            System.out.println("Customer      : " + customer.getNama() + " (" + customer.getNoCustomer()
                    + ") / " + customer.getNoHP());
        }
        if (kendaraan != null) {
            System.out.println("Kendaraan     : " + kendaraan.getNoPolisi() + " - " + kendaraan.getMerk()
                    + " " + kendaraan.getTipe() + " (" + kendaraan.getTahun() + "), "
                    + kendaraan.getWarna() + " [" + kendaraan.getJenisKendaraan() + "]");
        }
        
        // String.format dengan Locale ID untuk menambahkan titik ribuan (misal: 10.000)
        System.out.println("KM Saat Ini   : " + String.format(ID, "%,d", kmSaatIni) + " km");
        
        if (mekanik != null) {
            System.out.println("Mekanik       : " + mekanik.getNama() + " (" + mekanik.getSpesialisasi() + ")");
        }
        
        System.out.println(garis2);
        // Print header tabel untuk daftar pembelian
        System.out.printf(ID, "%-3s %-34s %-6s %17s%n", "No", "Item", "Jenis", "Harga");
        System.out.println(garis2);
        
        int no = 1;
        // Looping untuk mem-print baris tiap object item di dalam keranjang
        for (ItemLayanan item : listItem) {
            System.out.printf(ID, "%-3d %-34s %-6s %17s%n",
                    no++,
                    potong(item.getNama(), 34), // Panggil helper method untuk menyingkat teks
                    item.getJenisItem(),
                    String.format(ID, "%,.0f", item.getHarga()));
        }
        
        // Print summary biaya akhir
        System.out.println(garis2);
        System.out.printf(ID, "%-40s %25s%n", "Total Jasa", "Rp " + String.format(ID, "%,.0f", totalJasa));
        System.out.printf(ID, "%-40s %25s%n", "Total Part", "Rp " + String.format(ID, "%,.0f", totalPart));
        System.out.printf(ID, "%-40s %25s%n", "GRAND TOTAL", "Rp " + String.format(ID, "%,.0f", grandTotal));
        
        // Print informasi penutup
        System.out.println(garis2);
        System.out.println("Saran Perbaikan : " + saranPerbaikan);
        System.out.println("Garansi         : " + garansi);
        System.out.println(garis1);
        System.out.println(tengah("Terima kasih atas kepercayaan Anda"));
        System.out.println(garis1);
    }

    // Helper method (private) untuk membuat posisi text rata tengah berdasarkan LEBAR
    private String tengah(String teks) {
        int spasi = (LEBAR - teks.length()) / 2;
        return " ".repeat(Math.max(spasi, 0)) + teks;
    }

    // Helper method (private) untuk me-limit jumlah karakter string agar tidak merusak UI tabel
    private String potong(String teks, int maks) {
        if (teks.length() <= maks) {
            return teks;
        }
        return teks.substring(0, maks - 1) + ".";
    }
}