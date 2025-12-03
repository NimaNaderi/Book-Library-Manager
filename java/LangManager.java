import java.util.HashMap;
import java.util.Map;

class LangManager {
    private static final Map<String, Map<String, String>> dict = new HashMap<>();

    static {
        Map<String, String> de = new HashMap<>();
        de.put("app_title", "Bibliotheks-Manager Pro");
        de.put("search", "Suchen...");
        de.put("title", "Buchtitel");
        de.put("author", "Autor");
        de.put("category", "Kategorie");
        de.put("isbn", "ISBN");
        de.put("avail", "Verfügbar");
        de.put("status", "Status");
        de.put("add", "Hinzufügen");
        de.put("update", "Aktualisieren");
        de.put("delete", "Löschen");
        de.put("borrow", "Ausleihen");
        de.put("return", "Zurückgeben");
        de.put("clear", "Reset");
        de.put("theme", "Design");
        de.put("lang", "Sprache");
        de.put("status_ready", "Bereit.");
        de.put("err_select", "Bitte wählen Sie mindestens ein Buch aus.");
        de.put("err_empty", "Bitte füllen Sie alle Felder aus.");
        de.put("err_borrowed", "Das Buch ist bereits ausgeliehen!");
        dict.put("DE", de);

        Map<String, String> en = new HashMap<>();
        en.put("app_title", "Library Manager Pro");
        en.put("search", "Search...");
        en.put("title", "Book Title");
        en.put("author", "Author");
        en.put("category", "Category");
        en.put("isbn", "ISBN");
        en.put("avail", "Available");
        en.put("status", "Status");
        en.put("add", "Add Book");
        en.put("update", "Update");
        en.put("delete", "Delete");
        en.put("borrow", "Borrow");
        en.put("return", "Return");
        en.put("clear", "Reset");
        en.put("theme", "Theme");
        en.put("lang", "Language");
        en.put("status_ready", "Ready.");
        en.put("err_select", "Please select at least one book.");
        en.put("err_empty", "Please fill in all fields.");
        en.put("err_borrowed", "Book is already borrowed!");
        dict.put("EN", en);

        Map<String, String> fa = new HashMap<>();
        fa.put("app_title", "مدیریت پیشرفته کتابخانه");
        fa.put("search", "جستجو...");
        fa.put("title", "عنوان کتاب");
        fa.put("author", "نویسنده");
        fa.put("category", "دسته‌بندی");
        fa.put("isbn", "شابک");
        fa.put("avail", "موجود");
        fa.put("status", "وضعیت");
        fa.put("add", "افزودن");
        fa.put("update", "ویرایش");
        fa.put("delete", "حذف");
        fa.put("borrow", "امانت گرفتن");
        fa.put("return", "بازگرداندن");
        fa.put("clear", "پاک کردن");
        fa.put("theme", "پوسته");
        fa.put("lang", "زبان");
        fa.put("status_ready", "آماده");
        fa.put("err_select", "لطفاً حداقل یک کتاب را انتخاب کنید.");
        fa.put("err_empty", "لطفاً تمام فیلدها را پر کنید.");
        fa.put("err_borrowed", "این کتاب قبلاً امانت داده شده است!");
        dict.put("FA", fa);
    }

    public static String get(String lang, String key) {
        return dict.getOrDefault(lang, dict.get("DE")).getOrDefault(key, key);
    }
}