package com.example.medicineinventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 薬品情報を表すエンティティです。
 * medicines テーブルと1対1で対応します。
 */
@Entity
@Table(name = "medicines")
public class Medicine {

    /** 薬品IDです。 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 薬品名です。 */
    @Column(nullable = false, length = 100)
    private String name;

    /** 薬品の分類です。 */
    @Column(nullable = false, length = 100)
    private String category;

    /** 現在の在庫数です。 */
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    /** 使用期限です。 */
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    /** データの作成日時です。 */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * デフォルトコンストラクタです。
     * JPA がインスタンス生成するときに使用します。
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

    /**
     * 作成日時が未設定のときだけ、保存直前に現在日時を設定します。
     */
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
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