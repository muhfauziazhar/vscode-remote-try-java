package com.mycompany.app.tugas3;

/** Item menu berupa minuman, dengan atribut tambahan jenisMinuman. */
public class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    @Override
    public String tampilMenu() {
        return String.format("%-22s %-12s %12s  [Minuman %s]", getNama(), "Minuman", rupiah(getHarga()), jenisMinuman);
    }

    @Override
    public String keBaris() {
        return "MINUMAN;" + getNama() + ";" + (long) getHarga() + ";" + jenisMinuman;
    }
}
