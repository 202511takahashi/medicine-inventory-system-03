package com.example.medicineinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 薬品在庫管理アプリの起動クラスです。
 * 今後の機能追加の土台になるよう、最小構成で用意しています。
 */
@SpringBootApplication
public class MedicineInventoryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MedicineInventoryApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MedicineInventoryApplication.class);
    }
}
