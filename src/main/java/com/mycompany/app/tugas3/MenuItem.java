package com.mycompany.app.tugas3;

/**
 * Kelas abstrak dasar untuk semua item pada menu restoran.
 * Atribut dibuat private (encapsulation) dan diakses lewat getter/setter.
 */
public abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori;

    protected MenuItem(String nama, double harga, String kategori) {
        setNama(nama);
        setHarga(harga);
        this.kategori = kategori;
    }

    /** Menampilkan informasi item; diimplementasikan berbeda oleh tiap subkelas (polymorphism). */
    public abstract String tampilMenu();

    /** Mengubah item menjadi satu baris teks agar dapat disimpan ke file. */
    public abstract String keBaris();

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama item tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif.");
        }
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    /** Format rupiah, misalnya 25000 menjadi "Rp 25.000". */
    protected static String rupiah(double nilai) {
        return "Rp " + String.format("%,.0f", nilai).replace(',', '.');
    }
}
