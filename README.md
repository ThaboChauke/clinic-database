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

### **2. Configure the Environment**
Create an `.env` file and add the necessary database credentials:
```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=clinic_db
DB_USER=youruser
DB_PASSWORD=yourpassword
JWT_SECRET=your_jwt_secret
```

### **3. Run the Application**
#### **Using Maven**
```sh
./mvnw spring-boot:run
```

#### **Using Docker**
```sh
docker-compose up --build
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

## 📜 API Endpoints
### **Authentication**
| Method | Endpoint        | Description         |
|--------|---------------|---------------------|
| POST   | `/auth/login`  | User login         |
| POST   | `/auth/signup` | Create new account |

### **Patients**
| Method | Endpoint         | Description                   |
|--------|-----------------|-------------------------------|
| GET    | `/patients`      | Get all patients             |
| POST   | `/patients`      | Add a new patient            |
| GET    | `/patients/{id}` | Get patient by ID            |
| PUT    | `/patients/{id}` | Update patient information   |
| DELETE | `/patients/{id}` | Delete a patient             |

### **Employees**
| Method | Endpoint         | Description                   |
|--------|-----------------|-------------------------------|
| GET    | `/employees`      | Get all employees             |
| POST   | `/employees`      | Add a new employee            |
| GET    | `/employees/{id}` | Get employee by ID            |
| PUT    | `/employees/{id}` | Update employee information   |
| DELETE | `/employees/{id}` | Delete an employee           |

## 📌 Future Enhancements
- **Appointment Scheduling**
- **Billing & Payment Integration**
- **Advanced Analytics & Reporting**

## 📜 License
This project is licensed under the **MIT License**.

## 🤝 Contributing
Contributions are welcome! Feel free to submit a pull request or open an issue.

---
💡 **Built with Spring Boot & PostgreSQL**

