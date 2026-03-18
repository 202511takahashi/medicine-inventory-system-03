package com.example.medicineinventory.config;

import com.example.medicineinventory.model.Medicine;
import com.example.medicineinventory.repository.MedicineRepository;
import com.example.medicineinventory.service.MedicineService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 画面確認やポートフォリオ説明で使いやすいように、カテゴリ別の薬品テストデータを投入します。
 * すでにデータがある場合は重複登録しません。
 */
@Component
public class MedicineDataInitializer implements CommandLineRunner {

    private final MedicineRepository medicineRepository;
    private final MedicineService medicineService;

    public MedicineDataInitializer(MedicineRepository medicineRepository, MedicineService medicineService) {
        this.medicineRepository = medicineRepository;
        this.medicineService = medicineService;
    }

    @Override
    public void run(String... args) {
        if (medicineRepository.count() > 0) {
            return;
        }

        // カテゴリごとの違いが一覧画面で伝わるよう、3件ずつテストデータを用意します。
        List<Medicine> testMedicines = List.of(
                createTestMedicine("アセトアミノフェン錠", "普通薬", 24, 120, 30),
                createTestMedicine("ビオフェルミン錠", "普通薬", 18, 20, 25),
                createTestMedicine("ムコダイン錠", "普通薬", 9, 90, 18),

                createTestMedicine("アドレナリン注射液", "劇薬", 6, 15, 22),
                createTestMedicine("メトトレキサート錠", "劇薬", 14, 180, 16),
                createTestMedicine("硫酸アトロピン注射液", "劇薬", 4, 28, 12),

                createTestMedicine("ロキソニン錠", "内服薬", 26, 150, 28),
                createTestMedicine("アムロジピンOD錠", "内服薬", 7, 18, 20),
                createTestMedicine("メトホルミン錠", "内服薬", 21, 95, 15),

                createTestMedicine("ロキソニンテープ", "外用薬", 13, 75, 14),
                createTestMedicine("ヒルドイドソフト軟膏", "外用薬", 8, 24, 10),
                createTestMedicine("ゲンタシン軟膏", "外用薬", 19, 140, 9),

                createTestMedicine("生理食塩液注射", "注射薬", 22, 110, 24),
                createTestMedicine("ヘパリン注射液", "注射薬", 5, 21, 17),
                createTestMedicine("セフトリアキソン静注用", "注射薬", 11, 65, 13),

                createTestMedicine("葛根湯エキス顆粒", "漢方薬", 16, 130, 27),
                createTestMedicine("大建中湯エキス顆粒", "漢方薬", 6, 26, 21),
                createTestMedicine("芍薬甘草湯エキス顆粒", "漢方薬", 12, 85, 19),

                createTestMedicine("インスリン製剤", "冷所保存薬", 10, 19, 23),
                createTestMedicine("インフルエンザワクチン", "冷所保存薬", 3, 12, 11),
                createTestMedicine("ラタノプロスト点眼液", "冷所保存薬", 15, 70, 8),

                createTestMedicine("ワルファリン錠", "ハイリスク薬", 7, 32, 29),
                createTestMedicine("ジゴキシン錠", "ハイリスク薬", 5, 17, 20),
                createTestMedicine("シスプラチン点滴静注", "ハイリスク薬", 2, 55, 7)
        );

        for (Medicine medicine : testMedicines) {
            medicineService.createMedicine(medicine);
        }
    }

    /**
     * テストデータを見やすく並べるための補助メソッドです。
     * 在庫数や使用期限に差を付けて、状態表示の確認に使いやすくしています。
     */
    private Medicine createTestMedicine(String name,
                                        String category,
                                        int stockQuantity,
                                        int expirationAfterDays,
                                        int createdBeforeDays) {
        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setCategory(category);
        medicine.setStockQuantity(stockQuantity);
        medicine.setExpirationDate(LocalDate.now().plusDays(expirationAfterDays));
        medicine.setCreatedAt(LocalDateTime.now().minusDays(createdBeforeDays));
        return medicine;
    }
}