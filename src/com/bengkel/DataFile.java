package com.bengkel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataFile {
    
    // Method untuk membaca isi file teks
    public static void bacaFile(String pathFile) {
        try {
            File file = new File(pathFile);
            Scanner reader = new Scanner(file);
            System.out.println("--- Membaca Data dari: " + pathFile + " ---");
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ File tidak ditemukan: " + pathFile);
        }
    }

    // Method untuk menulis data ke file teks
    public static void tulisFile(String pathFile, String konten, boolean append) {
        try {
            FileWriter writer = new FileWriter(pathFile, append);
            writer.write(konten + System.lineSeparator());
            writer.close();
            System.out.println("✅ Data berhasil disimpan ke " + pathFile);
        } catch (IOException e) {
            System.out.println("⚠️ Terjadi kesalahan saat menulis file.");
            e.printStackTrace();
        }
    }
}