## PayVault - Gamified Financial Wellness App  

### **Overview**
PayVault is a gamified financial wellness app designed to help users improve their financial habits in a fun and engaging way. It provides real-time financial health scores, AI-powered spending insights, challenges, leaderboards, and rewards to encourage better money management.

---

## **📌 Project Structure**

The project follows a **monorepo** approach with separate branches for backend and frontend development:

- `develop` → The main branch containing the final, fully functional application.
- `backend` → Contains all backend-related code, including APIs, authentication, database, and business logic.
- `frontend` → Contains the frontend code, including UI, state management, and user interactions.

---

## **📌 Features**
### ✅ Financial Health Score
- AI-based scoring based on user transactions and habits.
- Personalized suggestions for financial improvement.

### ✅ Gamified Challenges
- Users can take part in saving and budgeting challenges.
- Earn rewards and badges for achieving financial goals.

### ✅ AI-Powered Spending Coach
- Provides personalized insights into spending patterns.
- Suggests optimized budgets and spending habits.

### ✅ Social Leaderboards
- Compete with friends or other users in savings and budgeting challenges.
- Share achievements on social platforms.

### ✅ Daily Bite-Sized Financial Lessons
- Interactive lessons on credit management, investing, and saving.
- AI-adapted content based on user knowledge level.

### ✅ Rewards & Discounts
- Users earn points for financial achievements.
- Redeemable for discounts, cashback, or partnered brand offers.

---

## **📌 Tech Stack**

### **🔹 Backend:**
- **Spring Boot (Java)** - REST API Development
- **PostgreSQL** - Database
- **Prisma ORM** - Database management
- **Redis Queue** - Event-driven architecture
- **Resilience4j** - Circuit breaker for fault tolerance
- **Spring Security & JWT** - Authentication & Authorization
- **Swagger OpenAPI** - API Documentation

### **🔹 Frontend:**
- **React.js (TypeScript)** - UI development
- **Tailwind CSS** - Styling
- **Recoil.js** - State management
- **Axios** - API calls
- **React Query** - Data fetching & caching

### **🔹 DevOps & Deployment:**
- **Docker** - Containerization
- **Cloudflare** - Security & CDN
- **GitHub Actions** - CI/CD Pipeline
- **Nginx** - Reverse Proxy

---

## **📌 Installation & Setup**

### **1️⃣ Clone the Repository**
```bash
git clone -b develop https://github.com/your-username/pay-vault.git
cd pay-vault
```

### **2️⃣ Navigate to Backend or Frontend**
```bash
# For Backend
git checkout backend
cd backend

# For Frontend
git checkout frontend
cd frontend
```

### **3️⃣ Follow Setup Instructions for Each Component**
- [Backend Setup Guide](https://github.com/ZidanAli111/pay-vault/tree/backend)
- [Frontend Setup Guide](https://github.com/ZidanAli111/pay-vault/tree/frontend)

---

## **📌 Contribution Guidelines**

1. **Branching Strategy:**
   - Work on feature branches (e.g., `feature/user-auth`), then merge into `backend` or `frontend`.
   - Once tested, merge into `develop`.

2. **Commit Messages:**
   - Use clear and descriptive commit messages.
   - Example: `feat: add user authentication API`

3. **Code Style:**
   - Follow Java best practices for backend.
   - Maintain React component structure for frontend.

4. **Pull Requests:**
   - Create PRs for merging into `backend` or `frontend`.
   - PRs should have detailed descriptions and pass all tests.

---

## **📌 API Documentation**
API documentation is available via **Swagger OpenAPI**.
- URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## **📌 Monitoring & Health Check**
Spring Actuator is enabled for health monitoring.
- URL: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

---

## **📌 License**
This project is licensed under the MIT License.

---

🚀 **Happy Coding!**

