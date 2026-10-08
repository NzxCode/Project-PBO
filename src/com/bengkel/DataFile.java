package com.bengkel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Konsep: Data Access Object (DAO) Pattern / Persistence Layer
// Segala urusan I/O (Tulis & Baca file ke penyimpanan fisik komputer) diisolasi di dalam class ini.
// Sehingga class bisnis (Model) dan App (Controller) tidak perlu memikirkan rumitnya urusan file txt.
public class DataFile {
    
    // Penentuan alamat folder relatif penyimpanan agar konsisten.
    private static final String FOLDER = "data";
    // Konfigurasi letak flat-file database layaknya tabel pada RDBMS konvensional.
    public static final String FILE_KENDARAAN = FOLDER + "/kendaraan.txt";
    public static final String FILE_CUSTOMER  = FOLDER + "/customer.txt";
    public static final String FILE_MEKANIK   = FOLDER + "/mekanik.txt";
    public static final String FILE_ITEM      = FOLDER + "/item.txt";
    public static final String FILE_TRANSAKSI = FOLDER + "/transaksi.txt";

    // Modul Core: Membaca teks mentah ke Array (Deserialization).
    private static ArrayList<String[]> bacaBaris(String path) {
        ArrayList<String[]> hasil = new ArrayList<String[]>();
        // Mapping file ke RAM
        File myObj = new File(path);
        
        // Guard check: Jika pertama kali program di-run, folder data mungkin belum terbuat.
        // Langsung return list kosong daripada membiarkan FileNotFoundException meledakkan sistem.
        if (!myObj.exists()) {
            return hasil; 
        }
        
        // Konsep: Exception Handling (Try-Catch)
        // Wajib diaplikasikan pada I/O Java karena sistem operasi bisa saja mencabut izin baca sewaktu-waktu.
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                
                // Sanitasi: Abaikan baris enter (white space) yang terselip agar array tidak out of bounds.
                if (data.trim().isEmpty()) {
                    continue;
                }
                
                // Parsing CSV: Membelah teks flat menjadi array berdasarkan delimiter koma (,).
                String[] str = data.split(",", -1);
                
                // Membersihkan spasi liar hasil ketikan manual di setiap elemen array.
                for (int i = 0; i < str.length; i++) {
                    str[i] = str[i].trim();
                }
                hasil.add(str);
            }
            myReader.close(); // Penting untuk melepaskan resource RAM dan System stream.
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return hasil;
    }

    // Modul Core: Menulis List Object menjadi bentuk teks (Serialization).
    private static void tulisBaris(String path, ArrayList<String> baris) {
        try {
            File folder = new File(FOLDER);
            if (!folder.exists()) {
                // Feature auto-recovery direktori. Jika folder terhapus OS, Java yang buatkan folder barunya.
                folder.mkdirs();
            }
            
            // FileWriter digunakan untuk proses Overwrite (menimpa file lama secara keseluruhan).
            // Ini untuk memastikan sinkronisasi data master.
            FileWriter fw = new FileWriter(path); 
            for (String b : baris) {
                fw.write(b); // Menembakkan string
                fw.write(System.lineSeparator()); // Meng-inject enter (\n atau \r\n tergantung OS)
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Helper: Menggabungkan properti object (Object...) menjadi 1 baris string utuh gaya CSV.
    private static String gabung(Object... kolom) {
        // Pemakaian StringBuilder sangat dianjurkan untuk operasi gabung string di dalam loop (Performa jauh lebih ringan).
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < kolom.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            // Proteksi CSV: Jika di dalam properti nama ada unsur "koma", sistem mereplace-nya dengan "titik-koma" (;).
            // Kalau tidak diganti, parser CSV (split by comma) kita nanti akan hancur lebur kolomnya bergeser.
            sb.append(String.valueOf(kolom[i]).replace(",", ";").replace("\r", " ").replace("\n", " "));
        }
        return sb.toString();
    }

    // Helper logger (Tracking error) agar developer mudah mendeteksi baris txt mana yang rusak (corrupt).
    private static void laporSkip(String namaFile, int nomor, Exception e) {
        System.out.println("[" + namaFile + "] baris " + nomor + " dilewati: " + e.getMessage());
    }

    /* ========================= Object Relational Mapping (ORM) Manual ========================= */

    // Proses konversi file kendaraan.txt menuju representasi object OOP Kendaraan.
    public static ArrayList<Kendaraan> bacaKendaraan() {
        ArrayList<Kendaraan> list = new ArrayList<Kendaraan>();
        int nomor = 0;
        
        for (String[] str : bacaBaris(FILE_KENDARAAN)) {
            nomor++;
            try {
                // Sistem mengidentifikasi klasifikasi object (Tipe Kendaraan).
                if (str[0].equals("Motor")) {
                    // Object instansiasi dengan menyuntikkan data array index ke parameter konstruktor parent.
                    Kendaraan k = new Motor(str[1], str[2], str[3], Integer.parseInt(str[4]), str[5], Integer.parseInt(str[6]), str[7], str[8], str[9]);
                    list.add(k);
                } else {
                    System.out.println("[kendaraan.txt] baris " + nomor + " dilewati: jenis tidak dikenal " + str[0]);
                }
            // Multi-catch exception handling: Mencegah error salah konversi integer, atau jumlah kolom array kurang.
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                laporSkip("kendaraan.txt", nomor, e);
            }
        }
        return list;
    }

    // Proses konversi list object menjadi string file Kendaraan.
    public static void simpanKendaraan(ArrayList<Kendaraan> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Kendaraan k : list) {
            // Konsep: Runtime Downcasting 
            // JVM mengecek wujud object. Jika dia adalah anak dari Motor...
            if (k instanceof Motor) {
                // ...Maka lakukan downcast agar bisa menarik properti spesifik jenisMotor yang tidak ada di parent.
                Motor m = (Motor) k; 
                baris.add(gabung("Motor", m.getNoPolisi(), m.getMerk(), m.getTipe(), m.getTahun(),
                        m.getWarna(), m.getKm(), m.getNoRangka(), m.getNoMesin(), m.getJenisMotor()));
            }
        }
        tulisBaris(FILE_KENDARAAN, baris); // Simpan ke disk.
    }

    // Sama seperti atas, instansiasi object Customer.
    public static ArrayList<Customer> bacaCustomer() {
        ArrayList<Customer> list = new ArrayList<Customer>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_CUSTOMER)) {
            nomor++;
            try {
                list.add(new Customer(str[0], str[1], str[2], str[3], str[4], str[5]));
            } catch (ArrayIndexOutOfBoundsException e) {
                laporSkip("customer.txt", nomor, e);
            }
        }
        return list;
    }

    public static void simpanCustomer(ArrayList<Customer> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Customer c : list) {
            baris.add(gabung(c.getId(), c.getNama(), c.getNoHP(), c.getAlamat(),
                    c.getNoCustomer(), c.getEmail()));
        }
        tulisBaris(FILE_CUSTOMER, baris);
    }

    // Logika instansiasi class Mekanik.
    public static ArrayList<Mekanik> bacaMekanik() {
        ArrayList<Mekanik> list = new ArrayList<Mekanik>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_MEKANIK)) {
            nomor++;
            try {
                list.add(new Mekanik(str[0], str[1], str[2], str[3], str[4], str[5]));
            } catch (ArrayIndexOutOfBoundsException e) {
                laporSkip("mekanik.txt", nomor, e);
            }
        }
        return list;
    }

    public static void simpanMekanik(ArrayList<Mekanik> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Mekanik m : list) {
            baris.add(gabung(m.getId(), m.getNama(), m.getNoHP(), m.getAlamat(),
                    m.getIdMekanik(), m.getSpesialisasi()));
        }
        tulisBaris(FILE_MEKANIK, baris);
    }

    // Logika mapping Item Layanan berbasis Polymorphism Class (Part & Jasa)
    public static ArrayList<ItemLayanan> bacaItem() {
        ArrayList<ItemLayanan> list = new ArrayList<ItemLayanan>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_ITEM)) {
            nomor++;
            try {
                // Routing instansiasi berdasar marker di depan teks
                if (str[0].equals("Part")) {
                    ItemLayanan item = new Part(str[1], str[2], Double.parseDouble(str[3]), str[4], Integer.parseInt(str[5]));
                    list.add(item);
                } else if (str[0].equals("Jasa")) {
                    ItemLayanan item = new Jasa(str[1], str[2], Double.parseDouble(str[3]), str[4], Integer.parseInt(str[5]));
                    list.add(item);
                } else {
                    System.out.println("[item.txt] baris " + nomor + " dilewati: jenis tidak dikenal " + str[0]);
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                laporSkip("item.txt", nomor, e);
            }
        }
        return list;
    }

    public static void simpanItem(ArrayList<ItemLayanan> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (ItemLayanan item : list) {
            // Karena list bertipe parent ItemLayanan, kita wajib membedah isinya dengan downcasting sebelum disave,
            // untuk mengambil atribut 'stok' milik Part dan 'estimasiWaktu' milik Jasa.
            if (item instanceof Part) {
                Part p = (Part) item; 
                baris.add(gabung("Part", p.getKode(), p.getNama(), p.getHarga(), p.getMerk(), p.getStok()));
            } else if (item instanceof Jasa) {
                Jasa j = (Jasa) item; 
                baris.add(gabung("Jasa", j.getKode(), j.getNama(), j.getHarga(), j.getKategori(), j.getEstimasiWaktu()));
            }
        }
        tulisBaris(FILE_ITEM, baris);
    }

    // Logika paling kompleks: Re-konstruksi relasi antar objek (Rebuilding Foreign Keys).
    // Transaksi harus dioper parameter ArrayList dari data-data Master karena transaksi punya relasi agregasi ke mereka.
    public static ArrayList<Transaksi> bacaTransaksi(ArrayList<Kendaraan> listKendaraan,
                                                     ArrayList<Customer> listCustomer,
                                                     ArrayList<Mekanik> listMekanik,
                                                     ArrayList<ItemLayanan> listItem) {
        ArrayList<Transaksi> list = new ArrayList<Transaksi>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_TRANSAKSI)) {
            nomor++;
            try {
                // Dependency Mapping: Mencari pointer alamat memory Object asli berdasarkan Primary Key dari teks.
                Kendaraan k = cariKendaraan(listKendaraan, str[3]);
                Customer c = cariCustomer(listCustomer, str[4]);
                Mekanik m = cariMekanik(listMekanik, str[5]);
                
                // Guard Clause: Kalau FK patah (misal motor sudah dihapus dari master txt tapi PKB nya masih ada), skip error.
                if (k == null || c == null || m == null) {
                    System.out.println("[transaksi.txt] baris " + nomor + " dilewati: kendaraan/customer/mekanik tidak ditemukan");
                    continue; // Loncat ke baris berikutnya
                }
                
                Transaksi t = new Transaksi(str[0], str[1], Integer.parseInt(str[2]), k, c, m, str[6], str[7]);
                
                // Rekonstruksi keranjang belanja (One-to-many relationship).
                // Logika: Item disave pakai format string split pipa (|). Contoh: P-01|P-02|J-01
                if (str.length > 8 && !str[8].isEmpty()) {
                    for (String kode : str[8].split("\\|")) {
                        // Tarik object rill berdasar kode yang dipisah pipa
                        ItemLayanan item = cariItem(listItem, kode.trim());
                        if (item == null) {
                            System.out.println("[transaksi.txt] baris " + nomor + ": item " + kode + " tidak ditemukan, dilewati");
                        } else {
                            t.addItem(item); // Kembalikan ke dalam keranjang
                        }
                    }
                }
                list.add(t); // Data transaksi valid, masukkan memori.
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                laporSkip("transaksi.txt", nomor, e);
            }
        }
        return list;
    }

    public static void simpanTransaksi(ArrayList<Transaksi> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Transaksi t : list) {
            StringBuilder kode = new StringBuilder();
            // Mengekstrasi hanya PK kode itemnya saja dari dalam array object keranjang (Flattening Array Object to String).
            for (ItemLayanan item : t.getListItem()) {
                if (kode.length() > 0) {
                    kode.append("|");
                }
                kode.append(item.getKode());
            }
            // Menyimpan hanya foreign key getter-nya (noPolisi, noCustomer, idMekanik), BUKAN keseluruhan dump objectnya.
            baris.add(gabung(t.getNoPKB(), t.getTanggalWaktu(), t.getKmSaatIni(),
                    t.getKendaraan().getNoPolisi(), t.getCustomer().getNoCustomer(),
                    t.getMekanik().getIdMekanik(), t.getSaranPerbaikan(), t.getGaransi(), kode));
        }
        tulisBaris(FILE_TRANSAKSI, baris);
    }

    /* ================= Utility Search Data (Simulasi Query SELECT WHERE) ================= */

    // Linear Search untuk mereturn Object Reference Kendaraan berdasar plat No.
    public static Kendaraan cariKendaraan(ArrayList<Kendaraan> list, String noPolisi) {
        for (Kendaraan k : list) {
            // Penggunaan equalsIgnoreCase mengabaikan salah ketik user huruf besar/kecil.
            if (k.getNoPolisi().equalsIgnoreCase(noPolisi)) {
                return k;
            }
        }
        return null;
    }

    public static Customer cariCustomer(ArrayList<Customer> list, String noCustomer) {
        for (Customer c : list) {
            if (c.getNoCustomer().equalsIgnoreCase(noCustomer)) {
                return c;
            }
        }
        return null;
    }

    public static Mekanik cariMekanik(ArrayList<Mekanik> list, String idMekanik) {
        for (Mekanik m : list) {
            if (m.getIdMekanik().equalsIgnoreCase(idMekanik)) {
                return m;
            }
        }
        return null;
    }

    public static ItemLayanan cariItem(ArrayList<ItemLayanan> list, String kode) {
        for (ItemLayanan item : list) {
            if (item.getKode().equalsIgnoreCase(kode)) {
                return item;
            }
        }
        return null;
    }

    public static Transaksi cariTransaksi(ArrayList<Transaksi> list, String noPKB) {
        for (Transaksi t : list) {
            if (t.getNoPKB().equalsIgnoreCase(noPKB)) {
                return t;
            }
        }
        return null;
    }
}