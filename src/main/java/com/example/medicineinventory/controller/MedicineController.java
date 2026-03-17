package com.example.medicineinventory.controller;

import com.example.medicineinventory.model.Medicine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 薬品一覧画面を表示するためのコントローラーです。
 * 現在はデータベースを使わず、仮データを画面に渡します。
 */
@Controller
public class MedicineController {

    /**
     * データベースの代わりに、画面表示用の仮データを保持します。
     * 今回は登録処理の流れを確認するため、Controller 内で管理します。
     */
    private final List<Medicine> medicines = new ArrayList<>();

    /**
     * コントローラー生成時に初期表示用のテストデータを用意します。
     */
    public MedicineController() {
        medicines.add(new Medicine(
                1,
                "アセトアミノフェン",
                "解熱鎮痛薬",
                20,
                LocalDate.of(2026, 12, 31),
                LocalDateTime.now()
        ));
        medicines.add(new Medicine(
                2,
                "アモキシシリン",
                "抗生物質",
                8,
                LocalDate.now().plusDays(20),
                LocalDateTime.now()
        ));
        medicines.add(new Medicine(
                3,
                "消毒用エタノール",
                "消毒薬",
                15,
                LocalDate.of(2027, 3, 31),
                LocalDateTime.now()
        ));
        medicines.add(new Medicine(
                4,
                "ロキソプロフェン",
                "解熱鎮痛薬",
                5,
                LocalDate.of(2026, 10, 20),
                LocalDateTime.now()
        ));
        medicines.add(new Medicine(
                5,
                "生理食塩液",
                "点滴・輸液",
                30,
                LocalDate.of(2027, 6, 10),
                LocalDateTime.now()
        ));
    }

    /**
     * /medicines/new にアクセスしたときに薬品登録画面を表示します。
     *
     * @param model JSP にフォーム初期値を渡すためのオブジェクト
     * @return 薬品登録画面
     */
    @GetMapping("/medicines/new")
    public String showMedicineForm(Model model) {
        prepareFormModel(model, new LinkedHashMap<>(), new LinkedHashMap<>());
        return "medicine-form";
    }

    /**
     * /medicines にアクセスしたときに薬品一覧画面を表示します。
     * キーワードがある場合は薬品名またはカテゴリで部分一致検索し、その後に並び替えを適用します。
     *
     * @param keyword 検索キーワード
     * @param sort 並び替え条件
     * @param model JSP にデータを渡すためのオブジェクト
     * @return 薬品一覧画面
     */
    @GetMapping("/medicines")
    public String showMedicines(@RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "sort", required = false) String sort,
                                Model model) {
        LocalDate today = LocalDate.now();
        LocalDate alertLimitDate = today.plusMonths(1);
        String trimmedKeyword = keyword == null ? "" : keyword.trim();
        String sortValue = sort == null ? "" : sort.trim();

        List<Medicine> filteredMedicines = filterMedicines(trimmedKeyword);
        List<Medicine> sortedMedicines = sortMedicines(filteredMedicines, sortValue);

        Map<Integer, Boolean> expirationAlerts = new LinkedHashMap<>();
        for (Medicine medicine : sortedMedicines) {
            expirationAlerts.put(medicine.getId(), isExpirationAlertTarget(medicine.getExpirationDate(), today, alertLimitDate));
        }

        model.addAttribute("medicines", sortedMedicines);
        model.addAttribute("expirationAlerts", expirationAlerts);
        model.addAttribute("keyword", trimmedKeyword);
        model.addAttribute("sort", sortValue);
        return "medicines";
    }

    /**
     * 登録画面から送信された薬品情報を受け取り、入力チェック後に仮データ一覧へ追加します。
     *
     * @param name 薬品名
     * @param category カテゴリ
     * @param stockQuantityText 在庫数の入力値
     * @param expirationDateText 使用期限の入力値
     * @param model エラー時に画面へ値を戻すためのオブジェクト
     * @return 一覧画面へのリダイレクト、または登録画面
     */
    @PostMapping("/medicines/create")
    public String createMedicine(@RequestParam("name") String name,
                                 @RequestParam("category") String category,
                                 @RequestParam("stockQuantity") String stockQuantityText,
                                 @RequestParam("expirationDate") String expirationDateText,
                                 Model model) {
        Map<String, String> formData = new LinkedHashMap<>();
        formData.put("name", name);
        formData.put("category", category);
        formData.put("stockQuantity", stockQuantityText);
        formData.put("expirationDate", expirationDateText);

        Map<String, String> errors = new LinkedHashMap<>();

        String trimmedName = name == null ? "" : name.trim();
        if (trimmedName.isEmpty()) {
            errors.put("name", "薬品名を入力してください");
        }

        String trimmedCategory = category == null ? "" : category.trim();
        if (trimmedCategory.isEmpty()) {
            errors.put("category", "カテゴリを入力してください");
        }

        Integer stockQuantity = null;
        if (stockQuantityText == null || stockQuantityText.trim().isEmpty()) {
            errors.put("stockQuantity", "在庫数を入力してください");
        } else {
            try {
                stockQuantity = Integer.parseInt(stockQuantityText.trim());
                if (stockQuantity < 0) {
                    errors.put("stockQuantity", "在庫数は0以上で入力してください");
                }
            } catch (NumberFormatException exception) {
                errors.put("stockQuantity", "在庫数は0以上で入力してください");
            }
        }

        LocalDate expirationDate = null;
        if (expirationDateText == null || expirationDateText.trim().isEmpty()) {
            errors.put("expirationDate", "使用期限を入力してください");
        } else {
            try {
                expirationDate = LocalDate.parse(expirationDateText.trim());
            } catch (DateTimeParseException exception) {
                errors.put("expirationDate", "使用期限を入力してください");
            }
        }

        if (!errors.isEmpty()) {
            prepareFormModel(model, formData, errors);
            return "medicine-form";
        }

        // 既存データの最大IDに 1 を足して、新しいIDを採番します。
        int nextId = medicines.stream()
                .map(Medicine::getId)
                .max(Integer::compareTo)
                .orElse(0) + 1;

        Medicine medicine = new Medicine(
                nextId,
                trimmedName,
                trimmedCategory,
                stockQuantity,
                expirationDate,
                LocalDateTime.now()
        );

        medicines.add(medicine);
        return "redirect:/medicines";
    }

    /**
     * 薬品名またはカテゴリに対して部分一致検索を行います。
     *
     * @param keyword 検索キーワード
     * @return 検索後の薬品一覧
     */
    private List<Medicine> filterMedicines(String keyword) {
        if (keyword.isEmpty()) {
            return new ArrayList<>(medicines);
        }

        String lowerKeyword = keyword.toLowerCase(Locale.ROOT);
        List<Medicine> filteredMedicines = new ArrayList<>();

        for (Medicine medicine : medicines) {
            String medicineName = medicine.getName().toLowerCase(Locale.ROOT);
            String medicineCategory = medicine.getCategory().toLowerCase(Locale.ROOT);

            if (medicineName.contains(lowerKeyword) || medicineCategory.contains(lowerKeyword)) {
                filteredMedicines.add(medicine);
            }
        }

        return filteredMedicines;
    }

    /**
     * 指定された並び替え条件でカテゴリ順に並び替えます。
     *
     * @param targetMedicines 並び替え対象の薬品一覧
     * @param sort 並び替え条件
     * @return 並び替え後の薬品一覧
     */
    private List<Medicine> sortMedicines(List<Medicine> targetMedicines, String sort) {
        List<Medicine> sortedMedicines = new ArrayList<>(targetMedicines);

        Comparator<Medicine> categoryComparator = Comparator.comparing(
                medicine -> medicine.getCategory().toLowerCase(Locale.ROOT)
        );

        if ("categoryAsc".equals(sort)) {
            sortedMedicines.sort(categoryComparator);
        } else if ("categoryDesc".equals(sort)) {
            sortedMedicines.sort(categoryComparator.reversed());
        }

        return sortedMedicines;
    }

    /**
     * 使用期限が今日以降かつ1か月以内かどうかを判定します。
     *
     * @param expirationDate 使用期限
     * @param today 今日の日付
     * @param alertLimitDate 注意表示の上限日
     * @return 期限注意の対象なら true
     */
    private boolean isExpirationAlertTarget(LocalDate expirationDate, LocalDate today, LocalDate alertLimitDate) {
        return expirationDate != null
                && !expirationDate.isBefore(today)
                && !expirationDate.isAfter(alertLimitDate);
    }

    /**
     * 登録画面で使う入力値とエラーメッセージをまとめて設定します。
     *
     * @param model JSP に渡すオブジェクト
     * @param formData 入力済みの値
     * @param errors 項目ごとのエラーメッセージ
     */
    private void prepareFormModel(Model model, Map<String, String> formData, Map<String, String> errors) {
        model.addAttribute("formData", formData);
        model.addAttribute("errors", errors);
    }
}