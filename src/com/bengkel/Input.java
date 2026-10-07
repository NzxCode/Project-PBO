// File: Input.java
package com.bengkel;

import java.util.Scanner;

// Class utilitas untuk mengambil input user dari console dengan validasi
public class Input {
    // Objek Scanner yang bersifat static dan konstan untuk dipakai berulang kali
    private static final Scanner inputUser = new Scanner(System.in);

    // Method untuk membaca string yang tidak boleh dikosongkan
    public static String bacaString(String pesan) {
        String hasil;
        // Looping selama input kosong
        do {
            System.out.print(pesan);
            // Menghilangkan spasi ekstra di awal dan akhir input
            hasil = inputUser.nextLine().trim();
            if (hasil.isEmpty()) {
                System.out.println("Input tidak boleh kosong, ulangi lagi!");
            }
        } while (hasil.isEmpty());
        // Me-return hasil input yang valid
        return hasil;
    }

    // Method untuk membaca string yang memperbolehkan input kosong
    public static String bacaStringKosong(String pesan) {
        System.out.print(pesan);
        return inputUser.nextLine().trim();
    }
    
    // Method untuk membaca input integer dengan batasan nilai minimal dan maksimal
    public static int bacaInt(String pesan, int min, int max) {
        int hasil = 0;
        boolean isValid;
        // Looping hingga input lolos dari pengecekan exception dan batas nilai
        do {
            isValid = true;
            System.out.print(pesan);
            String cek = inputUser.nextLine().trim();
            try {
                // Parsing string menjadi integer
                hasil = Integer.parseInt(cek);
                // Validasi rentang nilai minimum dan maksimum
                if (hasil < min || hasil > max) {
                    System.out.println("Nilai harus antara " + min + " dan " + max + ", ulangi lagi!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                // Menangkap exception jika user memasukkan huruf atau format salah
                System.out.println("Salah input, ulangi lagi!");
                isValid = false;
            }
        } while (!isValid);
        // Me-return nilai integer final
        return hasil;
    }

    // Method untuk membaca input double dengan batasan nilai minimal dan maksimal
    public static double bacaDouble(String pesan, double min, double max) {
        double hasil = 0;
        boolean isValid;
        // Looping validasi input untuk tipe pecahan desimal
        do {
            isValid = true;
            System.out.print(pesan);
            String cek = inputUser.nextLine().trim();
            try {
                // Parsing string ke tipe primitif double
                hasil = Double.parseDouble(cek);
                // Memastikan nilai bukan NaN/Infinite dan berada dalam batas yang benar
                if (Double.isNaN(hasil) || Double.isInfinite(hasil) || hasil < min || hasil > max) {
                    System.out.println("Nilai harus antara " + min + " dan " + max + ", ulangi lagi!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                // Penanganan exception ketika format desimal keliru
                System.out.println("Salah input, ulangi lagi!");
                isValid = false;
            }
        } while (!isValid);
        // Me-return nilai double yang sudah bersih
        return hasil;
    }
}