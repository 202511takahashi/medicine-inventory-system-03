package com.example.medicineinventory.controller;

import com.example.medicineinventory.model.Medicine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                LocalDate.of(2026, 8, 15),
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
     * @return 薬品登録画面
     */
    @GetMapping("/medicines/new")
    public String showMedicineForm() {
        return "medicine-form";
    }

    /**
     * /medicines にアクセスしたときに薬品一覧画面を表示します。
     *
     * @param model JSP にデータを渡すためのオブジェクト
     * @return 薬品一覧画面
     */
    @GetMapping("/medicines")
    public String showMedicines(Model model) {
        model.addAttribute("medicines", medicines);
        return "medicines";
    }

    /**
     * 登録画面から送信された薬品情報を受け取り、仮データ一覧へ追加します。
     *
     * @param name 薬品名
     * @param category カテゴリ
     * @param stockQuantity 在庫数
     * @param expirationDate 使用期限
     * @return 一覧画面へのリダイレクト
     */
    @PostMapping("/medicines/create")
    public String createMedicine(@RequestParam("name") String name,
                                 @RequestParam("category") String category,
                                 @RequestParam("stockQuantity") Integer stockQuantity,
                                 @RequestParam("expirationDate") LocalDate expirationDate) {
        // 既存データの最大IDに 1 を足して、新しいIDを採番します。
        int nextId = medicines.stream()
                .map(Medicine::getId)
                .max(Integer::compareTo)
                .orElse(0) + 1;

        Medicine medicine = new Medicine(
                nextId,
                name,
                category,
                stockQuantity,
                expirationDate,
                LocalDateTime.now()
        );

        medicines.add(medicine);
        return "redirect:/medicines";
    }
}