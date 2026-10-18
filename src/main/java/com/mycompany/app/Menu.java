package com.mycompany.app;

/**
 * Merepresentasikan satu item menu restoran (makanan atau minuman).
 */
public class Menu {
    private final String nama;
    private final int harga;
    private final String kategori;

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public boolean isMinuman() {
        return kategori.equals("Minuman");
    }
}
