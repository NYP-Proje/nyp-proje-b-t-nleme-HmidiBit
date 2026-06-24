# 🏦 Bank Yönetim Sistemi (BBS) - Veritabanı Tabanlı Banka Otomasyonu

Bu proje, **Beykoz Üniversitesi - Programlama II / Nesne Yönelimli Programlama** dersi dönemi sonu projesi olarak geliştirilmiştir. Proje, katmanlı mimari (Layered Architecture) prensiplerine uygun, MySQL veritabanı bağlantılı ve eksiksiz CRUD işlemlerini barındıran konsol tabanlı bir bankacılık yönetim otomasyonudur.

## 🚀 Özellikler & Gereksinim Karşılamaları
* **Nesne Yönelimli Programlama (OOP):** `Account`, `Customer`, `CheckingAccount` ve `SavingsAccount` sınıfları üzerinden Kalıtım (Inheritance), Polimorfizm (Çok Biçimlilik) ve Soyutlama (Abstraction) ilkeleri eksiksiz uygulanmıştır.
* **Katmanlı Mimari:** Proje `controller`, `services`, `repo`, `models`, `exceptions` ve `util` olmak üzere 6 ana katmana bölünmüştür.
* **Veritabanı Entegrasyonu (JDBC):** MySQL veritabanı ile ham SQL sorguları üzerinden `PreparedStatement` kullanılarak güvenli bağlantı kurulmuştur.
* **Hata Yönetimi & Validasyon:** T.C. Kimlik No kontrolü (11 hane), yetersiz bakiye kontrolleri ve sıfırdan küçük bakiye engellemeleri gibi tüm iş mantığı (Business Logic) kuralları `BankService` katmanında güvenceye alınmıştır.

## 🛠️ Kurulum ve Çalıştırma
1. **Veritabanı Hazırlığı:** XAMPP üzerinden MySQL servisinin (Port: 3307) aktif olduğundan emin olun ve `banking_system` adında bir veritabanı oluşturup tablları yükleyin.
2. **Bağlantı Ayarları:** `src/main/resources/db.properties` dosyasındaki port, kullanıcı adı ve şifre bilgilerini yerel ortamınıza göre doğrulayın.
3. **Çalıştırma:** `controller.BankConsoleApp` sınıfına sağ tıklayıp **Run** seçeneği ile uygulamayı başlatabilirsiniz.