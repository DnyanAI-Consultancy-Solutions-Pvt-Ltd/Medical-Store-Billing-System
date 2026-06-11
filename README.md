# Medical Billing & Inventory Management System

A complete Medical Store Billing and Inventory Management System developed using Spring Boot, MySQL, HTML, CSS, JavaScript and JPA/Hibernate.

## Features

### Master Modules
- Medicine Management
- Customer Management
- Supplier Management

### Transaction Modules
- Billing / Sales
- Purchase Entry
- Purchase History
- Bill History
- Opening Stock
- Sale Return
- Purchase Return
- Expiry & Breakage Entry
- Payment Entry
- Receipt Entry
- Daily Transaction Report

### Inventory Features
- Stock Management
- Low Stock Alert
- Out Of Stock Alert
- Expiring Medicines Alert

### Dashboard Features
- Total Medicines
- Total Customers
- Total Suppliers
- Total Bills
- Total Purchases
- Today's Sales
- Today's Purchase
- Today's Profit
- Recent Bills
- Recent Purchases
- Sales Graph
- Purchase Graph
- Inventory Graph

---

## Technologies Used

### Backend
- Java 19
- Spring Boot
- Spring Data JPA
- Hibernate

### Frontend
- HTML
- CSS
- JavaScript
- Chart.js

### Database
- MySQL

### Build Tool
- Maven

---

## Database Setup

Create database:

```sql
CREATE DATABASE medical_billing;
```

Update database configuration in:

```
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medical_billing
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## How To Run Project

### Step 1

Clone repository

```bash
git clone https://github.com/your-username/medical-billing-system.git
```

### Step 2

Open project in Eclipse or Spring Tool Suite.

### Step 3

Create MySQL database:

```sql
CREATE DATABASE medical_billing;
```

### Step 4

Update database credentials in:

```properties
application.properties
```

### Step 5

Run Spring Boot Application

```java
MedicalBillingSystemApplication.java
```

### Step 6

Open browser:

```
http://localhost:8080/dashboard.html
```

---

## Project Structure

```
src/main/java/com/medical

├── controller
├── service
├── repository
├── entity
├── dto

src/main/resources

├── static
│   ├── dashboard.html
│   ├── billing.html
│   ├── medicine.html
│   ├── customer.html
│   ├── supplier.html
│   ├── purchase.html
│   ├── inventory.html
│   └── other pages

└── application.properties
```

---

## Main APIs

### Dashboard

```
GET /api/dashboard
```

### Medicines

```
GET /api/medicines
POST /api/medicines
DELETE /api/medicines/{id}
```

### Customers

```
GET /api/customers
POST /api/customers
DELETE /api/customers/{id}
```

### Suppliers

```
GET /api/suppliers
POST /api/suppliers
DELETE /api/suppliers/{id}
```

### Purchases

```
GET /api/purchases
POST /api/purchases
DELETE /api/purchases/{id}
```

### Bills

```
POST /api/bills
GET /api/bills
```

---


---

## Developed By

Nikhil Dagadkar

Medical Billing System
