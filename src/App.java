import com.bengkel.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("=== APLIKASI KASIR BENGKEL UNTAR ===");

        Customer cust1 = new Customer("C01", "Budi Santoso", "0811223344", "Jakarta", "C-001", "budi@untarmaild.com");
        Mekanik mek1 = new Mekanik("M01", "Slamet Riyadi", "0899887766", "Bekasi", "MEK-01", "Spesialis CVT");
        
        int kmMasuk = 0;
        int tahunMotor = 0;
        boolean isValid;

        do {
            isValid = true;
            System.out.print("Masukkan Tahun Registrasi Motor (Angka): ");
            try {
                tahunMotor = Integer.parseInt(input.nextLine());
                if (tahunMotor < 1980 || tahunMotor > 2026) {
                    System.out.println("⚠️ Alert: Input tahun tidak rasional. Harap ulangi.");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Exception: Wajib memasukkan angka bulat.");
                isValid = false;
            }
        } while (!isValid);

        do {
            isValid = true;
            System.out.print("Masukkan Odometer / KM Saat Ini (Angka): ");
            try {
                kmMasuk = Integer.parseInt(input.nextLine());
                if(kmMasuk < 0) {
                    System.out.println("⚠️ Alert: Kilometer tidak boleh minus.");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Exception: Wajib memasukkan angka bulat tanpa titik/koma.");
                isValid = false;
            }
        } while (!isValid);

        Motor motorCust = new Motor("B 9999 XYZ", "Honda", "Vario 150", tahunMotor, "Matte Black", kmMasuk, "MH12345678", "KF123456", "Matic");
        Transaksi trx = new Transaksi("PKB-2026-1007", "07-Okt-2026 10:15 WIB", kmMasuk, motorCust, cust1, mek1);
        
        trx.setSaranPerbaikan("Periksa tekanan angin ban belakang dan ganti kampas ganda bulan depan.");
        trx.setGaransi("3 Hari Garansi Pemasangan Part");

        Part p1 = new Part("PRT-01", "V-Belt Honda", 155000, "Honda Genuine", 20);
        Part p2 = new Part("PRT-02", "Oli Gardan", 17000, "AHM", 50);
        Jasa j1 = new Jasa("JSA-01", "Bongkar CVT", 65000, "Servis Berat", 45);

        p1.kurangStok(1);
        p2.kurangStok(2); 

        trx.tambahItem(p1);
        trx.tambahItem(p2);
        trx.tambahItem(j1);

        trx.struk();
        
        input.close();
    }
}