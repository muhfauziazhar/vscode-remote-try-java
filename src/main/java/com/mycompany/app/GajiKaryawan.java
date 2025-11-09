package com.mycompany.app;
import java.util.Scanner;

public class GajiKaryawan {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input golongan karyawan
        System.out.print("Masukkan Golongan Karyawan (A/B/C): ");
        String golongan = scanner.nextLine().toUpperCase().trim();
        
        // Input jam lembur
        System.out.print("Masukkan Jam Lembur: ");
        int jamLembur = scanner.nextInt();
        
        // Tentukan gaji pokok berdasarkan golongan
        long gajiPokok = 0;
        if (golongan.equals("A")) {
            gajiPokok = 5_000_000;
        } else if (golongan.equals("B")) {
            gajiPokok = 6_500_000;
        } else if (golongan.equals("C")) {
            gajiPokok = 9_500_000;
        } else {
            System.out.println("Golongan tidak valid! Gunakan A, B, atau C.");
            scanner.close();
            return;
        }
        
        // Hitung gaji lembur berdasarkan jam
        double persentaseLembur = 0;
        if (jamLembur == 1) {
            persentaseLembur = 0.30; // 30%
        } else if (jamLembur == 2) {
            persentaseLembur = 0.32; // 32%
        } else if (jamLembur == 3) {
            persentaseLembur = 0.34; // 34%
        } else if (jamLembur == 4) {
            persentaseLembur = 0.36; // 36%
        } else if (jamLembur >= 5) {
            persentaseLembur = 0.38; // 38%
        }
        
        long gajiLembur = (long) (jamLembur * persentaseLembur * gajiPokok);
        long totalPenghasilan = gajiPokok + gajiLembur;
        
        // Tampilkan hasil
        System.out.println("\n=== HASIL PERHITUNGAN GAJI ===");
        System.out.println("Golongan: " + golongan);
        System.out.println("Gaji Pokok: Rp " + String.format("%,d", gajiPokok));
        System.out.println("Jam Lembur: " + jamLembur + " jam");
        System.out.println("Gaji Lembur: Rp " + String.format("%,d", gajiLembur));
        System.out.println("Jumlah Penghasilan: Rp " + String.format("%,d", totalPenghasilan));
        
        scanner.close();
    }
}
