package com.example.medicineinventory.controller;

import com.example.medicineinventory.model.Medicine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 薬品一覧画面を表示するためのコントローラーです。
 * 現在はデータベースを使わず、仮データを画面に渡します。
 */
@Controller
public class MedicineController {

    /**
     * /medicines にアクセスしたときに薬品一覧画面を表示します。
     *
     * @param model JSP にデータを渡すためのオブジェクト
     * @return 薬品一覧画面
     */
    @GetMapping("/medicines")
    public String showMedicines(Model model) {
        // 今回は一覧表示の確認用として、仮の薬品データを3件用意します。
        List<Medicine> medicines = List.of(
                new Medicine(
                        1,
                        "アセトアミノフェン",
                        "解熱鎮痛薬",
                        20,
                        LocalDate.of(2026, 12, 31),
                        LocalDateTime.now()
                ),
                new Medicine(
                        2,
                        "アモキシシリン",
                        "抗生物質",
                        8,
                        LocalDate.of(2026, 8, 15),
                        LocalDateTime.now()
                ),
                new Medicine(
                        3,
                        "消毒用エタノール",
                        "消毒薬",
                        15,
                        LocalDate.of(2027, 3, 31),
                        LocalDateTime.now()
                )
        );

        model.addAttribute("medicines", medicines);
        return "medicines";
    }
}