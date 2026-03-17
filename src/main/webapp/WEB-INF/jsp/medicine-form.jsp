<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isEditMode ? '薬品編集' : '薬品登録'}</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<main class="form-page-wrapper">
    <section class="form-card">
        <p class="section-label">${isEditMode ? 'Medicine Edit' : 'Medicine Registration'}</p>
        <h1 class="page-title">${isEditMode ? '薬品編集' : '薬品登録'}</h1>
        <p class="page-description">
            ${isEditMode ? '登録済みの薬品情報を修正するための編集画面です。' : '薬品情報を入力するための登録画面です。'}<br>
            ${isEditMode ? '内容を更新すると、一覧画面の対象データだけが変更されます。' : '今回は入力内容を仮データとして一覧へ反映できるようにしています。'}
        </p>

        <%-- 入力エラーがある場合は各項目の近くにメッセージを表示します。 --%>
        <form action="${formAction}" method="post" class="medicine-form">
            <div class="form-group">
                <label for="name" class="form-label">薬品名</label>
                <input type="text" id="name" name="name"
                       class="${not empty errors.name ? 'form-input form-input-error' : 'form-input'}"
                       value="${formData.name}" placeholder="例: アセトアミノフェン">
                <c:if test="${not empty errors.name}">
                    <p class="field-error">${errors.name}</p>
                </c:if>
            </div>

            <div class="form-group">
                <label for="category" class="form-label">カテゴリ</label>
                <input type="text" id="category" name="category"
                       class="${not empty errors.category ? 'form-input form-input-error' : 'form-input'}"
                       value="${formData.category}" placeholder="例: 解熱鎮痛薬">
                <c:if test="${not empty errors.category}">
                    <p class="field-error">${errors.category}</p>
                </c:if>
            </div>

            <div class="form-group">
                <label for="stockQuantity" class="form-label">在庫数</label>
                <input type="number" id="stockQuantity" name="stockQuantity"
                       class="${not empty errors.stockQuantity ? 'form-input form-input-error' : 'form-input'}"
                       value="${formData.stockQuantity}" placeholder="例: 20">
                <c:if test="${not empty errors.stockQuantity}">
                    <p class="field-error">${errors.stockQuantity}</p>
                </c:if>
            </div>

            <div class="form-group">
                <label for="expirationDate" class="form-label">使用期限</label>
                <input type="date" id="expirationDate" name="expirationDate"
                       class="${not empty errors.expirationDate ? 'form-input form-input-error' : 'form-input'}"
                       value="${formData.expirationDate}">
                <c:if test="${not empty errors.expirationDate}">
                    <p class="field-error">${errors.expirationDate}</p>
                </c:if>
            </div>

            <div class="form-actions">
                <button type="submit" class="menu-button">${isEditMode ? '更新' : '登録'}</button>
                <a href="/medicines" class="menu-button secondary-button">一覧へ戻る</a>
            </div>
        </form>
    </section>
</main>
</body>
</html>