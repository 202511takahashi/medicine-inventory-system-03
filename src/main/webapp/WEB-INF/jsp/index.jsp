<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>薬品在庫管理システム</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<main class="container">
    <section class="card">
        <%-- ポートフォリオ用のトップ画面であることを補足表示します。 --%>
        <p class="eyebrow">Portfolio Application</p>

        <%-- アプリ名を大きく表示します。 --%>
        <h1>薬品在庫管理システム</h1>

        <%-- アプリの目的が伝わる説明文を表示します。 --%>
        <p class="description">
            このアプリはポートフォリオとして作成した薬品在庫管理システムです。<br>
            薬品の在庫管理・期限管理を行うことを目的としています。
        </p>

        <%-- まだ一覧画面が未実装でも、画面遷移の導線を先に用意します。 --%>
        <div class="action-area">
            <a href="/medicines" class="menu-button">薬品一覧を見る</a>
        </div>
    </section>
</main>
</body>
</html>