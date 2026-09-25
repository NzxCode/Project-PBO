package com.bengkel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFile {
    private static final String FOLDER = "data";
    public static final String FILE_KENDARAAN = FOLDER + "/kendaraan.txt";
    public static final String FILE_CUSTOMER  = FOLDER + "/customer.txt";
    public static final String FILE_MEKANIK   = FOLDER + "/mekanik.txt";
    public static final String FILE_ITEM      = FOLDER + "/item.txt";
    public static final String FILE_TRANSAKSI = FOLDER + "/transaksi.txt";
    public static final String FILE_DETAIL    = FOLDER + "/detail.txt";

    private static ArrayList<String[]> bacaBaris(String path) {
        ArrayList<String[]> hasil = new ArrayList<String[]>();
        File myObj = new File(path);
        if (!myObj.exists()) {
            return hasil; 
        }
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.trim().isEmpty()) {
                    continue;
                }
                String[] str = data.split(",", -1);
                for (int i = 0; i < str.length; i++) {
                    str[i] = str[i].trim();
                }
                hasil.add(str);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return hasil;
    }

    private static void tulisBaris(String path, ArrayList<String> baris) {
        try {
            File folder = new File(FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileWriter fw = new FileWriter(path); 
            for (String b : baris) {
                fw.write(b);
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String gabung(Object... kolom) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < kolom.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(String.valueOf(kolom[i]).replace(",", ";").replace("\r", " ").replace("\n", " "));
        }
        return sb.toString();
    }

    private static void laporSkip(String namaFile, int nomor, Exception e) {
        System.out.println("[" + namaFile + "] baris " + nomor + " dilewati: " + e.getMessage());
    }

    public static ArrayList<Kendaraan> bacaKendaraan() {
        ArrayList<Kendaraan> list = new ArrayList<Kendaraan>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_KENDARAAN)) {
            nomor++;
            try {
                if (str[0].equals("Motor")) {
                    Kendaraan k = new Motor(str[1], str[2], str[3],
                            Integer.parseInt(str[4]), str[5],
                            Integer.parseInt(str[6]), str[7], str[8], str[9]);
                    list.add(k);
                } else {
                    System.out.println("[kendaraan.txt] baris " + nomor + " dilewati: jenis tidak dikenal " + str[0]);
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                laporSkip("kendaraan.txt", nomor, e);
            }
        }
        return list;
    }

    public static void simpanKendaraan(ArrayList<Kendaraan> list) {
        ArrayList<String> baris = new ArrayList<String>();
        for (Kendaraan k : list) {
            if (k instanceof Motor) {
                Motor m = (Motor) k; 
                baris.add(gabung("Motor", m.getNoPolisi(), m.getMerk(), m.getTipe(), m.getTahun(),
                        m.getWarna(), m.getKm(), m.getNoRangka(), m.getNoMesin(), m.getJenisMotor()));
            }
        }
        tulisBaris(FILE_KENDARAAN, baris);
    }


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

    public static ArrayList<ItemLayanan> bacaItem() {
        ArrayList<ItemLayanan> list = new ArrayList<ItemLayanan>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_ITEM)) {
            nomor++;
            try {
                if (str[0].equals("Part")) {
                    ItemLayanan item = new Part(str[1], str[2], Double.parseDouble(str[3]),
                            str[4], Integer.parseInt(str[5]));
                    list.add(item);
                } else if (str[0].equals("Jasa")) {
                    ItemLayanan item = new Jasa(str[1], str[2], Double.parseDouble(str[3]),
                            str[4], Integer.parseInt(str[5]));
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
            if (item instanceof Part) {
                Part p = (Part) item; 
                baris.add(gabung("Part", p.getKode(), p.getNama(), p.getHarga(), p.getMerk(), p.getStok()));
            } else if (item instanceof Jasa) {
                Jasa j = (Jasa) item; 
                baris.add(gabung("Jasa", j.getKode(), j.getNama(), j.getHarga(),
                        j.getKategori(), j.getEstimasiWaktu()));
            }
        }
        tulisBaris(FILE_ITEM, baris);
    }
    public static ArrayList<Transaksi> bacaTransaksi(ArrayList<Kendaraan> listKendaraan,
                                                     ArrayList<Customer> listCustomer,
                                                     ArrayList<Mekanik> listMekanik,
                                                     ArrayList<ItemLayanan> listItem) {
        ArrayList<Transaksi> list = new ArrayList<Transaksi>();
        int nomor = 0;
        for (String[] str : bacaBaris(FILE_TRANSAKSI)) {
            nomor++;
            try {
                Kendaraan k = cariKendaraan(listKendaraan, str[3]);
                Customer c = cariCustomer(listCustomer, str[4]);
                Mekanik m = cariMekanik(listMekanik, str[5]);
                if (k == null || c == null || m == null) {
                    System.out.println("[transaksi.txt] baris " + nomor + " dilewati: kendaraan/customer/mekanik tidak ditemukan");
                    continue;
                }
                list.add(new Transaksi(str[0], str[1], Integer.parseInt(str[2]), k, c, m, str[6], str[7]));
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                laporSkip("transaksi.txt", nomor, e);
            }
        }

        nomor = 0;
        for (String[] str : bacaBaris(FILE_DETAIL)) {
            nomor++;
            try {
                Transaksi t = cariTransaksi(list, str[0]);
                ItemLayanan item = cariItem(listItem, str[1]);
                if (t == null || item == null) {
                    System.out.println("[detail.txt] baris " + nomor + " dilewati: transaksi/item tidak ditemukan");
                    continue;
                }
                t.addDetail(new DetailTransaksi(item, Integer.parseInt(str[2]),
                        Double.parseDouble(str[3]), Double.parseDouble(str[4])));
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                laporSkip("detail.txt", nomor, e);
            }
        }
        return list;
    }

    public static void simpanTransaksi(ArrayList<Transaksi> list) {
        ArrayList<String> barisTransaksi = new ArrayList<String>();
        ArrayList<String> barisDetail = new ArrayList<String>();
        for (Transaksi t : list) {
            barisTransaksi.add(gabung(t.getNoPKB(), t.getTanggalWaktu(), t.getKmSaatIni(),
                    t.getKendaraan().getNoPolisi(), t.getCustomer().getNoCustomer(),
                    t.getMekanik().getIdMekanik(), t.getSaranPerbaikan(), t.getGaransi()));
            for (DetailTransaksi d : t.getListDetail()) {
                barisDetail.add(gabung(t.getNoPKB(), d.getItem().getKode(), d.getQty(),
                        d.getHargaSatuan(), d.getDiskonPersen()));
            }
        }
        tulisBaris(FILE_TRANSAKSI, barisTransaksi);
        tulisBaris(FILE_DETAIL, barisDetail);
    }

    /* ================= Pencarian (return null jika tidak ada) ================= */

    public static Kendaraan cariKendaraan(ArrayList<Kendaraan> list, String noPolisi) {
        for (Kendaraan k : list) {
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
