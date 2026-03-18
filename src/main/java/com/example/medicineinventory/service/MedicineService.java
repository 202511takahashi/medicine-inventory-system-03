package com.example.medicineinventory.service;

import com.example.medicineinventory.model.Medicine;
import com.example.medicineinventory.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * 薬品データの取得・保存・更新・削除をまとめるサービスです。
 * Controller からDB操作の詳細を切り離す役割を持ちます。
 */
@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    /**
     * 一覧表示用の薬品データを取得します。
     * キーワード検索のあとにカテゴリ並び替えを適用します。
     *
     * @param keyword 検索キーワード
     * @param sort 並び替え条件
     * @return 表示用の薬品一覧
     */
    public List<Medicine> getMedicines(String keyword, String sort) {
        String trimmedKeyword = keyword == null ? "" : keyword.trim();

        List<Medicine> medicines;
        if (trimmedKeyword.isEmpty()) {
            medicines = medicineRepository.findAll();
        } else {
            medicines = medicineRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                    trimmedKeyword,
                    trimmedKeyword
            );
        }

        return sortMedicines(medicines, sort == null ? "" : sort.trim());
    }

    /**
     * ID に一致する薬品を1件取得します。
     *
     * @param id 薬品ID
     * @return 薬品データ。存在しない場合は空
     */
    public Optional<Medicine> findMedicineById(Integer id) {
        return medicineRepository.findById(id);
    }

    /**
     * 新しい薬品データを保存します。
     *
     * @param medicine 保存対象の薬品
     * @return 保存後の薬品
     */
    public Medicine createMedicine(Medicine medicine) {
        if (medicine.getCreatedAt() == null) {
            medicine.setCreatedAt(LocalDateTime.now());
        }
        return medicineRepository.save(medicine);
    }

    /**
     * 既存の薬品データを更新します。
     *
     * @param id 更新対象の薬品ID
     * @param name 薬品名
     * @param category カテゴリ
     * @param stockQuantity 在庫数
     * @param expirationDate 使用期限
     * @return 更新後の薬品。存在しない場合は空
     */
    public Optional<Medicine> updateMedicine(Integer id,
                                             String name,
                                             String category,
                                             Integer stockQuantity,
                                             java.time.LocalDate expirationDate) {
        Optional<Medicine> optionalMedicine = medicineRepository.findById(id);
        if (optionalMedicine.isEmpty()) {
            return Optional.empty();
        }

        Medicine medicine = optionalMedicine.get();
        medicine.setName(name);
        medicine.setCategory(category);
        medicine.setStockQuantity(stockQuantity);
        medicine.setExpirationDate(expirationDate);

        return Optional.of(medicineRepository.save(medicine));
    }

    /**
     * ID に一致する薬品データを削除します。
     *
     * @param id 削除対象の薬品ID
     */
    public void deleteMedicine(Integer id) {
        medicineRepository.findById(id).ifPresent(medicineRepository::delete);
    }

    /**
     * 指定された並び替え条件でカテゴリ順に並び替えます。
     *
     * @param medicines 並び替え対象の薬品一覧
     * @param sort 並び替え条件
     * @return 並び替え後の薬品一覧
     */
    private List<Medicine> sortMedicines(List<Medicine> medicines, String sort) {
        List<Medicine> sortedMedicines = new ArrayList<>(medicines);

        Comparator<Medicine> categoryComparator = Comparator.comparing(
                medicine -> medicine.getCategory().toLowerCase(Locale.ROOT)
        );

        if ("categoryAsc".equals(sort)) {
            sortedMedicines.sort(categoryComparator);
        } else if ("categoryDesc".equals(sort)) {
            sortedMedicines.sort(categoryComparator.reversed());
        }

        return sortedMedicines;
    }
}