# Program Gaji Karyawan

## Deskripsi Tugas
Program sederhana untuk menghitung gaji karyawan berdasarkan:
- Golongan karyawan (A, B, C)
- Jam lembur dengan persentase yang berbeda

## Spesifikasi Gaji Pokok:
- Golongan A: Rp 5.000.000
- Golongan B: Rp 6.500.000  
- Golongan C: Rp 9.500.000

## Perhitungan Gaji Lembur:
- 1 jam = 30% dari gaji pokok
- 2 jam = 32% dari gaji pokok
- 3 jam = 34% dari gaji pokok
- 4 jam = 36% dari gaji pokok
- ≥5 jam = 38% dari gaji pokok

## Cara Menjalankan

### 1. Menggunakan JAR file (Recommended)
```bash
java -jar target/Tugas-2_Muhammad-Fauzi-Azhar.jar
```

### 2. Menggunakan Maven
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.mycompany.app.GajiKaryawan"
```

### 3. Compile manual
```bash
javac -d target/classes src/main/java/com/mycompany/app/GajiKaryawan.java
java -cp target/classes com.mycompany.app.GajiKaryawan
```

## Build Project
```bash
mvn clean package
```

## Contoh Penggunaan
Input:
```
Masukkan Golongan Karyawan (A/B/C): A
Masukkan Jam Lembur: 3
```

Output:
```
=== HASIL PERHITUNGAN GAJI ===
Golongan: A
Gaji Pokok: Rp 5,000,000
Jam Lembur: 3 jam
Gaji Lembur: Rp 5,100,000
Jumlah Penghasilan: Rp 10,100,000
```

## File yang Disubmit
- `src/main/java/com/mycompany/app/GajiKaryawan.java` - Source code
- `target/Tugas-2_Muhammad-Fauzi-Azhar.jar` - Executable JAR
- `pom.xml` - Maven configuration
- `README-TUGAS.md` - Dokumentasi ini
---

# Tugas Praktik 1 (STSI4201): Aplikasi Restoran

Kelas: `Menu.java` (data item menu) dan `Main.java` (program utama), tanpa struktur pengulangan.

```bash
javac -d target/classes src/main/java/com/mycompany/app/Menu.java src/main/java/com/mycompany/app/Main.java
java -cp target/classes com.mycompany.app.Main
```

Format pesanan: `Nama Menu = Jumlah` (maksimal 4 menu), contoh `Nasi Padang = 2`.
