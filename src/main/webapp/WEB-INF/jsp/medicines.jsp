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
        <div class="page-header dashboard-hero">
            <div class="hero-background"></div>
            <div class="hero-copy">
                <div class="hero-badge-row">
                    <span class="section-label hero-label">Medicine Inventory</span>
                    <span class="hero-chip">Dashboard</span>
                </div>
                <h1 class="page-title hero-title">医薬品在庫管理ダッシュボード</h1>
                <p class="page-description hero-description">
                    在庫状況・使用期限・安全管理をひとつの画面で確認できる、医療現場向けの管理ダッシュボードです。
                </p>
                <div class="hero-highlights">
                    <span class="hero-highlight">在庫確認</span>
                    <span class="hero-highlight">期限管理</span>
                    <span class="hero-highlight">安全チェック</span>
                </div>
            </div>
            <div class="header-action hero-action">
                <div class="hero-side-card">
                    <p class="hero-side-label">操作メニュー</p>
                    <p class="hero-side-text">新しい薬品情報を登録して、一覧へすぐ反映できます。</p>
                    <a href="/medicines/new" class="menu-button add-button hero-button">＋ 新規登録</a>
                </div>
            </div>
        </div>

        <section class="toolbar-panel">
            <div class="toolbar-heading">
                <h2 class="toolbar-title">検索・表示条件</h2>
                <p class="toolbar-description">薬品名やカテゴリで絞り込み、カテゴリ順に並び替えできます。</p>
            </div>

            <%-- 薬品検索と並び替えをまとめたフォームです。 --%>
            <form action="/medicines" method="get" class="toolbar-form">
                <div class="toolbar-fields">
                    <label class="toolbar-field">
                        <span class="toolbar-label">キーワード検索</span>
                        <input type="text" name="keyword" class="search-input"
                               value="${keyword}" placeholder="薬品名またはカテゴリで検索">
                    </label>

                    <label class="toolbar-field toolbar-field-select">
                        <span class="toolbar-label">カテゴリ並び替え</span>
                        <select name="sort" class="sort-select">
                            <option value="" ${empty sort ? 'selected' : ''}>並び替えなし</option>
                            <option value="categoryAsc" ${sort == 'categoryAsc' ? 'selected' : ''}>カテゴリ昇順</option>
                            <option value="categoryDesc" ${sort == 'categoryDesc' ? 'selected' : ''}>カテゴリ降順</option>
                        </select>
                    </label>
                </div>

                <div class="toolbar-actions">
                    <button type="submit" class="menu-button search-button">検索</button>
                    <a href="/medicines" class="menu-button clear-button">クリア</a>
                </div>
            </form>
        </section>

        <c:choose>
            <c:when test="${not empty medicines}">
                <section class="table-panel">
                    <div class="table-header">
                        <div>
                            <h2 class="table-title">薬品一覧データ</h2>
                            <p class="table-caption">在庫数・使用期限・状態をまとめて確認できます。</p>
                        </div>
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
                                <th>状態</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- Controller から受け取った medicines を1件ずつ表示します。 --%>
                            <c:forEach var="medicine" items="${medicines}">
                                <c:set var="isLowStock" value="${medicine.stockQuantity <= 10}" />
                                <c:set var="isExpirationAlert" value="${expirationAlerts[medicine.id]}" />
                                <c:set var="categoryClass" value="category-other" />
                                <c:choose>
                                    <c:when test="${medicine.category == '解熱鎮痛薬'}">
                                        <c:set var="categoryClass" value="category-analgesic" />
                                    </c:when>
                                    <c:when test="${medicine.category == '抗生物質'}">
                                        <c:set var="categoryClass" value="category-antibiotic" />
                                    </c:when>
                                    <c:when test="${medicine.category == '消毒薬'}">
                                        <c:set var="categoryClass" value="category-disinfectant" />
                                    </c:when>
                                    <c:when test="${medicine.category == '点滴・輸液'}">
                                        <c:set var="categoryClass" value="category-infusion" />
                                    </c:when>
                                    <c:when test="${medicine.category == '劇薬'}">
                                        <c:set var="categoryClass" value="category-strong" />
                                    </c:when>
                                    <c:when test="${medicine.category == '普通薬'}">
                                        <c:set var="categoryClass" value="category-standard" />
                                    </c:when>
                                    <c:when test="${medicine.category == '内服薬'}">
                                        <c:set var="categoryClass" value="category-internal" />
                                    </c:when>
                                    <c:when test="${medicine.category == '外用薬'}">
                                        <c:set var="categoryClass" value="category-topical" />
                                    </c:when>
                                    <c:when test="${medicine.category == '注射薬'}">
                                        <c:set var="categoryClass" value="category-injection" />
                                    </c:when>
                                    <c:when test="${medicine.category == '漢方薬'}">
                                        <c:set var="categoryClass" value="category-kampo" />
                                    </c:when>
                                    <c:when test="${medicine.category == '冷所保存薬'}">
                                        <c:set var="categoryClass" value="category-cold" />
                                    </c:when>
                                    <c:when test="${medicine.category == 'ハイリスク薬'}">
                                        <c:set var="categoryClass" value="category-highrisk" />
                                    </c:when>
                                </c:choose>
                                <tr>
                                    <td class="cell-id">${medicine.id}</td>
                                    <td class="medicine-name-cell">${medicine.name}</td>
                                    <td>
                                        <span class="category-badge ${categoryClass}">${medicine.category}</span>
                                    </td>
                                    <td class="stock-cell ${isLowStock ? 'stock-warning-text' : ''}">
                                        ${medicine.stockQuantity}
                                    </td>
                                    <td class="expiration-cell ${isExpirationAlert ? 'expiration-warning-text' : ''}">
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
                                    <td class="action-cell">
                                        <div class="action-buttons">
                                            <a href="/medicines/edit/${medicine.id}" class="edit-link">編集</a>
                                            <form action="/medicines/delete/${medicine.id}" method="post" class="inline-action-form">
                                                <button type="submit" class="delete-button"
                                                        onclick="return confirm('この薬品を削除しますか？');">削除</button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </section>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <h2 class="empty-title">該当する薬品がありません</h2>
                    <p class="empty-message">検索条件を変更するか、新しい薬品を登録して一覧を確認してください。</p>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="action-area page-footer-actions">
            <a href="/" class="menu-button secondary-button">トップへ戻る</a>
        </div>
    </section>
</main>
</body>
</html>