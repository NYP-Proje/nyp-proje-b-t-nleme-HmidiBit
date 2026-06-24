# 📊 Proje Tasarım ve Mimari Raporu

## 1. Mimari Tasarım (Katmanlı Yapı)
Projede kodun sürdürülebilir ve okunabilir olması için **Katmanlı Mimari (Layered Architecture)** modeli benimsenmiştir:
* **Controller (Arayüz):** Kullanıcı ile etkileşime geçen, konsol menüsünü yöneten katman (`BankConsoleApp`).
* **Services (İş Mantığı):** Kuralların, doğrulamaların ve hata yönetiminin yapıldığı çekirdek katman (`BankService`).
* **Repository / Repo (Veri Erişimi):** Veritabanı sorgularının (CRUD) çalıştırıldığı yer (`CustomerRepository`, `AccountRepository`).
* **Models (Varlıklar):** Banka nesnelerini temsil eden sınıflar. `Account` sınıfı abstract (soyut) yapıdadır.
* **Util (Araçlar):** Veritabanı singleton bağlantısını yöneten `DBConnection` sınıfı.

## 2. Kullanılan Teknolojiler ve Yapılar
* **Dil:** Java 17+
* **Veritabanı:** MySQL & JDBC Sürücüsü (mysql-connector-j)
* **Kalıtım & Polimorfizm:** Hesap türleri (`CheckingAccount` ve `SavingsAccount`), `Account` soyut sınıfından türetilmiştir. Veritabanından veriler okunurken polymorphism (Çok Biçimlilik) kullanılarak dinamik nesne dönüşümü (`mapAccount`) yapılmıştır.

## 3. Veritabanı Şeması
* **customers:** `id` (PK, Auto Increment), `first_name`, `last_name`, `tc_no` (Unique), `email`.
* **accounts:** `id` (PK, Auto Increment), `account_number` (Unique), `balance`, `account_type`, `customer_id` (FK).