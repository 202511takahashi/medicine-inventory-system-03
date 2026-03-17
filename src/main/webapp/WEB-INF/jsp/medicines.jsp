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

        <%-- 薬品登録画面へ移動するためのボタンです。 --%>
        <div class="list-header-actions">
            <a href="/medicines/new" class="menu-button add-button">＋ 新規登録</a>
        </div>

        <div class="table-wrapper">
            <table class="medicine-table">
                <thead>
                <tr>
                    <th>薬品ID</th>
                    <th>薬品名</th>
                    <th>カテゴリ</th>
                    <th>在庫数</th>
                    <th>使用期限</th>
                </tr>
                </thead>
                <tbody>
                <%-- Controller から受け取った medicines を1件ずつ表示します。 --%>
                <c:forEach var="medicine" items="${medicines}">
                    <tr>
                        <td>${medicine.id}</td>
                        <td>${medicine.name}</td>
                        <td>${medicine.category}</td>
                        <td>${medicine.stockQuantity}</td>
                        <td>${medicine.expirationDate}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="action-area">
            <a href="/" class="menu-button secondary-button">トップへ戻る</a>
        </div>
    </section>
</main>
</body>
</html>