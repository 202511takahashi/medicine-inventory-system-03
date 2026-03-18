package com.example.medicineinventory.repository;

import com.example.medicineinventory.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * medicines テーブルへアクセスするためのリポジトリです。
 */
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    /**
     * 薬品名またはカテゴリで部分一致検索します。
     *
     * @param nameKeyword 薬品名の検索キーワード
     * @param categoryKeyword カテゴリの検索キーワード
     * @return 条件に一致した薬品一覧
     */
    List<Medicine> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String nameKeyword, String categoryKeyword);
}