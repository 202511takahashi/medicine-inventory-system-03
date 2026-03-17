<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>薬品一覧</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<main class="page-wrapper">
    <section class="list-card">
        <p class="section-label">Medicine Inventory</p>
        <h1 class="page-title">薬品一覧</h1>
        <p class="page-description">
            現在登録されている薬品情報の一覧です。<br>
            今後、この画面をもとに登録・編集・削除機能を追加していきます。
        </p>

        <div class="list-toolbar">
            <%-- 薬品検索と並び替えをまとめたフォームです。 --%>
            <form action="/medicines" method="get" class="search-form">
                <input type="text" name="keyword" class="search-input"
                       value="${keyword}" placeholder="薬品名またはカテゴリで検索">

                <select name="sort" class="sort-select">
                    <option value="" ${empty sort ? 'selected' : ''}>並び替えなし</option>
                    <option value="categoryAsc" ${sort == 'categoryAsc' ? 'selected' : ''}>カテゴリ昇順</option>
                    <option value="categoryDesc" ${sort == 'categoryDesc' ? 'selected' : ''}>カテゴリ降順</option>
                </select>

                <button type="submit" class="menu-button search-button">検索</button>
                <a href="/medicines" class="menu-button clear-button">クリア</a>
            </form>

            <%-- 薬品登録画面へ移動するためのボタンです。 --%>
            <div class="list-header-actions">
                <a href="/medicines/new" class="menu-button add-button">＋ 新規登録</a>
            </div>
        </div>

        <c:choose>
            <c:when test="${not empty medicines}">
                <div class="table-wrapper">
                    <table class="medicine-table">
                        <thead>
                        <tr>
                            <th>薬品ID</th>
                            <th>薬品名</th>
                            <th>カテゴリ</th>
                            <th>在庫数</th>
                            <th>使用期限</th>
                            <th>状態</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%-- Controller から受け取った medicines を1件ずつ表示します。 --%>
                        <c:forEach var="medicine" items="${medicines}">
                            <c:set var="isLowStock" value="${medicine.stockQuantity <= 10}" />
                            <c:set var="isExpirationAlert" value="${expirationAlerts[medicine.id]}" />
                            <tr>
                                <td>${medicine.id}</td>
                                <td>${medicine.name}</td>
                                <td>${medicine.category}</td>
                                <td class="${isLowStock ? 'stock-warning-text' : ''}">
                                    ${medicine.stockQuantity}
                                </td>
                                <td class="${isExpirationAlert ? 'expiration-warning-text' : ''}">
                                    ${medicine.expirationDate}
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${isLowStock and isExpirationAlert}">
                                            <span class="status-badge status-critical">在庫不足・期限注意</span>
                                        </c:when>
                                        <c:when test="${isLowStock}">
                                            <span class="status-badge status-warning">在庫不足</span>
                                        </c:when>
                                        <c:when test="${isExpirationAlert}">
                                            <span class="status-badge status-expiration">期限注意</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-badge status-normal">正常</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <p class="empty-message">該当する薬品がありません。</p>
            </c:otherwise>
        </c:choose>

        <div class="action-area">
            <a href="/" class="menu-button secondary-button">トップへ戻る</a>
        </div>
    </section>
</main>
</body>
</html>