# 🍩 Milky Donut - Aplikasi Menghias Donat Imut

Aplikasi **Milky Donut** berbasis Android untuk menghias donat sesuka hati: pilih rasa glasir, tambahkan topping, lihat donatnya berubah langsung, dan harga dihitung otomatis. Setiap donat yang jadi akan menambah skor. Dikembangkan menggunakan Kotlin dan XML (Material Components) di Android Studio.

## 📱 Fitur & Layar Aplikasi

1. **Layar Utama (MainActivity)**: Menampilkan judul aplikasi, papan **Skor** dan **Donat jadi**, serta 6 kartu rasa donat berwajah imut (Stroberi, Tiramisu, Cokelat, Matcha, Taro, Blueberry). Ketuk kartu untuk mulai menghias, atau tekan tombol **Acak rasa** untuk memilih rasa secara acak.
2. **Layar Menghias Donat (RakitActivity)**: Berisi pratinjau donat berukuran besar, pilihan rasa berbentuk lingkaran, dan 12 pilihan topping dengan ikon emoji (Meses, Choco chips, Keju parut, Crumble, Oreo, Almond, Kelapa parut, Marshmallow, Karamel, Wafer, Selai stroberi, Boba mini). Donat ikut berubah dan "bergoyang" setiap kali rasa atau topping dipilih.
3. **Harga & Pesanan**: Total harga otomatis berubah di bagian bawah layar. Tombol **Tambah ke pesanan** menambah skor dan jumlah donat jadi, lalu kembali ke layar utama.

## 💰 Aturan Harga dan Skor

| Hal | Nilai |
|---|---|
| Harga dasar donat | Rp 8.000 |
| Harga tiap topping | Rp 1.500 |
| Contoh: donat + 5 topping | Rp 15.500 |
| Skor per donat jadi | +10 poin |
| Bonus skor per topping | +5 poin |

## 🛠️ Komponen UI yang Digunakan

- **ViewGroup**: ScrollView, LinearLayout, GridLayout, FrameLayout, HorizontalScrollView
- **View Widgets**: TextView, Button
- **Material Components**: MaterialCardView, MaterialButton
- **Custom View**: `DonutView` menggambar donat langsung dengan Canvas, Paint, dan Path (lubang donat memakai fill type EVEN_ODD), lengkap dengan wajah imut dan topping yang posisinya acak tetapi konsisten
- **Navigasi**: Activity dan Intent (mengirim rasa terpilih lewat `putExtra`)
- **Data**: data class `Rasa`, daftar rasa dan topping, serta object `Skor` (disimpan di memori)
- **Animasi**: efek goyang pada donat dengan ViewPropertyAnimator
- **Resource Management**: strings.xml, layout XML, dan tampilan mode terang dipaksa aktif

## 🗂️ Struktur File Utama

| File | Fungsi |
|---|---|
| `MainActivity.kt` | Layar utama: kartu rasa, tombol acak, skor |
| `RakitActivity.kt` | Layar menghias donat: rasa, topping, harga, pesanan |
| `DonutView.kt` | Custom view penggambar donat, wajah, dan topping |
| `Data.kt` | Data rasa, daftar topping, dan skor |
| `activity_main.xml` | Tampilan layar utama |
| `activity_rakit.xml` | Tampilan layar menghias donat |
| `strings.xml` | Nama aplikasi |
| `AndroidManifest.xml` | Pendaftaran Activity |

## 🚀 Cara Menjalankan Aplikasi

1. Buka folder project di **Android Studio**, lalu tunggu Gradle sync selesai.
2. Pilih perangkat di menu atas: emulator, atau HP asli (aktifkan **USB Debugging** lalu sambungkan lewat kabel USB).
3. Klik tombol ▶ **Run**.

Atau lewat terminal di folder project (Windows):

```
gradlew.bat installDebug
```

Setelah muncul tulisan `BUILD SUCCESSFUL`, buka aplikasi **Milky Donut** di HP, atau jalankan:

```
adb shell am start -n com.example.milkydonut/.MainActivity
```

## 📸 Tangkapan Layar (Screenshot)

| Layar Utama | Menghias Donat |
|---|---|
| 

Layar Utama🍩

<img width="738" height="1600" alt="Layar Utama" src="https://github.com/user-attachments/assets/d8d1776f-f48c-4239-aeee-21765620d993" />

 | 

Menghias Donat🍩

<img width="497" height="1280" alt="Menghias Donat" src="https://github.com/user-attachments/assets/81c8f8bf-296a-468c-bf0c-3b2a665e0024" />
 |

## 🔮 Rencana Pengembangan

- Misi harian dengan hadiah poin
- Skor dan donat jadi tersimpan permanen (SharedPreferences)
- Daftar pesanan dan riwayat donat yang pernah dibuat
