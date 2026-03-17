<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>薬品登録</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<main class="form-page-wrapper">
    <section class="form-card">
        <p class="section-label">Medicine Registration</p>
        <h1 class="page-title">薬品登録</h1>
        <p class="page-description">
            薬品情報を入力するための登録画面です。<br>
            今回は入力内容を仮データとして一覧へ反映できるようにしています。
        </p>

        <%-- 今後の登録処理追加を想定した入力フォームです。 --%>
        <form action="/medicines/create" method="post" class="medicine-form">
            <div class="form-group">
                <label for="name" class="form-label">薬品名</label>
                <input type="text" id="name" name="name" class="form-input" placeholder="例: アセトアミノフェン">
            </div>

            <div class="form-group">
                <label for="category" class="form-label">カテゴリ</label>
                <input type="text" id="category" name="category" class="form-input" placeholder="例: 解熱鎮痛薬">
            </div>

            <div class="form-group">
                <label for="stockQuantity" class="form-label">在庫数</label>
                <input type="number" id="stockQuantity" name="stockQuantity" class="form-input" placeholder="例: 20">
            </div>

            <div class="form-group">
                <label for="expirationDate" class="form-label">使用期限</label>
                <input type="date" id="expirationDate" name="expirationDate" class="form-input">
            </div>

            <div class="form-actions">
                <button type="submit" class="menu-button">登録</button>
                <a href="/medicines" class="menu-button secondary-button">一覧へ戻る</a>
            </div>
        </form>
    </section>
</main>
</body>
</html>