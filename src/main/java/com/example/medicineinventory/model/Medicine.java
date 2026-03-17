package com.example.medicineinventory.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 薬品情報を表すモデルクラスです。
 * 今後の一覧表示、登録、編集、削除機能で共通して利用することを想定しています。
 */
public class Medicine {

    /** 薬品IDです。 */
    private Integer id;

    /** 薬品名です。 */
    private String name;

    /** 薬品の分類です。 */
    private String category;

    /** 現在の在庫数です。 */
    private Integer stockQuantity;

    /** 使用期限です。 */
    private LocalDate expirationDate;

    /** データの作成日時です。 */
    private LocalDateTime createdAt;

    /**
     * デフォルトコンストラクタです。
     * フレームワークや後から値を設定する場合に使用します。
     */
    public Medicine() {
    }

    /**
     * すべての項目をまとめて設定するコンストラクタです。
     *
     * @param id 薬品ID
     * @param name 薬品名
     * @param category 薬品の分類
     * @param stockQuantity 在庫数
     * @param expirationDate 使用期限
     * @param createdAt 作成日時
     */
    public Medicine(Integer id, String name, String category, Integer stockQuantity,
                    LocalDate expirationDate, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", expirationDate=" + expirationDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
