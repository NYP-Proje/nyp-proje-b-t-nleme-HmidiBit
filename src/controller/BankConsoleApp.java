package controller;

import models.Account;
import models.Customer;
import services.BankService;

import java.util.List;
import java.util.Scanner;

public class BankConsoleApp {
    private static final BankService bankService = new BankService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==========================================");
        System.out.println("    BBS: BANK MANAGEMENT SYSTEM HOŞGELDİNİZ  ");
        System.out.println("==========================================");

        while (true) {
            System.out.println("\n--- ANA MENÜ ---");
            System.out.println("1. Yeni Müşteri Kaydet (Müşteri Ekle)");
            System.out.println("2. Tüm Müşterileri Listele");
            System.out.println("3. Yeni Banka Hesabı Aç");
            System.out.println("4. Hesaba Para Yatır");
            System.out.println("5. Hesaptan Para Çek");
            System.out.println("6. Havale / EFT Yap (Para Transferi)");
            System.out.println("7. Müşterinin Hesaplarını Görüntüle");
            System.out.println("0. Güvenli Çıkış");
            System.out.print("Lütfen yapmak istediğiniz işlemi seçiniz: ");

            int secim;
            try {
                secim = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Hata: Lütfen sadece sayısal bir değer giriniz!");
                scanner.nextLine();
                continue;
            }

            try {
                switch (secim) {
                    case 1 -> {
                        System.out.println("\n[YENİ MÜŞTERİ KAYDI]");
                        System.out.print("Adı: "); String ad = scanner.nextLine();
                        System.out.print("Soyadı: "); String soyad = scanner.nextLine();
                        System.out.print("T.C. Kimlik No (11 Haneli): "); String tc = scanner.nextLine();
                        System.out.print("E-posta Adresi: "); String email = scanner.nextLine();

                        bankService.registerCustomer(ad, soyad, tc, email);
                        System.out.println("✓ Müşteri başarıyla veritabanına kaydedildi.");
                    }
                    case 2 -> {
                        System.out.println("\n[SİSTEMDEKİ TÜM MÜŞTERİLER]");
                        List<Customer> musteriler = bankService.getAllCustomers();
                        if (musteriler.isEmpty()) {
                            System.out.println("Sistemde henüz kayıtlı müşteri bulunmamaktadır.");
                        } else {
                            for (Customer c : musteriler) {
                                System.out.printf("ID: %d | %s %s | T.C.: %s | E-posta: %s\n",
                                        c.getId(), c.getFirstName(), c.getLastName(), c.getTcNo(), c.getEmail());
                            }
                        }
                    }
                    case 3 -> {
                        System.out.println("\n[YENİ HESAP AÇILIŞI]");
                        System.out.print("Hesap Numarası oluşturun (Ör: TR1001): "); String hesapNo = scanner.nextLine();
                        System.out.print("İlk Yatırılacak Tutar (Başlangıç Bakiyesi): "); double bakiye = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Hesap Türü (SAVINGS / CHECKING): "); String tur = scanner.nextLine();
                        System.out.print("Hesabın Bağlanacağı Müşteri ID: "); int musteriId = scanner.nextInt();

                        bankService.openAccount(hesapNo, bakiye, tur, musteriId);
                        System.out.println("✓ Banka hesabı başarıyla açıldı ve müşteriye bağlandı.");
                    }
                    case 4 -> {
                        System.out.println("\n[HESABA PARA YATIRMA]");
                        System.out.print("Para Yatırılacak Hesap No: "); String hesapNo = scanner.nextLine();
                        System.out.print("Yatırılacak Tutar: "); double miktar = scanner.nextDouble();

                        bankService.deposit(hesapNo, miktar);
                        System.out.println("✓ Para yatırma işlemi başarıyla tamamlandı. Güncel bakiye veritabanına işlendi.");
                    }
                    case 5 -> {
                        System.out.println("\n[HESAPTAN PARA ÇEKME]");
                        System.out.print("Para Çekilecek Hesap No: "); String hesapNo = scanner.nextLine();
                        System.out.print("Çekilecek Tutar: "); double miktar = scanner.nextDouble();

                        bankService.withdraw(hesapNo, miktar);
                        System.out.println("✓ Para çekme işlemi başarıyla tamamlandı.");
                    }
                    case 6 -> {
                        System.out.println("\n[HAVALE / EFT - PARA TRANSFERİ]");
                        System.out.print("Kaynak (Gönderen) Hesap No: "); String kaynakNo = scanner.nextLine();
                        System.out.print("Hedef (Alıcı) Hesap No: "); String hedefNo = scanner.nextLine();
                        System.out.print("Transfer Edilecek Tutar: "); double miktar = scanner.nextDouble();

                        bankService.transfer(kaynakNo, hedefNo, miktar);
                        System.out.println("✓ Transfer işlemi başarıyla gerçekleşti. Her iki hesap bakiyesi güncellendi.");
                    }
                    case 7 -> {
                        System.out.print("\nHesaplarını görmek istediğiniz Müşteri ID: ");
                        int musteriId = scanner.nextInt();
                        List<Account> hesaplar = bankService.getCustomerAccounts(musteriId);

                        System.out.println("\n[MÜŞTERİYE AİT HESAPLAR]");
                        if (hesaplar.isEmpty()) {
                            System.out.println("Bu müşteriye ait açılmış bir hesap bulunamadı.");
                        } else {
                            for (Account a : hesaplar) {
                                System.out.printf("Hesap No: %s | Bakiye: %.2f TL | Hesap Türü: %s\n",
                                        a.getAccountNumber(), a.getBalance(), a.getAccountType());
                            }
                        }
                    }
                    case 0 -> {
                        System.out.println("Sistemden güvenli bir şekilde çıkış yapılıyor. İyi günler dileriz!");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Geçersiz seçim! Lütfen menüdeki seçeneklerden birini giriniz.");
                }
            } catch (Exception e) {
                // Herhangi bir validasyon veya SQL hatası durumunda kullanıcıya gösterilecek mesaj
                System.out.println("UYARI: " + e.getMessage());
            }
        }
    }
}