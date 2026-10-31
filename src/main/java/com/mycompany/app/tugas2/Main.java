package com.mycompany.app.tugas2;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Aplikasi restoran dengan dua menu utama: pelanggan (memesan) dan pemilik
 * (mengelola menu). Menu dan pesanan disimpan dalam array, dan input yang
 * tidak valid akan diminta ulang dengan struktur pengulangan.
 */
public class Main {

    static final int PAJAK_PERSEN = 10;
    static final int BIAYA_PELAYANAN = 20000;
    static final int BATAS_DISKON = 100000;
    static final int DISKON_PERSEN = 10;
    static final int BATAS_PROMO = 50000;
    static final String[] KATEGORI = {"Makanan", "Minuman"};

    static final Scanner scanner = new Scanner(System.in);

    // Daftar menu (array) dan jumlah isi yang terpakai
    static Menu[] daftarMenu = new Menu[10];
    static int jumlahMenu = 0;

    // Pesanan pelanggan (array paralel) dan jumlah isi yang terpakai
    static Menu[] pesananMenu = new Menu[10];
    static int[] pesananJumlah = new int[10];
    static int jumlahPesanan = 0;

    public static void main(String[] args) {
        isiMenuAwal();

        int pilihan;
        do {
            System.out.println("\n=== RESTORAN: MENU UTAMA ===");
            System.out.println("1. Pelanggan (memesan)");
            System.out.println("2. Pemilik restoran (kelola menu)");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            pilihan = bacaAngka(baca());

            switch (pilihan) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuPemilik();
                    break;
                case 0:
                    System.out.println("Terima kasih. Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1, 2, atau 0.");
            }
        } while (pilihan != 0);
    }

    static void isiMenuAwal() {
        tambahKeDaftar(new Menu("Nasi Padang", 25000, "Makanan"));
        tambahKeDaftar(new Menu("Ayam Bakar", 28000, "Makanan"));
        tambahKeDaftar(new Menu("Soto Ayam", 20000, "Makanan"));
        tambahKeDaftar(new Menu("Mie Goreng", 18000, "Makanan"));
        tambahKeDaftar(new Menu("Es Teh", 6000, "Minuman"));
        tambahKeDaftar(new Menu("Es Jeruk", 10000, "Minuman"));
        tambahKeDaftar(new Menu("Kopi Susu", 15000, "Minuman"));
        tambahKeDaftar(new Menu("Jus Alpukat", 18000, "Minuman"));
    }

    // ---------- Input ----------

    // Membaca satu baris; jika input sudah habis, program berhenti agar tidak berputar tanpa akhir
    static String baca() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput berakhir. Program selesai.");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    // Mengubah teks menjadi angka; -1 jika bukan angka
    static int bacaAngka(String teks) {
        int hasil;
        try {
            hasil = Integer.parseInt(teks.trim());
        } catch (NumberFormatException e) {
            hasil = -1;
        }
        return hasil;
    }

    // Meminta angka positif sampai input benar
    static int bacaAngkaPositif(String pertanyaan) {
        int angka;
        do {
            System.out.print(pertanyaan);
            angka = bacaAngka(baca());
            if (angka < 1) {
                System.out.println("Input harus berupa angka lebih dari 0.");
            }
        } while (angka < 1);
        return angka;
    }

    // Meminta teks tidak kosong sampai input benar
    static String bacaTeks(String pertanyaan) {
        String teks;
        do {
            System.out.print(pertanyaan);
            teks = baca();
            if (teks.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
            }
        } while (teks.isEmpty());
        return teks;
    }

    // Konfirmasi Ya/Tidak; terus meminta input sampai jawabannya Ya atau Tidak
    static boolean konfirmasi(String pertanyaan) {
        String jawab;
        boolean valid;
        do {
            System.out.print(pertanyaan + " (Ya/Tidak): ");
            jawab = baca();
            valid = jawab.equalsIgnoreCase("ya") || jawab.equalsIgnoreCase("tidak");
            if (!valid) {
                System.out.println("Jawaban tidak valid. Ketik Ya atau Tidak.");
            }
        } while (!valid);
        return jawab.equalsIgnoreCase("ya");
    }

    // Meminta nomor menu yang valid; mengembalikan indeks array, atau -1 jika memilih 0 (kembali)
    static int bacaNomorMenu(String aksi) {
        while (true) {
            System.out.print("Masukkan nomor menu yang akan " + aksi + " (0 = kembali): ");
            int nomor = bacaAngka(baca());
            if (nomor == 0) {
                return -1;
            }
            if (nomor >= 1 && nomor <= jumlahMenu) {
                return nomor - 1;
            }
            System.out.println("Nomor menu tidak ada. Coba lagi.");
        }
    }

    // ---------- Tampilan menu (dikelompokkan per kategori) ----------

    static void tampilkanMenu() {
        System.out.println("\n----- DAFTAR MENU -----");
        for (String kategori : KATEGORI) {
            System.out.println("\n[" + kategori.toUpperCase() + "]");
            for (int i = 0; i < jumlahMenu; i++) {
                if (daftarMenu[i].getKategori().equals(kategori)) {
                    System.out.println(String.format("%2d. %-14s %s", i + 1,
                            daftarMenu[i].getNama(), rupiah(daftarMenu[i].getHarga())));
                }
            }
        }
    }

    // ---------- Menu pelanggan: pemesanan ----------

    static void menuPelanggan() {
        jumlahPesanan = 0;
        while (true) {
            tampilkanMenu();
            System.out.println("\nKetik nomor menu untuk memesan, 'hapus' untuk menghapus pesanan,");
            System.out.println("'selesai' untuk mencetak struk, atau '0' untuk kembali ke menu utama.");
            System.out.print("Input: ");
            String input = baca();

            if (input.equalsIgnoreCase("selesai")) {
                if (jumlahPesanan == 0) {
                    System.out.println("Belum ada pesanan. Pilih menu terlebih dahulu.");
                } else {
                    cetakStruk();
                    return;
                }
            } else if (input.equalsIgnoreCase("hapus")) {
                hapusPesanan();
            } else if (input.equals("0")) {
                System.out.println("Kembali ke menu utama. Pesanan yang belum dicetak dibatalkan.");
                jumlahPesanan = 0;
                return;
            } else {
                int nomor = bacaAngka(input);
                if (nomor < 1 || nomor > jumlahMenu) {
                    System.out.println("Input tidak valid. Pilih nomor menu yang ada, 'hapus', 'selesai', atau 0.");
                } else {
                    tambahPesanan(daftarMenu[nomor - 1]);
                }
            }
        }
    }

    static void tambahPesanan(Menu menu) {
        int jumlah = bacaAngkaPositif("Jumlah \"" + menu.getNama() + "\": ");

        // Jika menu sudah dipesan, jumlahnya ditambahkan
        boolean sudahAda = false;
        for (int i = 0; i < jumlahPesanan; i++) {
            if (pesananMenu[i] == menu) {
                pesananJumlah[i] += jumlah;
                sudahAda = true;
            }
        }
        if (!sudahAda) {
            if (jumlahPesanan == pesananMenu.length) {
                pesananMenu = Arrays.copyOf(pesananMenu, pesananMenu.length * 2);
                pesananJumlah = Arrays.copyOf(pesananJumlah, pesananJumlah.length * 2);
            }
            pesananMenu[jumlahPesanan] = menu;
            pesananJumlah[jumlahPesanan] = jumlah;
            jumlahPesanan++;
        }
        System.out.println("Ditambahkan: " + jumlah + " x " + menu.getNama());
    }

    static void hapusPesanan() {
        if (jumlahPesanan == 0) {
            System.out.println("Belum ada pesanan yang bisa dihapus.");
            return;
        }
        System.out.println("\n--- Pesanan saat ini ---");
        for (int i = 0; i < jumlahPesanan; i++) {
            System.out.println((i + 1) + ". " + pesananJumlah[i] + " x " + pesananMenu[i].getNama());
        }
        int nomor;
        do {
            System.out.print("Nomor pesanan yang dihapus (0 = batal): ");
            nomor = bacaAngka(baca());
            if (nomor < 0 || nomor > jumlahPesanan) {
                System.out.println("Nomor pesanan tidak ada. Coba lagi.");
            }
        } while (nomor < 0 || nomor > jumlahPesanan);

        if (nomor > 0) {
            String nama = pesananMenu[nomor - 1].getNama();
            for (int i = nomor - 1; i < jumlahPesanan - 1; i++) {
                pesananMenu[i] = pesananMenu[i + 1];
                pesananJumlah[i] = pesananJumlah[i + 1];
            }
            jumlahPesanan--;
            System.out.println("Pesanan \"" + nama + "\" dihapus.");
        }
    }

    // ---------- Perhitungan dan struk ----------

    static int hitungSubtotal() {
        int total = 0;
        for (int i = 0; i < jumlahPesanan; i++) {
            total += pesananMenu[i].getHarga() * pesananJumlah[i];
        }
        return total;
    }

    // Indeks pesanan minuman pertama, atau -1 jika tidak ada minuman
    static int cariPesananMinuman() {
        int indeks = -1;
        for (int i = 0; i < jumlahPesanan && indeks == -1; i++) {
            if (pesananMenu[i].isMinuman()) {
                indeks = i;
            }
        }
        return indeks;
    }

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
        for (int i = 0; i < jumlahPesanan; i++) {
            Menu menu = pesananMenu[i];
            System.out.println(String.format("%-14s %6d %12s %14s", menu.getNama(), pesananJumlah[i],
                    rupiah(menu.getHarga()), rupiah(menu.getHarga() * pesananJumlah[i])));
        }
        System.out.println("--------------------------------------------------");
        cetakBaris("Total biaya pesanan", rupiah(subtotal));
        cetakBaris("Pajak " + PAJAK_PERSEN + "%", rupiah(pajak));
        cetakBaris("Biaya pelayanan", rupiah(BIAYA_PELAYANAN));

        if (diskon > 0) {
            cetakBaris("Diskon " + DISKON_PERSEN + "% (> " + rupiah(BATAS_DISKON) + ")", "-" + rupiah(diskon));
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

    static void cetakBaris(String label, String nilai) {
        System.out.println(String.format("%-34s %15s", label, nilai));
    }

    static String rupiah(int nilai) {
        return "Rp " + String.format("%,d", nilai).replace(',', '.');
    }

    // ---------- Menu pemilik: pengelolaan menu ----------

    static void menuPemilik() {
        int pilihan;
        do {
            System.out.println("\n=== PENGELOLAAN MENU (PEMILIK) ===");
            System.out.println("1. Tambah menu baru");
            System.out.println("2. Ubah harga menu");
            System.out.println("3. Hapus menu");
            System.out.println("0. Kembali ke menu utama");
            System.out.print("Pilih: ");
            pilihan = bacaAngka(baca());

            switch (pilihan) {
                case 1:
                    tambahMenuBaru();
                    break;
                case 2:
                    ubahHargaMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 0:
                    System.out.println("Kembali ke menu utama.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1, 2, 3, atau 0.");
            }
        } while (pilihan != 0);
    }

    // Menyisipkan menu pada akhir kelompok kategorinya agar nomor menu tetap berurutan
    static void tambahKeDaftar(Menu menuBaru) {
        if (jumlahMenu == daftarMenu.length) {
            daftarMenu = Arrays.copyOf(daftarMenu, daftarMenu.length * 2);
        }
        int posisi = jumlahMenu;
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].getKategori().equals(menuBaru.getKategori())) {
                posisi = i + 1;
            }
        }
        for (int i = jumlahMenu; i > posisi; i--) {
            daftarMenu[i] = daftarMenu[i - 1];
        }
        daftarMenu[posisi] = menuBaru;
        jumlahMenu++;
    }

    static void tambahMenuBaru() {
        int banyak = bacaAngkaPositif("Berapa menu baru yang akan ditambahkan? ");
        for (int k = 1; k <= banyak; k++) {
            System.out.println("\n--- Menu baru ke-" + k + " dari " + banyak + " ---");
            String nama = bacaTeks("Nama menu: ");

            int pilihanKategori;
            do {
                System.out.print("Kategori (1 = Makanan, 2 = Minuman): ");
                pilihanKategori = bacaAngka(baca());
                if (pilihanKategori != 1 && pilihanKategori != 2) {
                    System.out.println("Kategori tidak valid. Pilih 1 atau 2.");
                }
            } while (pilihanKategori != 1 && pilihanKategori != 2);

            int harga = bacaAngkaPositif("Harga (Rp): ");
            tambahKeDaftar(new Menu(nama, harga, KATEGORI[pilihanKategori - 1]));
            System.out.println("Menu \"" + nama + "\" ditambahkan.");
        }
        tampilkanMenu();
    }

    static void ubahHargaMenu() {
        tampilkanMenu();
        int indeks = bacaNomorMenu("diubah harganya");
        if (indeks == -1) {
            return;
        }
        Menu menu = daftarMenu[indeks];
        int hargaBaru = bacaAngkaPositif("Harga baru untuk \"" + menu.getNama() + "\" (Rp): ");
        boolean yakin = konfirmasi("Ubah harga \"" + menu.getNama() + "\" dari " + rupiah(menu.getHarga())
                + " menjadi " + rupiah(hargaBaru) + "?");
        if (yakin) {
            menu.setHarga(hargaBaru);
            System.out.println("Harga berhasil diubah.");
            tampilkanMenu();
        } else {
            System.out.println("Perubahan dibatalkan. Daftar menu tidak berubah.");
        }
    }

    static void hapusMenu() {
        tampilkanMenu();
        int indeks = bacaNomorMenu("dihapus");
        if (indeks == -1) {
            return;
        }
        Menu menu = daftarMenu[indeks];
        boolean yakin = konfirmasi("Hapus menu \"" + menu.getNama() + "\"?");
        if (yakin) {
            for (int i = indeks; i < jumlahMenu - 1; i++) {
                daftarMenu[i] = daftarMenu[i + 1];
            }
            daftarMenu[jumlahMenu - 1] = null;
            jumlahMenu--;
            System.out.println("Menu berhasil dihapus.");
            tampilkanMenu();
        } else {
            System.out.println("Penghapusan dibatalkan. Daftar menu tidak berubah.");
        }
    }
}
