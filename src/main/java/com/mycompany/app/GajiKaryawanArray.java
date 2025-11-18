package com.mycompany.app;
import java.util.Scanner;

public class GajiKaryawanArray {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Array Gaji untuk setiap golongan (A, B, C)
        long[] arrayGaji = {5_000_000, 6_500_000, 9_500_000};
        
        // Array Persentase Lembur (30%, 32%, 34%, 36%, 38%)
        double[] arrayPersenLembur = {0.30, 0.32, 0.34, 0.36, 0.38};
        
        // Input golongan karyawan
        System.out.print("Masukkan Golongan Karyawan (A/B/C): ");
        String golongan = scanner.nextLine().toUpperCase().trim();
        
        // Input jam lembur
        System.out.print("Masukkan Jam Lembur: ");
        int jamLembur = scanner.nextInt();
        
        // Validasi golongan dan tentukan index array
        int indexGaji = -1;
        if (golongan.equals("A")) {
            indexGaji = 0;
        } else if (golongan.equals("B")) {
            indexGaji = 1;
        } else if (golongan.equals("C")) {
            indexGaji = 2;
        } else {
            System.out.println("Golongan tidak valid! Gunakan A, B, atau C.");
            scanner.close();
            return;
        }
        
        // Ambil gaji pokok dari array berdasarkan golongan
        long gajiPokok = arrayGaji[indexGaji];
        
        // Tentukan index persentase lembur berdasarkan jam
        int indexLembur = -1;
        if (jamLembur == 1) {
            indexLembur = 0; // 30%
        } else if (jamLembur == 2) {
            indexLembur = 1; // 32%
        } else if (jamLembur == 3) {
            indexLembur = 2; // 34%
        } else if (jamLembur == 4) {
            indexLembur = 3; // 36%
        } else if (jamLembur >= 5) {
            indexLembur = 4; // 38%
        }
        
        // Hitung gaji lembur menggunakan array persentase
        long gajiLembur = 0;
        if (indexLembur != -1) {
            double persenLembur = arrayPersenLembur[indexLembur];
            gajiLembur = (long) (persenLembur * gajiPokok);
        }
        
        // Hitung total penghasilan
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
