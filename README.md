# Clinic Database API

## 📌 Project Overview
The **Clinic Database API** is a secure and efficient system for managing clinic operations, including patient records, employee details, and role-based access control (RBAC). Built using **Spring Boot** and **PostgreSQL**, this internal system ensures streamlined management of clinic data while maintaining security and scalability.

## 🚀 Features
- **Patient Management**: Create, update, retrieve, and delete patient records.
- **Employee Management**: Handle employee records with different roles and permissions.
- **Role-Based Access Control (RBAC)**: Restrict access based on user roles.
- **Authentication & Authorization**: Secure API endpoints using **JWT authentication**.
- **Database Management**: Efficient storage and retrieval using **JPA & Hibernate**.
- **Unit & Integration Testing**: Comprehensive test coverage using **JUnit**.
- **Containerization**: Supports **Docker** for deployment.
- **API Documentation**: Available via **Swagger**.

## 🛠️ Tech Stack
- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: PostgreSQL
- **Authentication**: JWT (JSON Web Tokens)
- **Testing**: JUnit, Mockito
- **Deployment**: Docker, Docker Compose

## 🏗️ Installation & Setup
### **1. Clone the Repository**
```sh
git clone https://github.com/ThaboChauke/clinic-database.git
cd clinic-database
```

### **2. Run the Application**

#### **Using Docker**
```sh
docker compose up --build
```

## 🧪 Running Tests
To execute unit tests:
```sh
./mvnw test
```
For integration tests:
```sh
./mvnw verify
```

## 📜 License
This project is licensed under the **MIT License**.

## 🤝 Contributing
Contributions are welcome! Feel free to submit a pull request or open an issue.

---
💡 **Built with Spring Boot & PostgreSQL**

