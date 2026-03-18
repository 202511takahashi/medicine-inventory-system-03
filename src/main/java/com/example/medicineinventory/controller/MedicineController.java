package com.example.medicineinventory.controller;

import com.example.medicineinventory.model.Medicine;
import com.example.medicineinventory.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 薬品一覧・登録・編集画面を表示するためのコントローラーです。
 * 薬品データの読み書きは MedicineService 経由で行います。
 */
@Controller
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    /**
     * /medicines/new にアクセスしたときに薬品登録画面を表示します。
     *
     * @param model JSP にフォーム初期値を渡すためのオブジェクト
     * @return 薬品登録画面
     */
    @GetMapping("/medicines/new")
    public String showMedicineForm(Model model) {
        prepareFormModel(model, createEmptyFormData(), new LinkedHashMap<>(), false, null);
        return "medicine-form";
    }

    /**
     * /medicines/edit/{id} にアクセスしたときに薬品編集画面を表示します。
     *
     * @param id 編集対象の薬品ID
     * @param model JSP にフォーム初期値を渡すためのオブジェクト
     * @return 薬品編集画面、対象がない場合は一覧画面へリダイレクト
     */
    @GetMapping("/medicines/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Medicine> optionalMedicine = medicineService.findMedicineById(id);
        if (optionalMedicine.isEmpty()) {
            return "redirect:/medicines";
        }

        Medicine medicine = optionalMedicine.get();
        prepareFormModel(model, createFormDataFromMedicine(medicine), new LinkedHashMap<>(), true, medicine.getId());
        return "medicine-form";
    }

    /**
     * /medicines にアクセスしたときに薬品一覧画面を表示します。
     * キーワード検索とカテゴリ並び替えは Service 経由で取得します。
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

        List<Medicine> medicines = medicineService.getMedicines(trimmedKeyword, sortValue);

        Map<Integer, Boolean> expirationAlerts = new LinkedHashMap<>();
        Map<Integer, Boolean> expiredAlerts = new LinkedHashMap<>();
        int lowStockCount = 0;
        int expiringSoonCount = 0;
        int expiredCount = 0;

        for (Medicine medicine : medicines) {
            boolean isLowStock = medicine.getStockQuantity() != null && medicine.getStockQuantity() <= 10;
            boolean isExpired = isExpiredTarget(medicine.getExpirationDate(), today);
            boolean isExpirationAlert = isExpiringSoonTarget(medicine.getExpirationDate(), today, alertLimitDate);

            expirationAlerts.put(medicine.getId(), isExpirationAlert);
            expiredAlerts.put(medicine.getId(), isExpired);

            if (isLowStock) {
                lowStockCount++;
            }
            if (isExpired) {
                expiredCount++;
            }
            if (isExpirationAlert) {
                expiringSoonCount++;
            }
        }

        model.addAttribute("medicines", medicines);
        model.addAttribute("expirationAlerts", expirationAlerts);
        model.addAttribute("expiredAlerts", expiredAlerts);
        model.addAttribute("totalCount", medicines.size());
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("expiringSoonCount", expiringSoonCount);
        model.addAttribute("expiredCount", expiredCount);
        model.addAttribute("keyword", trimmedKeyword);
        model.addAttribute("sort", sortValue);
        return "medicines";
    }

    /**
     * 登録画面から送信された薬品情報を受け取り、入力チェック後にDBへ保存します。
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
        MedicineFormData validatedForm = validateMedicineInput(name, category, stockQuantityText, expirationDateText);

        if (!validatedForm.getErrors().isEmpty()) {
            prepareFormModel(model, validatedForm.getFormData(), validatedForm.getErrors(), false, null);
            return "medicine-form";
        }

        Medicine medicine = new Medicine();
        medicine.setName(validatedForm.getName());
        medicine.setCategory(validatedForm.getCategory());
        medicine.setStockQuantity(validatedForm.getStockQuantity());
        medicine.setExpirationDate(validatedForm.getExpirationDate());

        medicineService.createMedicine(medicine);
        return "redirect:/medicines";
    }

    /**
     * 編集画面から送信された薬品情報を受け取り、入力チェック後にDBへ反映します。
     *
     * @param id 更新対象の薬品ID
     * @param name 薬品名
     * @param category カテゴリ
     * @param stockQuantityText 在庫数の入力値
     * @param expirationDateText 使用期限の入力値
     * @param model エラー時に画面へ値を戻すためのオブジェクト
     * @return 一覧画面へのリダイレクト、または編集画面
     */
    @PostMapping("/medicines/update/{id}")
    public String updateMedicine(@PathVariable Integer id,
                                 @RequestParam("name") String name,
                                 @RequestParam("category") String category,
                                 @RequestParam("stockQuantity") String stockQuantityText,
                                 @RequestParam("expirationDate") String expirationDateText,
                                 Model model) {
        Optional<Medicine> optionalMedicine = medicineService.findMedicineById(id);
        if (optionalMedicine.isEmpty()) {
            return "redirect:/medicines";
        }

        MedicineFormData validatedForm = validateMedicineInput(name, category, stockQuantityText, expirationDateText);

        if (!validatedForm.getErrors().isEmpty()) {
            prepareFormModel(model, validatedForm.getFormData(), validatedForm.getErrors(), true, id);
            return "medicine-form";
        }

        medicineService.updateMedicine(
                id,
                validatedForm.getName(),
                validatedForm.getCategory(),
                validatedForm.getStockQuantity(),
                validatedForm.getExpirationDate()
        );

        return "redirect:/medicines";
    }

    /**
     * 一覧画面から送信された削除操作を受け取り、対象データだけ削除します。
     *
     * @param id 削除対象の薬品ID
     * @return 一覧画面へのリダイレクト
     */
    @PostMapping("/medicines/delete/{id}")
    public String deleteMedicine(@PathVariable Integer id) {
        medicineService.deleteMedicine(id);
        return "redirect:/medicines";
    }

    /**
     * 登録と更新で共通利用する入力チェックです。
     * 入力値の整形結果とエラーメッセージをまとめて返します。
     *
     * @param name 薬品名の入力値
     * @param category カテゴリの入力値
     * @param stockQuantityText 在庫数の入力値
     * @param expirationDateText 使用期限の入力値
     * @return 整形後の値とエラー情報
     */
    private MedicineFormData validateMedicineInput(String name,
                                                   String category,
                                                   String stockQuantityText,
                                                   String expirationDateText) {
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

        return new MedicineFormData(
                formData,
                errors,
                trimmedName,
                trimmedCategory,
                stockQuantity,
                expirationDate
        );
    }

    /**
     * 新規登録画面で使う空のフォームデータを作成します。
     *
     * @return 空のフォームデータ
     */
    private Map<String, String> createEmptyFormData() {
        Map<String, String> formData = new LinkedHashMap<>();
        formData.put("name", "");
        formData.put("category", "");
        formData.put("stockQuantity", "");
        formData.put("expirationDate", "");
        return formData;
    }

    /**
     * 編集画面で初期表示するためのフォームデータを作成します。
     *
     * @param medicine 編集対象の薬品
     * @return フォーム初期表示用データ
     */
    private Map<String, String> createFormDataFromMedicine(Medicine medicine) {
        Map<String, String> formData = new LinkedHashMap<>();
        formData.put("name", medicine.getName());
        formData.put("category", medicine.getCategory());
        formData.put("stockQuantity", String.valueOf(medicine.getStockQuantity()));
        formData.put("expirationDate", String.valueOf(medicine.getExpirationDate()));
        return formData;
    }

    /**
     * 使用期限が今日より前かどうかを判定します。
     *
     * @param expirationDate 使用期限
     * @param today 今日の日付
     * @return 期限切れの対象なら true
     */
    private boolean isExpiredTarget(LocalDate expirationDate, LocalDate today) {
        return expirationDate != null && expirationDate.isBefore(today);
    }

    /**
     * 使用期限が今日以上かつ1か月以内かどうかを判定します。
     *
     * @param expirationDate 使用期限
     * @param today 今日の日付
     * @param alertLimitDate 注意表示の上限日
     * @return 期限注意の対象なら true
     */
    private boolean isExpiringSoonTarget(LocalDate expirationDate, LocalDate today, LocalDate alertLimitDate) {
        return expirationDate != null
                && !expirationDate.isBefore(today)
                && !expirationDate.isAfter(alertLimitDate);
    }

    /**
     * 登録画面と編集画面で使う共通属性を設定します。
     *
     * @param model JSP に渡すオブジェクト
     * @param formData 入力済みの値
     * @param errors 項目ごとのエラーメッセージ
     * @param isEditMode 編集画面なら true
     * @param medicineId 編集対象の薬品ID
     */
    private void prepareFormModel(Model model,
                                  Map<String, String> formData,
                                  Map<String, String> errors,
                                  boolean isEditMode,
                                  Integer medicineId) {
        model.addAttribute("formData", formData);
        model.addAttribute("errors", errors);
        model.addAttribute("isEditMode", isEditMode);
        model.addAttribute("medicineId", medicineId);
        model.addAttribute("formAction", isEditMode ? "/medicines/update/" + medicineId : "/medicines/create");
    }

    /**
     * 入力チェック後の値をまとめて扱うための小さなクラスです。
     */
    private static class MedicineFormData {
        private final Map<String, String> formData;
        private final Map<String, String> errors;
        private final String name;
        private final String category;
        private final Integer stockQuantity;
        private final LocalDate expirationDate;

        private MedicineFormData(Map<String, String> formData,
                                 Map<String, String> errors,
                                 String name,
                                 String category,
                                 Integer stockQuantity,
                                 LocalDate expirationDate) {
            this.formData = formData;
            this.errors = errors;
            this.name = name;
            this.category = category;
            this.stockQuantity = stockQuantity;
            this.expirationDate = expirationDate;
        }

        public Map<String, String> getFormData() {
            return formData;
        }

        public Map<String, String> getErrors() {
            return errors;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public Integer getStockQuantity() {
            return stockQuantity;
        }

        public LocalDate getExpirationDate() {
            return expirationDate;
        }
    }
}