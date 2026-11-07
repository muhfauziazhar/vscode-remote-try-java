package com.mycompany.app.tugas3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Program manajemen restoran (Tugas Praktik 3).
 * Menu utama: tambah item, tampilkan menu, terima pesanan, hitung total, struk, simpan/muat file.
 */
public class Main {
    private static final Path FILE_MENU = Paths.get("data", "menu.txt");

    private static final Scanner pemindai = new Scanner(System.in);
    private static final Menu menu = new Menu();
    private static Pesanan pesananAktif = new Pesanan(1);
    private static int nomorBerikutnya = 2;

    public static void main(String[] args) {
        System.out.println("=== MANAJEMEN RESTORAN ===");
        muatMenuAwal();

        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenuUtama();
            String pilihan = baca("Pilih menu: ");
            if (pilihan == null) {
                break;
            }
            switch (pilihan) {
                case "1":
                    tambahItemMenu();
                    break;
                case "2":
                    menu.tampil();
                    break;
                case "3":
                    terimaPesanan();
                    break;
                case "4":
                    hitungTotal();
                    break;
                case "5":
                    tampilkanStruk();
                    break;
                case "6":
                    simpanMenu();
                    break;
                case "7":
                    muatMenu();
                    break;
                case "8":
                    muatStrukDariFile();
                    break;
                case "0":
                    berjalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak dikenal, silakan coba lagi.");
            }
        }
        System.out.println("Terima kasih, program selesai.");
    }

    private static void tampilkanMenuUtama() {
        System.out.println();
        System.out.println("---------- MENU UTAMA ----------");
        System.out.println("1. Tambah item menu");
        System.out.println("2. Tampilkan menu");
        System.out.println("3. Terima pesanan");
        System.out.println("4. Hitung total biaya");
        System.out.println("5. Tampilkan dan simpan struk");
        System.out.println("6. Simpan menu ke file");
        System.out.println("7. Muat menu dari file");
        System.out.println("8. Muat struk dari file");
        System.out.println("0. Keluar");
    }

    /** Membaca satu baris; mengembalikan null jika masukan sudah habis. */
    private static String baca(String petunjuk) {
        System.out.print(petunjuk);
        if (!pemindai.hasNextLine()) {
            return null;
        }
        return pemindai.nextLine().trim();
    }

    /** Membaca bilangan bulat; mengulang sampai valid. Mengembalikan null jika masukan habis. */
    private static Integer bacaBulat(String petunjuk) {
        while (true) {
            String teks = baca(petunjuk);
            if (teks == null) {
                return null;
            }
            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("Masukan harus berupa bilangan bulat.");
            }
        }
    }

    private static Double bacaDesimal(String petunjuk) {
        while (true) {
            String teks = baca(petunjuk);
            if (teks == null) {
                return null;
            }
            try {
                return Double.parseDouble(teks);
            } catch (NumberFormatException e) {
                System.out.println("Masukan harus berupa angka.");
            }
        }
    }

    private static void muatMenuAwal() {
        try {
            int jumlah = menu.muatDariFile(FILE_MENU);
            System.out.println("Menu dimuat dari " + FILE_MENU + " (" + jumlah + " item).");
        } catch (NoSuchFileException e) {
            System.out.println("File menu belum ada, memakai menu bawaan.");
            menu.tambah(new Makanan("Nasi Goreng", 25000, "Berat"));
            menu.tambah(new Makanan("Mie Ayam", 20000, "Berat"));
            menu.tambah(new Makanan("Roti Bakar", 15000, "Ringan"));
            menu.tambah(new Minuman("Es Teh", 6000, "Dingin"));
            menu.tambah(new Minuman("Kopi Panas", 12000, "Panas"));
            menu.tambah(new Diskon("Diskon Makanan 10%", 10, "Makanan"));
            menu.tambah(new Diskon("Promo Semua 5%", 5, "Semua"));
        } catch (IOException e) {
            System.out.println("Gagal membaca file menu: " + e.getMessage());
        }
    }

    private static void tambahItemMenu() {
        System.out.println("Jenis item: 1. Makanan  2. Minuman  3. Diskon");
        Integer jenis = bacaBulat("Pilih jenis: ");
        if (jenis == null) {
            return;
        }
        if (jenis < 1 || jenis > 3) {
            System.out.println("Jenis tidak valid.");
            return;
        }
        String nama = baca("Nama item: ");
        if (nama == null) {
            return;
        }
        try {
            if (jenis == 3) {
                Double persen = bacaDesimal("Besar diskon (persen): ");
                String target = baca("Berlaku untuk (Makanan/Minuman/Semua): ");
                if (persen == null || target == null) {
                    return;
                }
                menu.tambah(new Diskon(nama, persen, target));
            } else {
                Double harga = bacaDesimal("Harga: ");
                String ket = baca(jenis == 1 ? "Jenis makanan (mis. Berat/Ringan): " : "Jenis minuman (mis. Dingin/Panas): ");
                if (harga == null || ket == null) {
                    return;
                }
                menu.tambah(jenis == 1 ? new Makanan(nama, harga, ket) : new Minuman(nama, harga, ket));
            }
            System.out.println("Item \"" + nama + "\" berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambah item: " + e.getMessage());
        }
    }

    private static void terimaPesanan() {
        menu.tampil();
        System.out.println("Pesanan No. " + pesananAktif.getNomorPesanan() + " (ketik 0 untuk selesai memesan)");
        while (true) {
            Integer nomor = bacaBulat("Nomor menu: ");
            if (nomor == null || nomor == 0) {
                break;
            }
            try {
                MenuItem item = menu.ambil(nomor);
                Integer jumlah = bacaBulat("Jumlah " + item.getNama() + ": ");
                if (jumlah == null) {
                    break;
                }
                pesananAktif.tambahItem(item, jumlah);
                System.out.println("Dicatat: " + jumlah + " x " + item.getNama());
            } catch (ItemTidakDitemukanException e) {
                System.out.println("Kesalahan: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Kesalahan: " + e.getMessage());
            }
        }
    }

    private static void hitungTotal() {
        if (pesananAktif.kosong()) {
            System.out.println("Belum ada pesanan yang dicatat.");
            return;
        }
        System.out.println("Subtotal           : " + Pesanan.rupiahPublik(pesananAktif.hitungSubtotal()));
        System.out.println("Diskon             : " + Pesanan.rupiahPublik(pesananAktif.hitungPotongan(menu.daftarDiskon())));
        System.out.println("Total (+pajak 10%) : " + Pesanan.rupiahPublik(pesananAktif.hitungTotal(menu.daftarDiskon())));
    }

    private static void tampilkanStruk() {
        if (pesananAktif.kosong()) {
            System.out.println("Belum ada pesanan yang dicatat.");
            return;
        }
        System.out.print(pesananAktif.buatStruk(menu.daftarDiskon()));
        Path lokasi = Paths.get("data", String.format("struk_%03d.txt", pesananAktif.getNomorPesanan()));
        try {
            pesananAktif.simpanStruk(lokasi, menu.daftarDiskon());
            System.out.println("Struk disimpan ke " + lokasi);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk: " + e.getMessage());
        }
        pesananAktif = new Pesanan(nomorBerikutnya++);
    }

    private static void simpanMenu() {
        try {
            menu.simpanKeFile(FILE_MENU);
            System.out.println("Menu disimpan ke " + FILE_MENU + " (" + menu.jumlahItem() + " item).");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }
    }

    private static void muatMenu() {
        try {
            int jumlah = menu.muatDariFile(FILE_MENU);
            System.out.println("Menu dimuat dari " + FILE_MENU + " (" + jumlah + " item).");
        } catch (NoSuchFileException e) {
            System.out.println("File " + FILE_MENU + " belum ada. Simpan menu terlebih dahulu.");
        } catch (IOException e) {
            System.out.println("Gagal membaca file menu: " + e.getMessage());
        }
    }

    private static void muatStrukDariFile() {
        Integer nomor = bacaBulat("Nomor struk yang dimuat: ");
        if (nomor == null) {
            return;
        }
        Path lokasi = Paths.get("data", String.format("struk_%03d.txt", nomor));
        try {
            System.out.print(Pesanan.muatStruk(lokasi));
        } catch (NoSuchFileException e) {
            System.out.println("Struk nomor " + nomor + " tidak ditemukan.");
        } catch (IOException e) {
            System.out.println("Gagal membaca struk: " + e.getMessage());
        }
    }
}
