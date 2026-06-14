# WearFlow Logistics — Ishga Tushirish Qo'llanmasi

## Loyiha Tuzilmasi

```
wearflow/
├── src/main/java/uz/wearflow/
│   ├── WearflowApplication.java      ← Bosh sinf (ishga tushirish nuqtasi)
│   ├── entity/
│   │   ├── User.java                 ← Foydalanuvchi modeli
│   │   └── Inventory.java            ← Mahsulot modeli
│   ├── repository/
│   │   ├── UserRepository.java       ← Baza amaliyotlari (user)
│   │   └── InventoryRepository.java  ← Baza amaliyotlari (mahsulot)
│   └── controller/
│       └── LoyihaController.java     ← Hamma sahifalar shu yerda
├── src/main/resources/
│   ├── templates/
│   │   ├── login.html                ← Kirish sahifasi
│   │   ├── admin.html                ← Admin dashboard
│   │   └── user.html                 ← Omborchi paneli
│   └── application.properties        ← Sozlamalar
├── database_schema.sql               ← Bazani yaratish uchun SQL
├── Dockerfile                        ← Docker sozlamasi
└── pom.xml                           ← Maven bog'liqliklar
```

---

## 1-QADAM: Neon.tech Bazasini Sozlash

1. [neon.tech](https://neon.tech) saytiga kiring va ro'yxatdan o'ting
2. **"New Project"** tugmasini bosib, loyiha nomi kiriting (masalan: `wearflow-db`)
3. **Dashboard → SQL Editor** bo'limiga o'ting
4. `database_schema.sql` faylidagi kodni to'liq ko'chirib joylashtiring va **Run** bosing
5. **"Connection Details"** bo'limidan quyidagilarni nusxa oling:
   - **Host**: `ep-xxxx.us-east-2.aws.neon.tech`
   - **Database**: `neondb`
   - **Username**: (berilgan username)
   - **Password**: (berilgan parol)

---

## 2-QADAM: GitHub'ga Yuklash

```bash
# Loyiha papkasida terminal oching
git init
git add .
git commit -m "WearFlow Logistics - Birinchi versiya"
git branch -M main
git remote add origin https://github.com/<sizning-username>/<repo-nomi>.git
git push -u origin main
```

---

## 3-QADAM: Render.com'da Deploy Qilish

1. [render.com](https://render.com) ga kiring va **GitHub** orqali ro'yxatdan o'ting
2. **"New +"** → **"Web Service"** ni tanlang
3. GitHub repozitoriyangizni ulang
4. Quyidagi sozlamalarni kiriting:
   - **Name**: `wearflow-logistics`
   - **Region**: `Frankfurt (EU Central)`
   - **Branch**: `main`
   - **Runtime**: `Docker`
   - **Instance Type**: `Free`
5. **"Advanced"** bo'limini oching va **Environment Variables** qo'shing:

```
DATABASE_URL = jdbc:postgresql://<host>/neondb?sslmode=require
SPRING_DATASOURCE_USERNAME = <neon-username>
SPRING_DATASOURCE_PASSWORD = <neon-password>
```

6. **"Create Web Service"** tugmasini bosing

✅ 3-5 daqiqadan so'ng sizning ilovangiz:
`https://wearflow-logistics-xxxx.onrender.com` manzilida ishlaydi!

---

## CI/CD (Avtomatik yangilanish)

IntelliJ IDEA'dan `git push` qilganingizda Render.com **avtomatik**:
1. Yangi kodni GitHub'dan tortib oladi
2. Docker image qayta build qiladi
3. Yangi versiyani deploy qiladi

---

## Test Foydalanuvchilar

| Username  | Parol       | Rol     |
|-----------|-------------|---------|
| admin     | admin123    | ADMIN   |
| omborchi  | omborchi123 | USER    |
| sardor    | sardor123   | USER    |
