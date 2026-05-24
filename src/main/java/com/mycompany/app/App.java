/*----------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for license information.
 *---------------------------------------------------------------------------------------*/

package com.mycompany.app;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class App {
    public static void main(String[] args) {
        demoStackLifo();
        demoQueueFifo();
        demoQuickSort();
    }

    private static void demoStackLifo() {
        System.out.println("=== 1) STACK - LIFO (Last In First Out) ===");

        int[] dataStack = {12, 7, 25, 4, 18, 30, 9, 21};
        Deque<Integer> stack = new ArrayDeque<>();

        System.out.println("Proses push:");
        for (int value : dataStack) {
            stack.push(value);
            System.out.println("push(" + value + ") -> " + stack);
        }

        System.out.println("Isi stack sebelum pop: " + stack);
        int pop1 = stack.pop();
        System.out.println("pop() = " + pop1 + " -> " + stack);
        int pop2 = stack.pop();
        System.out.println("pop() = " + pop2 + " -> " + stack);
        System.out.println("Isi stack sesudah pop: " + stack);

        System.out.println("Penjelasan LIFO:");
        System.out.println("Elemen yang terakhir masuk ke stack akan keluar lebih dulu saat pop.");
        System.out.println("Contoh di atas: setelah push 8 data, elemen teratas dipop lebih dahulu.");

        System.out.println("Kompleksitas waktu operasi stack:");
        System.out.println("push: O(1), pop: O(1), peek: O(1)");
        System.out.println();
    }

    private static void demoQueueFifo() {
        System.out.println("=== 2) QUEUE - FIFO (First In First Out) ===");

        int[] dataQueue = {15, 3, 27, 8, 19, 40, 6, 11};
        Deque<Integer> queue = new ArrayDeque<>();

        System.out.println("Proses enqueue:");
        for (int value : dataQueue) {
            queue.offer(value);
            System.out.println("enqueue(" + value + ") -> " + queue);
        }

        System.out.println("Isi queue sebelum dequeue: " + queue);
        int out1 = queue.poll();
        System.out.println("dequeue() = " + out1 + " -> " + queue);
        int out2 = queue.poll();
        System.out.println("dequeue() = " + out2 + " -> " + queue);
        System.out.println("Isi queue sesudah dequeue: " + queue);

        System.out.println("Penjelasan FIFO:");
        System.out.println("Elemen yang pertama masuk ke queue akan keluar lebih dulu saat dequeue.");
        System.out.println("Contoh di atas: data awal 15 keluar lebih dulu, lalu 3.");

        System.out.println("Kompleksitas waktu operasi queue:");
        System.out.println("enqueue: O(1), dequeue: O(1), front/peek: O(1)");
        System.out.println();
    }

    private static void demoQuickSort() {
        System.out.println("=== 3) QUICK SORT ===");

        int[] data = {45, 12, 78, 3, 29, 54, 8, 33};
        System.out.println("Data sebelum diurutkan: " + Arrays.toString(data));

        quickSort(data, 0, data.length - 1);

        System.out.println("Data sesudah diurutkan: " + Arrays.toString(data));
        System.out.println("Penjelasan singkat Quick Sort:");
        System.out.println("Quick Sort memilih satu pivot, lalu membagi data menjadi dua bagian:");
        System.out.println("nilai yang lebih kecil dari pivot di kiri, lebih besar di kanan, lalu rekursif.");
        System.out.println("Kompleksitas waktu: rata-rata O(n log n), terburuk O(n^2).");
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }
}
