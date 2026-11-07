package com.mycompany.app.tugas3;

/** Item menu berupa makanan, dengan atribut tambahan jenisMakanan. */
public class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    @Override
    public String tampilMenu() {
        return String.format("%-22s %-12s %12s  [Makanan %s]", getNama(), "Makanan", rupiah(getHarga()), jenisMakanan);
    }

    @Override
    public String keBaris() {
        return "MAKANAN;" + getNama() + ";" + (long) getHarga() + ";" + jenisMakanan;
    }
}
