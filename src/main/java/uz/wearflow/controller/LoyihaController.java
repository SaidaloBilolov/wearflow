package uz.wearflow.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.wearflow.entity.Inventory;
import uz.wearflow.entity.User;
import uz.wearflow.repository.InventoryRepository;
import uz.wearflow.repository.UserRepository;

import java.util.List;
import java.util.Optional;

// @Controller - bu sinf HTTP so'rovlarini qabul qilib, HTML sahifalarni qaytaradi
@Controller
public class LoyihaController {

    // Spring bu repositorylarni avtomatik "inject" (ulaydi) qiladi
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    // ==============================================
    // GET / -> Login sahifasini ko'rsatish
    // ==============================================
    @GetMapping("/")
    public String loginSahifasi() {
        return "login"; // src/main/resources/templates/login.html
    }

    // ==============================================
    // POST /login -> Login ma'lumotlarini tekshirish
    // ==============================================
    @PostMapping("/login")
    public String loginTekshir(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,  // Sessiyaga foydalanuvchi ma'lumotini saqlaymiz
            Model model) {

        // Bazadan username bo'yicha foydalanuvchini qidirish
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Parolni oddiy solishtirish (oddiy loyiha uchun)
            if (user.getPassword().equals(password)) {
                // Sessiyaga foydalanuvchini saqlaymiz (sahifalar orasida "eslab" turish uchun)
                session.setAttribute("loginUser", user);

                // Roli bo'yicha yo'naltirish
                if ("ADMIN".equals(user.getRole())) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/user";
                }
            }
        }

        // Login noto'g'ri bo'lsa, xato xabari bilan orqaga qaytish
        model.addAttribute("xato", "Username yoki parol noto'g'ri!");
        return "login";
    }

    // ==============================================
    // GET /admin -> Admin paneli
    // ==============================================
    @GetMapping("/admin")
    public String adminPaneli(HttpSession session, Model model) {
        // Sessiyani tekshirish - agar login qilinmagan bo'lsa, qaytaramiz
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/";
        }

        // Bazadan barcha mahsulotlarni olish
        List<Inventory> mahsulotlar = inventoryRepository.findAll();

        // Dashboard uchun statistika hisoblash
        int jami_mahsulot_soni = mahsulotlar.size();
        int jami_miqdor = mahsulotlar.stream()
                .mapToInt(Inventory::getMiqdori)
                .sum();
        long faol_omborlar = mahsulotlar.stream()
                .map(Inventory::getOmborTuri)
                .distinct()
                .count();

        // Model orqali HTML sahifaga ma'lumot uzatish
        model.addAttribute("mahsulotlar", mahsulotlar);
        model.addAttribute("jami_mahsulot_soni", jami_mahsulot_soni);
        model.addAttribute("jami_miqdor", jami_miqdor);
        model.addAttribute("faol_omborlar", faol_omborlar);
        model.addAttribute("loginUser", loginUser);

        return "admin"; // templates/admin.html
    }

    // ==============================================
    // GET /user -> Omborchi paneli
    // ==============================================
    @GetMapping("/user")
    public String userPaneli(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/";
        }

        List<Inventory> mahsulotlar = inventoryRepository.findAll();
        model.addAttribute("mahsulotlar", mahsulotlar);
        model.addAttribute("loginUser", loginUser);

        return "user"; // templates/user.html
    }

    // ==============================================
    // POST /user/update -> Mahsulot miqdorini yangilash
    // ==============================================
    @PostMapping("/user/update")
    public String miqdorniYangilash(
            @RequestParam Long id,
            @RequestParam Integer yangiMiqdor,
            HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/";
        }

        // Bazadan mahsulotni id bo'yicha topib, miqdorini yangilash
        Optional<Inventory> mahsulotOptional = inventoryRepository.findById(id);
        if (mahsulotOptional.isPresent()) {
            Inventory mahsulot = mahsulotOptional.get();
            mahsulot.setMiqdori(yangiMiqdor);
            inventoryRepository.save(mahsulot); // Bazaga saqlash
        }

        return "redirect:/user"; // Yangilangandan so'ng user sahifasiga qaytish
    }

    // ==============================================
    // GET /chiqish -> Sessiyani tozalab chiqish
    // ==============================================
    @GetMapping("/chiqish")
    public String chiqish(HttpSession session) {
        session.invalidate(); // Sessiyani o'chirish
        return "redirect:/";
    }
}
