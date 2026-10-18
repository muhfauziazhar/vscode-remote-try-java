package com.mycompany.app;

import java.util.Scanner;

/**
 * Aplikasi restoran sederhana: tampil menu, terima pesanan (maksimal 4 menu),
 * hitung total biaya (pajak, biaya pelayanan, diskon, promo), dan cetak struk.
 * Sesuai petunjuk tugas, program ini tidak memakai struktur pengulangan.
 */
public class Main {

    static final int MAKS_MENU = 4;
    static final int PAJAK_PERSEN = 10;
    static final int BIAYA_PELAYANAN = 20000;
    static final int BATAS_DISKON = 100000;
    static final int DISKON_PERSEN = 10;
    static final int BATAS_PROMO = 50000;

    // Daftar menu disimpan dalam array: indeks 0-3 makanan, 4-7 minuman
    static final Menu[] daftarMenu = {
        new Menu("Nasi Padang", 25000, "Makanan"),
        new Menu("Ayam Bakar", 28000, "Makanan"),
        new Menu("Soto Ayam", 20000, "Makanan"),
        new Menu("Mie Goreng", 18000, "Makanan"),
        new Menu("Es Teh", 6000, "Minuman"),
        new Menu("Es Jeruk", 10000, "Minuman"),
        new Menu("Kopi Susu", 15000, "Minuman"),
        new Menu("Jus Alpukat", 18000, "Minuman")
    };

    // Pesanan pelanggan: menu dan jumlahnya disimpan pada array paralel
    static final Menu[] pesananMenu = new Menu[MAKS_MENU];
    static final int[] pesananJumlah = new int[MAKS_MENU];
    static int jumlahItem = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== RESTORAN SEDERHANA ===");
        tampilkanMenu();

        System.out.print("\nBerapa jenis menu yang ingin dipesan (1-" + MAKS_MENU + ")? ");
        int jumlahJenis = bacaAngka(scanner.nextLine());
        if (jumlahJenis < 1 || jumlahJenis > MAKS_MENU) {
            System.out.println("Jumlah jenis menu harus antara 1 dan " + MAKS_MENU + ". Program selesai.");
            scanner.close();
            return;
        }

        System.out.println("Format pesanan: Nama Menu = Jumlah (contoh: Nasi Padang = 2)");
        terimaPesanan(scanner, 1);
        if (jumlahJenis >= 2) {
            terimaPesanan(scanner, 2);
        }
        if (jumlahJenis >= 3) {
            terimaPesanan(scanner, 3);
        }
        if (jumlahJenis >= 4) {
            terimaPesanan(scanner, 4);
        }

        if (jumlahItem == 0) {
            System.out.println("\nTidak ada pesanan yang valid. Program selesai.");
        } else {
            cetakStruk();
        }
        scanner.close();
    }

    // ---------- Tampilan menu (dikelompokkan per kategori) ----------

    static void tampilkanMenu() {
        System.out.println("\n--- MAKANAN ---");
        cetakBarisMenu(0);
        cetakBarisMenu(1);
        cetakBarisMenu(2);
        cetakBarisMenu(3);
        System.out.println("\n--- MINUMAN ---");
        cetakBarisMenu(4);
        cetakBarisMenu(5);
        cetakBarisMenu(6);
        cetakBarisMenu(7);
    }

    static void cetakBarisMenu(int indeks) {
        Menu menu = daftarMenu[indeks];
        System.out.println(String.format("%d. %-14s %s", indeks + 1, menu.getNama(), rupiah(menu.getHarga())));
    }

    // ---------- Pemesanan ----------

    static void terimaPesanan(Scanner scanner, int urutan) {
        System.out.print("Pesanan ke-" + urutan + ": ");
        String baris = scanner.nextLine().trim();

        if (!baris.contains("=")) {
            System.out.println("  Format salah (gunakan: Nama Menu = Jumlah). Pesanan dilewati.");
            return;
        }

        String[] bagian = baris.split("=");
        String namaMenu = bagian[0].trim();
        int jumlah = bagian.length > 1 ? bacaAngka(bagian[1].trim()) : 0;

        int indeks = cariIndeksMenu(namaMenu);
        if (indeks == -1) {
            System.out.println("  Menu \"" + namaMenu + "\" tidak ada di daftar. Pesanan dilewati.");
        } else if (jumlah < 1) {
            System.out.println("  Jumlah harus berupa angka minimal 1. Pesanan dilewati.");
        } else {
            pesananMenu[jumlahItem] = daftarMenu[indeks];
            pesananJumlah[jumlahItem] = jumlah;
            jumlahItem++;
        }
    }

    // Mencari indeks menu berdasarkan nama (tidak membedakan huruf besar/kecil)
    static int cariIndeksMenu(String nama) {
        int indeks;
        switch (nama.toLowerCase()) {
            case "nasi padang":
                indeks = 0;
                break;
            case "ayam bakar":
                indeks = 1;
                break;
            case "soto ayam":
                indeks = 2;
                break;
            case "mie goreng":
                indeks = 3;
                break;
            case "es teh":
                indeks = 4;
                break;
            case "es jeruk":
                indeks = 5;
                break;
            case "kopi susu":
                indeks = 6;
                break;
            case "jus alpukat":
                indeks = 7;
                break;
            default:
                indeks = -1;
        }
        return indeks;
    }

    // Mengubah teks menjadi angka; hasil 0 jika bukan angka
    static int bacaAngka(String teks) {
        int hasil;
        try {
            hasil = Integer.parseInt(teks.trim());
        } catch (NumberFormatException e) {
            hasil = 0;
        }
        return hasil;
    }

    // ---------- Perhitungan ----------

    // Total harga satu baris pesanan (0 jika baris belum terisi)
    static int totalBaris(int indeks) {
        int total = 0;
        if (indeks < jumlahItem) {
            total = pesananMenu[indeks].getHarga() * pesananJumlah[indeks];
        }
        return total;
    }

    static int hitungSubtotal() {
        return totalBaris(0) + totalBaris(1) + totalBaris(2) + totalBaris(3);
    }

    // Mengembalikan indeks pesanan minuman pertama, atau -1 jika tidak ada minuman
    static int cariPesananMinuman() {
        int indeks = -1;
        if (jumlahItem > 0 && pesananMenu[0].isMinuman()) {
            indeks = 0;
        } else if (jumlahItem > 1 && pesananMenu[1].isMinuman()) {
            indeks = 1;
        } else if (jumlahItem > 2 && pesananMenu[2].isMinuman()) {
            indeks = 2;
        } else if (jumlahItem > 3 && pesananMenu[3].isMinuman()) {
            indeks = 3;
        }
        return indeks;
    }

    // ---------- Struk ----------

    static void cetakStruk() {
        int subtotal = hitungSubtotal();
        int pajak = subtotal * PAJAK_PERSEN / 100;

        int diskon = 0;
        if (subtotal > BATAS_DISKON) {
            diskon = subtotal * DISKON_PERSEN / 100;
        }

        int indeksPromo = cariPesananMinuman();

        int totalBayar = subtotal + pajak + BIAYA_PELAYANAN - diskon;

        System.out.println("\n==================================================");
        System.out.println("                 STRUK PEMESANAN");
        System.out.println("==================================================");
        System.out.println(String.format("%-14s %6s %12s %14s", "Item", "Jumlah", "Harga", "Total"));
        System.out.println("--------------------------------------------------");
        cetakBarisPesanan(0);
        cetakBarisPesanan(1);
        cetakBarisPesanan(2);
        cetakBarisPesanan(3);
        System.out.println("--------------------------------------------------");
        cetakBaris("Total biaya pesanan", rupiah(subtotal));
        cetakBaris("Pajak " + PAJAK_PERSEN + "%", rupiah(pajak));
        cetakBaris("Biaya pelayanan", rupiah(BIAYA_PELAYANAN));

        if (diskon > 0) {
            cetakBaris("Diskon " + DISKON_PERSEN + "% (total > " + rupiah(BATAS_DISKON) + ")", "-" + rupiah(diskon));
        }
        if (subtotal > BATAS_PROMO) {
            if (indeksPromo != -1) {
                Menu menuPromo = pesananMenu[indeksPromo];
                int jumlahGratis = pesananJumlah[indeksPromo];
                cetakBaris("Promo Beli 1 Gratis 1 (> " + rupiah(BATAS_PROMO) + ")", "");
                cetakBaris("  Gratis: " + menuPromo.getNama() + " x" + jumlahGratis,
                        rupiah(0) + " (senilai " + rupiah(menuPromo.getHarga() * jumlahGratis) + ")");
            } else {
                cetakBaris("Promo Beli 1 Gratis 1", "tidak berlaku (tanpa minuman)");
            }
        }

        System.out.println("--------------------------------------------------");
        if (diskon > 0) {
            cetakBaris("Harga sebelum diskon", rupiah(subtotal + pajak + BIAYA_PELAYANAN));
            cetakBaris("Harga setelah diskon", rupiah(totalBayar));
        }
        cetakBaris("TOTAL BAYAR", rupiah(totalBayar));
        System.out.println("==================================================");
        System.out.println("            Terima kasih atas kunjungan Anda");
    }

    static void cetakBarisPesanan(int indeks) {
        if (indeks < jumlahItem) {
            Menu menu = pesananMenu[indeks];
            System.out.println(String.format("%-14s %6d %12s %14s", menu.getNama(), pesananJumlah[indeks],
                    rupiah(menu.getHarga()), rupiah(totalBaris(indeks))));
        }
    }

    static void cetakBaris(String label, String nilai) {
        System.out.println(String.format("%-34s %15s", label, nilai));
    }

    static String rupiah(int nilai) {
        return "Rp " + String.format("%,d", nilai).replace(',', '.');
    }
}
