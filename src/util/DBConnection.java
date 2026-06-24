package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = new Properties();
                try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                    if (in == null) {
                        throw new RuntimeException("db.properties dosyası bulunamadı!");
                    }
                    props.load(in);
                }

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.password");

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Veritabanı bağlantısı başarıyla kuruldu.");

            } catch (Exception e) {
                System.err.println("Veritabanı bağlantı hatası: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}