package com.mycompany.app.tugas3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/** Mengelola seluruh item menu restoran dalam sebuah ArrayList. */
public class Menu {
    private final ArrayList<MenuItem> daftarItem = new ArrayList<>();

    public void tambah(MenuItem item) {
        daftarItem.add(item);
    }

    public int jumlahItem() {
        return daftarItem.size();
    }

    /** Mengambil item berdasarkan nomor urut tampilan (mulai dari 1). */
    public MenuItem ambil(int nomor) throws ItemTidakDitemukanException {
        if (nomor < 1 || nomor > daftarItem.size()) {
            throw new ItemTidakDitemukanException("Item nomor " + nomor + " tidak ada di menu.");
        }
        return daftarItem.get(nomor - 1);
    }

    /** Daftar diskon yang sedang tersedia di menu. */
    public ArrayList<Diskon> daftarDiskon() {
        ArrayList<Diskon> hasil = new ArrayList<>();
        for (MenuItem item : daftarItem) {
            if (item instanceof Diskon) {
                hasil.add((Diskon) item);
            }
        }
        return hasil;
    }

    public void tampil() {
        if (daftarItem.isEmpty()) {
            System.out.println("Menu masih kosong.");
            return;
        }
        System.out.println("=============== MENU RESTORAN ===============");
        for (int i = 0; i < daftarItem.size(); i++) {
            System.out.println((i + 1) + ". " + daftarItem.get(i).tampilMenu());
        }
        System.out.println("=============================================");
    }

    /** Menyimpan seluruh menu ke file teks, satu item per baris. */
    public void simpanKeFile(Path lokasi) throws IOException {
        if (lokasi.getParent() != null) {
            Files.createDirectories(lokasi.getParent());
        }
        try (BufferedWriter penulis = Files.newBufferedWriter(lokasi, StandardCharsets.UTF_8)) {
            for (MenuItem item : daftarItem) {
                penulis.write(item.keBaris());
                penulis.newLine();
            }
        }
    }

    /** Memuat menu dari file teks. Baris yang formatnya salah dilewati dan dilaporkan. */
    public int muatDariFile(Path lokasi) throws IOException {
        ArrayList<MenuItem> hasil = new ArrayList<>();
        try (BufferedReader pembaca = Files.newBufferedReader(lokasi, StandardCharsets.UTF_8)) {
            String baris;
            int nomorBaris = 0;
            while ((baris = pembaca.readLine()) != null) {
                nomorBaris++;
                if (baris.trim().isEmpty()) {
                    continue;
                }
                try {
                    hasil.add(dariBaris(baris));
                } catch (IllegalArgumentException e) {
                    System.out.println("Baris " + nomorBaris + " dilewati: " + e.getMessage());
                }
            }
        }
        daftarItem.clear();
        daftarItem.addAll(hasil);
        return hasil.size();
    }

    private static MenuItem dariBaris(String baris) {
        String[] bagian = baris.split(";");
        if (bagian.length != 4) {
            throw new IllegalArgumentException("format harus JENIS;nama;nilai;keterangan");
        }
        String jenis = bagian[0].trim().toUpperCase();
        try {
            switch (jenis) {
                case "MAKANAN":
                    return new Makanan(bagian[1], Double.parseDouble(bagian[2]), bagian[3].trim());
                case "MINUMAN":
                    return new Minuman(bagian[1], Double.parseDouble(bagian[2]), bagian[3].trim());
                case "DISKON":
                    return new Diskon(bagian[1], Double.parseDouble(bagian[2]), bagian[3].trim());
                default:
                    throw new IllegalArgumentException("jenis \"" + bagian[0] + "\" tidak dikenal");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("nilai angka tidak valid");
        }
    }
}
