package com.bengkel;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    // Helper untuk input teks biasa
    public static String bacaString(String pesan) {
        System.out.print(pesan);
        return scanner.nextLine();
    }

    // Helper untuk input angka bulat (Integer) dengan Exception Handling
    public static int bacaInt(String pesan) {
        int hasil = 0;
        boolean valid = false;
        do {
            System.out.print(pesan);
            try {
                hasil = Integer.parseInt(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Input harus berupa angka bulat! Silakan ulangi.");
            }
        } while (!valid);
        return hasil;
    }

    // Helper untuk input angka desimal (Double) dengan Exception Handling
    public static double bacaDouble(String pesan) {
        double hasil = 0.0;
        boolean valid = false;
        do {
            System.out.print(pesan);
            try {
                hasil = Double.parseDouble(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Input harus berupa angka valid (gunakan titik untuk desimal)! Silakan ulangi.");
            }
        } while (!valid);
        return hasil;
    }
}