package com.mycompany.app.tugas3;

/**
 * Item menu khusus untuk diskon. Diskon berupa persen dan berlaku pada satu kategori
 * (Makanan, Minuman) atau pada semua kategori ("Semua"). Harga diskon sendiri adalah 0.
 */
public class Diskon extends MenuItem {
    private double diskon;
    private String kategoriTarget;

    public Diskon(String nama, double diskon, String kategoriTarget) {
        super(nama, 0, "Diskon");
        setDiskon(diskon);
        this.kategoriTarget = kategoriTarget;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        if (diskon <= 0 || diskon > 100) {
            throw new IllegalArgumentException("Besar diskon harus lebih dari 0 dan paling banyak 100 persen.");
        }
        this.diskon = diskon;
    }

    public String getKategoriTarget() {
        return kategoriTarget;
    }

    /** Apakah diskon ini berlaku untuk item tertentu. */
    public boolean berlakuUntuk(MenuItem item) {
        if (item instanceof Diskon) {
            return false;
        }
        return kategoriTarget.equalsIgnoreCase("Semua") || kategoriTarget.equalsIgnoreCase(item.getKategori());
    }

    @Override
    public String tampilMenu() {
        return String.format("%-22s %-12s %12s  [berlaku untuk: %s]", getNama(), "Diskon",
                String.format("%.0f%%", diskon), kategoriTarget);
    }

    @Override
    public String keBaris() {
        return "DISKON;" + getNama() + ";" + diskon + ";" + kategoriTarget;
    }
}
