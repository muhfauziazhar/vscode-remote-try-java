/*----------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for license information.
 *---------------------------------------------------------------------------------------*/

package com.mycompany.app;

import java.util.LinkedList;

public class App {
    public static void main(String[] args) {
        // Soal 1: Buatlah sebuah deklarasi variabel dengan tipe data float yang bernama 'StrukturBaris'.
        float StrukturBaris = 0.0f;

        // Soal 2: Buatlah sebuah deklarasi variabel dengan tipe data string yang bernama 'SusunanKataBaru' yang berisi kata 'Pemrograman Struktur Data Java'.
        String SusunanKataBaru = "Pemrograman Struktur Data Java";

        // Soal 3: Pendeklarasian array satu dimensi dengan nama 'DelapanAngka', tipe data integer, yang berisi angka (10, 12, 18, 23, 33, 58, 67, 82).
        int[] DelapanAngka = {10, 12, 18, 23, 33, 58, 67, 82};

        // Soal 4: Buatlah pendeklarasian array dua dimensi dengan nama 'DuaAngka', tipe data String, yang terdiri dari tiga baris dan tiga kolom, isi baris dan kolom berisi angka berikut (2, 4, 6, 8, 10, 12, 14, 16, 18).
        String[][] DuaAngka = {
            {"2", "4", "6"},
            {"8", "10", "12"},
            {"14", "16", "18"}
        };

        // Soal 5: Buatlah deklarasi linked list dengan nama 'UrutanListAngka' yang memiliki list (15, 28, 33, 47, 59).
        LinkedList<Integer> UrutanListAngka = new LinkedList<>();
        UrutanListAngka.add(15);
        UrutanListAngka.add(28);
        UrutanListAngka.add(33);
        UrutanListAngka.add(47);
        UrutanListAngka.add(59);

        System.out.println("StrukturBaris = " + StrukturBaris);
        System.out.println("SusunanKataBaru = " + SusunanKataBaru);
        System.out.print("DelapanAngka = ");
        for (int angka : DelapanAngka) {
            System.out.print(angka + " ");
        }
        System.out.println();

        System.out.println("DuaAngka = ");
        for (String[] baris : DuaAngka) {
            for (String angka : baris) {
                System.out.print(angka + " ");
            }
            System.out.println();
        }

        System.out.println("UrutanListAngka = " + UrutanListAngka);
    }
}
