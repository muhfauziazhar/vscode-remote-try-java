package com.mycompany.app.tugas3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/** Mencatat pesanan seorang pelanggan: item yang dipesan dan jumlahnya. */
public class Pesanan {
    private static final double PAJAK_PERSEN = 10;

    private final int nomorPesanan;
    private final ArrayList<MenuItem> itemDipesan = new ArrayList<>();
    private final ArrayList<Integer> jumlahItem = new ArrayList<>();

    public Pesanan(int nomorPesanan) {
        this.nomorPesanan = nomorPesanan;
    }

    public int getNomorPesanan() {
        return nomorPesanan;
    }

    public boolean kosong() {
        return itemDipesan.isEmpty();
    }

    public void tambahItem(MenuItem item, int jumlah) {
        if (item instanceof Diskon) {
            throw new IllegalArgumentException("Diskon tidak dapat dipesan, diterapkan otomatis saat penghitungan.");
        }
        if (jumlah < 1) {
            throw new IllegalArgumentException("Jumlah pesanan minimal 1.");
        }
        int indeks = itemDipesan.indexOf(item);
        if (indeks >= 0) {
            jumlahItem.set(indeks, jumlahItem.get(indeks) + jumlah);
        } else {
            itemDipesan.add(item);
            jumlahItem.add(jumlah);
        }
    }

    public double hitungSubtotal() {
        double total = 0;
        for (int i = 0; i < itemDipesan.size(); i++) {
            total += itemDipesan.get(i).getHarga() * jumlahItem.get(i);
        }
        return total;
    }

    /** Persen diskon terbesar yang berlaku untuk sebuah item (0 jika tidak ada). */
    private double diskonTerbaik(MenuItem item, ArrayList<Diskon> daftarDiskon) {
        double terbaik = 0;
        for (Diskon d : daftarDiskon) {
            if (d.berlakuUntuk(item) && d.getDiskon() > terbaik) {
                terbaik = d.getDiskon();
            }
        }
        return terbaik;
    }

    public double hitungPotongan(ArrayList<Diskon> daftarDiskon) {
        double potongan = 0;
        for (int i = 0; i < itemDipesan.size(); i++) {
            MenuItem item = itemDipesan.get(i);
            potongan += item.getHarga() * jumlahItem.get(i) * diskonTerbaik(item, daftarDiskon) / 100;
        }
        return potongan;
    }

    public double hitungTotal(ArrayList<Diskon> daftarDiskon) {
        double setelahDiskon = hitungSubtotal() - hitungPotongan(daftarDiskon);
        return setelahDiskon + setelahDiskon * PAJAK_PERSEN / 100;
    }

    /** Menyusun teks struk lengkap. */
    public String buatStruk(ArrayList<Diskon> daftarDiskon) {
        StringBuilder sb = new StringBuilder();
        String garis = "==========================================\n";
        sb.append(garis);
        sb.append(String.format("        STRUK PESANAN NO. %03d%n", nomorPesanan));
        sb.append(garis);
        for (int i = 0; i < itemDipesan.size(); i++) {
            MenuItem item = itemDipesan.get(i);
            int jumlah = jumlahItem.get(i);
            sb.append(String.format("%-20s %2d x %10s%n", item.getNama(), jumlah, rupiahPublik(item.getHarga())));
            double persen = diskonTerbaik(item, daftarDiskon);
            if (persen > 0) {
                sb.append(String.format("   diskon %.0f%% %24s%n", persen,
                        "-" + rupiahPublik(item.getHarga() * jumlah * persen / 100)));
            }
        }
        double subtotal = hitungSubtotal();
        double potongan = hitungPotongan(daftarDiskon);
        double setelah = subtotal - potongan;
        double pajak = setelah * PAJAK_PERSEN / 100;
        sb.append("------------------------------------------\n");
        sb.append(String.format("%-24s %16s%n", "Subtotal", rupiahPublik(subtotal)));
        sb.append(String.format("%-24s %16s%n", "Total diskon", "-" + rupiahPublik(potongan)));
        sb.append(String.format("%-24s %16s%n", "Pajak 10%", rupiahPublik(pajak)));
        sb.append(String.format("%-24s %16s%n", "TOTAL BAYAR", rupiahPublik(setelah + pajak)));
        sb.append(garis);
        sb.append("        Terima kasih atas kunjungan Anda\n");
        return sb.toString();
    }

    public static String rupiahPublik(double nilai) {
        return "Rp " + String.format("%,.0f", nilai).replace(',', '.');
    }

    /** Menyimpan struk ke file teks. */
    public void simpanStruk(Path lokasi, ArrayList<Diskon> daftarDiskon) throws IOException {
        if (lokasi.getParent() != null) {
            Files.createDirectories(lokasi.getParent());
        }
        try (BufferedWriter penulis = Files.newBufferedWriter(lokasi, StandardCharsets.UTF_8)) {
            penulis.write(buatStruk(daftarDiskon));
        }
    }

    /** Membaca struk dari file teks dan mengembalikannya sebagai teks. */
    public static String muatStruk(Path lokasi) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader pembaca = Files.newBufferedReader(lokasi, StandardCharsets.UTF_8)) {
            String baris;
            while ((baris = pembaca.readLine()) != null) {
                sb.append(baris).append('\n');
            }
        }
        return sb.toString();
    }
}
