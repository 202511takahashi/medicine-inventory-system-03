<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>薬品詳細</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<main class="detail-page-wrapper">
    <section class="detail-page-card">
        <div class="detail-header">
            <div>
                <p class="section-label">Medicine Detail</p>
                <h1 class="detail-page-title">薬品詳細</h1>
                <p class="detail-page-description">1件の薬品情報を整理して確認できる詳細画面です。</p>
            </div>
            <span class="status-badge ${statusClass}">${statusLabel}</span>
        </div>

        <section class="detail-card">
            <div class="detail-grid">
                <div class="detail-row">
                    <p class="detail-label">薬品ID</p>
                    <p class="detail-value">${medicine.id}</p>
                </div>
                <div class="detail-row">
                    <p class="detail-label">薬品名</p>
                    <p class="detail-value detail-strong">${medicine.name}</p>
                </div>
                <div class="detail-row">
                    <p class="detail-label">カテゴリ</p>
                    <p class="detail-value">${medicine.category}</p>
                </div>
                <div class="detail-row">
                    <p class="detail-label">在庫数</p>
                    <p class="detail-value ${isLowStock ? 'stock-warning-text' : ''}">${medicine.stockQuantity}</p>
                </div>
                <div class="detail-row">
                    <p class="detail-label">使用期限</p>
                    <p class="detail-value ${expirationTextClass}">${medicine.expirationDate}</p>
                </div>
                <div class="detail-row">
                    <p class="detail-label">状態</p>
                    <p class="detail-value"><span class="status-badge ${statusClass}">${statusLabel}</span></p>
                </div>
                <c:if test="${not empty medicine.createdAt}">
                    <div class="detail-row detail-row-full">
                        <p class="detail-label">登録日時</p>
                        <p class="detail-value">${medicine.createdAt}</p>
                    </div>
                </c:if>
            </div>
        </section>

        <div class="detail-actions">
            <a href="/medicines" class="menu-button secondary-button">一覧へ戻る</a>
            <a href="/medicines/edit/${medicine.id}" class="menu-button add-button">編集へ進む</a>
        </div>
    </section>
</main>
</body>
</html>