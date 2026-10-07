// File: DataFile.java
package com.bengkel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Class utilitas untuk menangani penyimpanan dan pembacaan data dari file teks
public class DataFile {
    // Mendefinisikan path folder utama untuk penyimpanan data
    private static final String FOLDER = "data";
    // Mendefinisikan path file untuk masing-masing entitas
    public static final String FILE_KENDARAAN = FOLDER + "/kendaraan.txt";
    public static final String FILE_CUSTOMER  = FOLDER + "/customer.txt";
    public static final String FILE_MEKANIK   = FOLDER + "/mekanik.txt";
    public static final String FILE_ITEM      = FOLDER + "/item.txt";
    public static final String FILE_TRANSAKSI = FOLDER + "/transaksi.txt";

    // Method private untuk membaca isi file per baris dan membaginya dengan koma
    private static ArrayList<String[]> bacaBaris(String path) {
        // Menginisialisasi ArrayList untuk menampung hasil pemisahan string
        ArrayList<String[]> hasil = new ArrayList<String[]>();
        // Membuat objek File dari path yang diberikan
        File myObj = new File(path);
        // Jika file tidak ada, return list kosong
        if (!myObj.exists()) {
            return hasil; 
        }
        try {
            // Menggunakan Scanner untuk membaca file
            Scanner myReader = new Scanner(myObj);
            // Looping selama masih ada baris di dalam file
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                // Mengabaikan baris yang kosong
                if (data.trim().isEmpty()) {
                    continue;
                }
                // Memecah string berdasarkan karakter koma
                String[] str = data.split(",", -1);
                // Membersihkan spasi berlebih pada setiap elemen array
                for (int i = 0; i < str.length; i++) {
                    str[i] = str[i].trim();
                }
                // Menambahkan array string ke dalam list hasil
                hasil.add(str);
            }
            // Menutup Scanner setelah selesai
            myReader.close();
        } catch (FileNotFoundException e) {
            // Menangkap exception jika file tidak bisa diakses
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // Me-return data yang sudah diparsing
        return hasil;
    }

    // Method private untuk menulis sekumpulan baris string ke dalam file
    private static void tulisBaris(String path, ArrayList<String> baris) {
        try {
            // Mengecek apakah folder sudah ada atau belum
            File folder = new File(FOLDER);
            if (!folder.exists()) {
                // Membuat folder jika belum ada
                folder.mkdirs();
            }
            // Menginisialisasi FileWriter untuk menulis ke file
            FileWriter fw = new FileWriter(path); 
            // Looping untuk menulis setiap elemen list ke dalam file
            for (String b : baris) {
                fw.write(b);
                fw.write(System.lineSeparator());
            }
            // Menutup FileWriter setelah selesai
            fw.close();
        } catch (IOException e) {
            // Menangkap exception jika proses tulis gagal
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Method private untuk menggabungkan banyak objek menjadi satu string dipisah koma
    private static String gabung(Object... kolom) {
        // Menggunakan StringBuilder untuk efisiensi penggabungan string
        StringBuilder sb = new StringBuilder();
        // Looping sepanjang argumen yang diberikan
        for (int i = 0; i < kolom.length; i++) {
            // Menambahkan koma sebelum elemen kedua dan seterusnya
            if (i > 0) {
                sb.append(",");
            }
            // Mengubah tipe objek menjadi string dan membersihkan karakter koma/enter bawaan
            sb.append(String.valueOf(kolom[i]).replace(",", ";").replace("\r", " ").replace("\n", " "));
        }
        // Me-return hasil akhir gabungan string
        return sb.toString();
    }

    // Method private untuk mencetak log jika ada baris error yang dilewati (skipped)
    private static void laporSkip(String namaFile, int nomor, Exception e) {
        System.out.println("[" + namaFile + "] baris " + nomor + " dilewati: " + e.getMessage());
    }

    // Method untuk membaca dan memparsing file kendaraan.txt menjadi objek Kendaraan
    public static ArrayList<Kendaraan> bacaKendaraan() {
        ArrayList<Kendaraan> list = new ArrayList<Kendaraan>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_KENDARAAN)) {
            nomor++;
            try {
                // Mengecek apakah data tersebut adalah objek Motor
                if (str[0].equals("Motor")) {
                    // Membuat instance Motor dari data string yang dibaca
                    Kendaraan k = new Motor(str[1], str[2], str[3],
                            Integer.parseInt(str[4]), str[5],
                            Integer.parseInt(str[6]), str[7], str[8], str[9]);
                    list.add(k);
                } else {
                    // Log jika jenis kendaraan tidak dikenali
                    System.out.println("[kendaraan.txt] baris " + nomor + " dilewati: jenis tidak dikenal " + str[0]);
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                // Menangkap exception akibat salah format data atau kurang kolom
                laporSkip("kendaraan.txt", nomor, e);
            }
        }
        // Me-return list objek Kendaraan
        return list;
    }

    // Method untuk menyimpan list objek Kendaraan ke dalam file kendaraan.txt
    public static void simpanKendaraan(ArrayList<Kendaraan> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Kendaraan k : list) {
            // Mengecek instance objek secara dinamis
            if (k instanceof Motor) {
                // Melakukan casting dari Kendaraan ke Motor
                Motor m = (Motor) k; 
                // Menggabungkan atribut motor menjadi satu baris string
                baris.add(gabung("Motor", m.getNoPolisi(), m.getMerk(), m.getTipe(), m.getTahun(),
                        m.getWarna(), m.getKm(), m.getNoRangka(), m.getNoMesin(), m.getJenisMotor()));
            }
        }
        // Menjalankan operasi tulis ke file
        tulisBaris(FILE_KENDARAAN, baris);
    }

    // Method untuk membaca dan memparsing file customer.txt menjadi objek Customer
    public static ArrayList<Customer> bacaCustomer() {
        ArrayList<Customer> list = new ArrayList<Customer>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_CUSTOMER)) {
            nomor++;
            try {
                // Membuat instance Customer dari elemen array string
                list.add(new Customer(str[0], str[1], str[2], str[3], str[4], str[5]));
            } catch (ArrayIndexOutOfBoundsException e) {
                // Menangkap exception jika kolom di file teks kurang
                laporSkip("customer.txt", nomor, e);
            }
        }
        return list;
    }

    // Method untuk menyimpan list objek Customer ke dalam file teks
    public static void simpanCustomer(ArrayList<Customer> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Customer c : list) {
            // Menggabungkan seluruh nilai getter dari Customer
            baris.add(gabung(c.getId(), c.getNama(), c.getNoHP(), c.getAlamat(),
                    c.getNoCustomer(), c.getEmail()));
        }
        tulisBaris(FILE_CUSTOMER, baris);
    }

    // Method untuk membaca file mekanik.txt menjadi list objek Mekanik
    public static ArrayList<Mekanik> bacaMekanik() {
        ArrayList<Mekanik> list = new ArrayList<Mekanik>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_MEKANIK)) {
            nomor++;
            try {
                // Membuat dan menambahkan objek Mekanik ke list
                list.add(new Mekanik(str[0], str[1], str[2], str[3], str[4], str[5]));
            } catch (ArrayIndexOutOfBoundsException e) {
                // Skip baris ini jika terjadi error pembacaan indeks
                laporSkip("mekanik.txt", nomor, e);
            }
        }
        return list;
    }

    // Method untuk menulis data objek Mekanik ke dalam file teks
    public static void simpanMekanik(ArrayList<Mekanik> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Mekanik m : list) {
            // Memanggil method gabung untuk setiap objek mekanik
            baris.add(gabung(m.getId(), m.getNama(), m.getNoHP(), m.getAlamat(),
                    m.getIdMekanik(), m.getSpesialisasi()));
        }
        tulisBaris(FILE_MEKANIK, baris);
    }

    // Method untuk membaca file item.txt menjadi list objek ItemLayanan
    public static ArrayList<ItemLayanan> bacaItem() {
        ArrayList<ItemLayanan> list = new ArrayList<ItemLayanan>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_ITEM)) {
            nomor++;
            try {
                // Mengecek tipe item secara spesifik (Part atau Jasa)
                if (str[0].equals("Part")) {
                    // Inisialisasi child class Part
                    ItemLayanan item = new Part(str[1], str[2], Double.parseDouble(str[3]),
                            str[4], Integer.parseInt(str[5]));
                    list.add(item);
                } else if (str[0].equals("Jasa")) {
                    // Inisialisasi child class Jasa
                    ItemLayanan item = new Jasa(str[1], str[2], Double.parseDouble(str[3]),
                            str[4], Integer.parseInt(str[5]));
                    list.add(item);
                } else {
                    // Log jika jenis item tidak valid
                    System.out.println("[item.txt] baris " + nomor + " dilewati: jenis tidak dikenal " + str[0]);
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                // Tangkap error format angka atau kekurangan kolom
                laporSkip("item.txt", nomor, e);
            }
        }
        return list;
    }

    // Method untuk men-serialize list objek ItemLayanan ke teks
    public static void simpanItem(ArrayList<ItemLayanan> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (ItemLayanan item : list) {
            // Evaluasi instance dari objek menggunakan instanceof
            if (item instanceof Part) {
                // Lakukan downcasting menjadi Part
                Part p = (Part) item; 
                baris.add(gabung("Part", p.getKode(), p.getNama(), p.getHarga(), p.getMerk(), p.getStok()));
            } else if (item instanceof Jasa) {
                // Lakukan downcasting menjadi Jasa
                Jasa j = (Jasa) item; 
                baris.add(gabung("Jasa", j.getKode(), j.getNama(), j.getHarga(),
                        j.getKategori(), j.getEstimasiWaktu()));
            }
        }
        tulisBaris(FILE_ITEM, baris);
    }

    // Method membaca file transaksi dengan melakukan mapping relasi antar objek
    public static ArrayList<Transaksi> bacaTransaksi(ArrayList<Kendaraan> listKendaraan,
                                                     ArrayList<Customer> listCustomer,
                                                     ArrayList<Mekanik> listMekanik,
                                                     ArrayList<ItemLayanan> listItem) {
        ArrayList<Transaksi> list = new ArrayList<Transaksi>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_TRANSAKSI)) {
            nomor++;
            try {
                // Memanggil method pencarian untuk mendapatkan referensi objek asli
                Kendaraan k = cariKendaraan(listKendaraan, str[3]);
                Customer c = cariCustomer(listCustomer, str[4]);
                Mekanik m = cariMekanik(listMekanik, str[5]);
                // Jika ada referensi relasi yang hilang, skip transaksi ini
                if (k == null || c == null || m == null) {
                    System.out.println("[transaksi.txt] baris " + nomor + " dilewati: kendaraan/customer/mekanik tidak ditemukan");
                    continue;
                }
                // Membuat objek transaksi baru
                Transaksi t = new Transaksi(str[0], str[1], Integer.parseInt(str[2]), k, c, m, str[6], str[7]);
                // Parsing relasi item di kolom ke-9 yang dipisah dengan karakter "|"
                if (str.length > 8 && !str[8].isEmpty()) {
                    for (String kode : str[8].split("\\|")) {
                        // Mencari objek ItemLayanan berdasarkan kodenya
                        ItemLayanan item = cariItem(listItem, kode.trim());
                        if (item == null) {
                            System.out.println("[transaksi.txt] baris " + nomor + ": item " + kode + " tidak ditemukan, dilewati");
                        } else {
                            // Menambahkan item ke dalam keranjang transaksi
                            t.addItem(item);
                        }
                    }
                }
                list.add(t);
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                laporSkip("transaksi.txt", nomor, e);
            }
        }
        return list;
    }

    // Method untuk menulis daftar transaksi beserta relasinya ke dalam file
    public static void simpanTransaksi(ArrayList<Transaksi> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Transaksi t : list) {
            StringBuilder kode = new StringBuilder();
            // Menggabungkan seluruh kode item dalam transaksi dengan pemisah "|"
            for (ItemLayanan item : t.getListItem()) {
                if (kode.length() > 0) {
                    kode.append("|");
                }
                kode.append(item.getKode());
            }
            // Menggabungkan foreign key dari objek berelasi (noPolisi, noCustomer, idMekanik)
            baris.add(gabung(t.getNoPKB(), t.getTanggalWaktu(), t.getKmSaatIni(),
                    t.getKendaraan().getNoPolisi(), t.getCustomer().getNoCustomer(),
                    t.getMekanik().getIdMekanik(), t.getSaranPerbaikan(), t.getGaransi(), kode));
        }
        tulisBaris(FILE_TRANSAKSI, baris);
    }

    // Pencarian Kendaraan berdasarkan nomor polisi (me-return null jika tidak ada)
    public static Kendaraan cariKendaraan(ArrayList<Kendaraan> list, String noPolisi) {
        for (Kendaraan k : list) {
            if (k.getNoPolisi().equalsIgnoreCase(noPolisi)) {
                return k;
            }
        }
        return null;
    }

    // Pencarian Customer berdasarkan noCustomer
    public static Customer cariCustomer(ArrayList<Customer> list, String noCustomer) {
        for (Customer c : list) {
            if (c.getNoCustomer().equalsIgnoreCase(noCustomer)) {
                return c;
            }
        }
        return null;
    }

    // Pencarian Mekanik berdasarkan idMekanik
    public static Mekanik cariMekanik(ArrayList<Mekanik> list, String idMekanik) {
        for (Mekanik m : list) {
            if (m.getIdMekanik().equalsIgnoreCase(idMekanik)) {
                return m;
            }
        }
        return null;
    }

    // Pencarian ItemLayanan berdasarkan kode item
    public static ItemLayanan cariItem(ArrayList<ItemLayanan> list, String kode) {
        for (ItemLayanan item : list) {
            if (item.getKode().equalsIgnoreCase(kode)) {
                return item;
            }
        }
        return null;
    }

    // Pencarian Transaksi berdasarkan noPKB
    public static Transaksi cariTransaksi(ArrayList<Transaksi> list, String noPKB) {
        for (Transaksi t : list) {
            if (t.getNoPKB().equalsIgnoreCase(noPKB)) {
                return t;
            }
        }
        return null;
    }
}