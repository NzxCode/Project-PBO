package com.bengkel;

import java.util.Scanner;

// Konsep: Utility Class / Helper (Static context)
// Class ini menyatukan semua logic input yang berpotensi error agar kita tidak perlu 
// membuat try-catch berulang-ulang di berbagai tempat, mendukung prinsip DRY (Don't Repeat Yourself).
public class Input {
    
    // Objek Scanner (I/O Java) dibuat static final (konstan). 
    // Mengapa? Karena Scanner menempel pada System.in (Keyboard OS), cukup buka satu stream saja 
    // agar efisien memory dan tidak terjadi resource leak gara-gara 'new Scanner()' terus-terusan.
    private static final Scanner inputUser = new Scanner(System.in);

    // Method pembaca string anti-null (memaksa field required terisi).
    public static String bacaString(String pesan) {
        String hasil;
        // Konsep: Control Flow - Guard Looping
        // Do-while loop mengurung flow aplikasi hingga kondisi kepuasan logika tercapai.
        do {
            System.out.print(pesan);
            hasil = inputUser.nextLine().trim(); // Memotong spasi di kiri-kanan input user.
            if (hasil.isEmpty()) {
                System.out.println("Input tidak boleh kosong, ulangi lagi!");
            }
        } while (hasil.isEmpty()); 
        
        return hasil; // Flow keluar, me-return string bersih.
    }

    // Wrapper sederhana jika sistem memperbolehkan pass field kosongan.
    public static String bacaStringKosong(String pesan) {
        System.out.print(pesan);
        return inputUser.nextLine().trim();
    }
    
    // Method pembaca Integer terproteksi penuh dari tipe data sampah (string/huruf).
    public static int bacaInt(String pesan, int min, int max) {
        int hasil = 0;
        boolean isValid;
        do {
            isValid = true;
            System.out.print(pesan);
            String cek = inputUser.nextLine().trim();
            
            // Konsep: Exception Trapping
            try {
                // Parsing krusial: Jika user mengetik "A", "Spasi", atau "!?", 
                // program aslinya akan force close melempar NumberFormatException. 
                hasil = Integer.parseInt(cek);
                
                // Bisnis Logic guard (batas angka minimal - maksimal).
                if (hasil < min || hasil > max) {
                    System.out.println("Nilai harus antara " + min + " dan " + max + ", ulangi lagi!");
                    isValid = false; // Membatalkan validitas agar ter-looping ulang.
                }
            } catch (NumberFormatException e) {
                // Dengan adanya block catch, JVM tidak jadi crash. 
                // Kita menangkap error tersebut dan memberikan feedback halus ke UI terminal.
                System.out.println("Salah input, ulangi lagi!");
                isValid = false;
            }
        } while (!isValid);
        return hasil;
    }

    // Eksekusi logic serupa untuk tipe data bilangan desimal dan pecahan mata uang.
    public static double bacaDouble(String pesan, double min, double max) {
        double hasil = 0;
        boolean isValid;
        do {
            isValid = true;
            System.out.print(pesan);
            String cek = inputUser.nextLine().trim();
            try {
                hasil = Double.parseDouble(cek); // Konversi primitif double
                
                // Mencegah eksploit injeksi NaN (Not a Number) dan Infinite (Infinity loop value)
                // ke dalam double dari Java Math Core.
                if (Double.isNaN(hasil) || Double.isInfinite(hasil) || hasil < min || hasil > max) {
                    System.out.println("Nilai harus antara " + min + " dan " + max + ", ulangi lagi!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Salah input, ulangi lagi!");
                isValid = false;
            }
        } while (!isValid);
        return hasil;
    }
}