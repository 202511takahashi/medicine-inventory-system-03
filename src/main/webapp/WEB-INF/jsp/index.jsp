<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>医薬品在庫管理システム</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<main class="top-page">
    <section class="top-hero-card">
        <div class="top-hero-copy">
            <p class="eyebrow">Medical Inventory Portfolio</p>
            <h1 class="top-title">医薬品在庫管理システム</h1>
            <p class="top-subtitle">在庫・期限・安全管理をサポートする、医療現場向けの管理システムです。</p>
            <p class="description top-description">
                薬品の在庫状況や使用期限、注意が必要な状態を分かりやすく確認できるように設計しています。<br>
                一覧確認から登録・編集まで、日常的な管理操作をスムーズに行えます。
            </p>

            <div class="top-action-group">
                <a href="/medicines" class="menu-button top-primary-button">薬品一覧を見る</a>
                <a href="/medicines/new" class="menu-button top-secondary-button">新規登録</a>
            </div>
        </div>

        <div class="top-hero-visual">
            <div class="top-visual-panel">
                <img
                        src="https://images.unsplash.com/photo-1587854692152-cbe660dbde88?auto=format&fit=crop&w=900&q=80"
                        alt="薬や医療用品をイメージしたトップ画面イラスト"
                        class="top-hero-image">
                <div class="top-visual-badge badge-blue">在庫確認</div>
                <div class="top-visual-badge badge-green">期限チェック</div>
            </div>
        </div>
    </section>

    <section class="feature-section">
        <div class="feature-card">
            <div class="feature-icon">01</div>
            <h2 class="feature-title">在庫管理</h2>
            <p class="feature-text">薬品ごとの在庫数を確認し、不足しそうなものを早めに把握できます。</p>
        </div>

        <div class="feature-card">
            <div class="feature-icon">02</div>
            <h2 class="feature-title">使用期限チェック</h2>
            <p class="feature-text">使用期限が近い薬品を見つけやすくし、管理漏れを防ぎます。</p>
        </div>

        <div class="feature-card">
            <div class="feature-icon">03</div>
            <h2 class="feature-title">安全管理</h2>
            <p class="feature-text">状態表示やカテゴリ整理により、注意が必要な薬品も一目で確認できます。</p>
        </div>
    </section>
</main>
</body>
</html>