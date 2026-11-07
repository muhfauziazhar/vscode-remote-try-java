package com.mycompany.app.tugas3;

/** Dilempar ketika pengguna mengakses item menu yang tidak ada. */
public class ItemTidakDitemukanException extends Exception {
    public ItemTidakDitemukanException(String pesan) {
        super(pesan);
    }
}
