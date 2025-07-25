# 💼 JobBoard CRM – Job Application Tracker

A full-stack job board and application tracker where admins post jobs and users apply, built with **Spring Boot**, **PostgreSQL**, and **React**.

## 🚀 Live Demo

🌐 [Frontend (Vercel)](https://jobtracker-frontend-kappa.vercel.app/)  
🔗 Backend: [https://jobtracker-backend-nx24.onrender.com/api](https://jobtracker-backend-nx24.onrender.com/api)

---

## 🧰 Tech Stack

- **Frontend:** React, Axios
- **Backend:** Java 17, Spring Boot
- **Authentication:** Spring Security + JWT
- **Database:** PostgreSQL (Render)
- **Persistence:** Spring Data JPA
- **Build Tool:** Maven
- **Version Control:** Git + GitHub
- **Deployment:** Docker, Vercel (Frontend), Render (Backend)
- **IDE:** IntelliJ IDEA

---

## 📦 Features

- 🔐 JWT-based user authentication
- 🧑‍💼 Admin-only job posting via Postman
- 🙋‍♂️ User dashboard to apply and view job applications
- 🔁 Role-based access control
- 🧾 Job application status tracking
- 🐳 Dockerized backend with environment variables

---

## 📁 Folder Structure

```
.
├── backend/
│   ├── src/main/java/com/example/cmr/...
│   └── Dockerfile
├── frontend/
│   └── src/...
```

---

## 🛠️ Setup Instructions

1. **Clone the repo**
   ```bash
   git clone https://github.com/your-username/jobtracker.git
   cd jobtracker
   ```

2. **Backend Setup**
   - Add `.env` or set environment variables:
     ```
     DB_URL=jdbc:postgresql://...
     DB_USER=...
     DB_PASS=...
     JWT_SECRET=...
     ```
   - Run backend:
     ```bash
     ./mvnw spring-boot:run
     ```

3. **Frontend Setup**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

---

## 🩺 Health Check

To keep the backend alive, use this health endpoint:
```
GET https://jobtracker-backend-nx24.onrender.com/api/health
```

---

## 📜 License

This project is open-source and available under the [MIT License](LICENSE).
