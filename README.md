# User Management REST API (Spring Boot + MongoDB)

## 🚀 Getting Started

### Start MongoDB using Docker

Before running the application, start MongoDB using the following
command:

``` bash
docker run -d -p 27017:27017 --name mongodb mongo:8.2
```

Make sure Docker is installed and running on your system.

------------------------------------------------------------------------

## 📌 Project Overview

This project is a **Spring Boot REST API** for managing users using
**MongoDB** as the database.\
It supports:

-   CRUD operations on users
-   Search and filter users
-   Pagination
-   Aggregation queries (oldest user by city, population by city)
-   Base64 image support for user photos

------------------------------------------------------------------------

## 🧱 Tech Stack

-   Java 17+
-   Spring Boot
-   Spring Data MongoDB
-   MongoDB (Docker)
-   Lombok
-   Maven

------------------------------------------------------------------------

## 📂 Project Structure

    com.sumit
     ├── controller
     │   └── UserController.java
     ├── entity
     │   ├── User.java
     │   ├── Photo.java
     │   └── Address.java
     ├── repository
     ├── service
     └── Application.java

------------------------------------------------------------------------

## 🧩 Entity Details

### User

-   id
-   firstName
-   lastName
-   email
-   age
-   photo (Base64)
-   hobbies
-   addresses

### Photo

-   id
-   title
-   photoBase64

------------------------------------------------------------------------

## 🔗 API Endpoints

### 1️⃣ Get All Users

    GET /api/user/

### 2️⃣ Create User

    POST /api/user/

**Body Example:**

``` json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "age": 28,
  "photo": {
    "title": "Profile Picture",
    "photoBase64": "BASE64_STRING"
  },
  "hobbies": ["Reading", "Gaming"],
  "addresses": [
    {
      "street": "123 Main St",
      "city": "Toronto",
      "state": "ON",
      "country": "Canada",
      "zipCode": "M1M1M1"
    }
  ]
}
```

### 3️⃣ Search User by Name

    GET /api/user/name?name=Jo

### 4️⃣ Delete User by ID

    DELETE /api/user/{id}

### 5️⃣ Search by Age Range

    GET /api/user/age?minAge=20&maxAge=40

### 6️⃣ Advanced Search with Pagination

    GET /api/user/search

**Query Params:** - firstName - lastName - city - minAge - maxAge - page
(default: 0) - size (default: 5)

### 7️⃣ Oldest User by City

    GET /api/user/oldestUserByCity

### 8️⃣ Population by City

    GET /api/user/populationByCity

------------------------------------------------------------------------

## 🧪 Testing

-   Import APIs into **Postman**
-   Use Base64-encoded images for photo uploads
-   MongoDB runs on `localhost:27017`

------------------------------------------------------------------------

## ▶️ Run Application

``` bash
mvn clean install
mvn spring-boot:run
```

Application will start at:

    http://localhost:8080

------------------------------------------------------------------------

## ✅ Notes

-   Photo is stored as Base64 string
-   MongoDB collections are auto-created
-   Aggregation queries use MongoDB pipeline

------------------------------------------------------------------------

## 👨‍💻 Author

**Sumit Mittal**

------------------------------------------------------------------------

Happy Coding! 🚀
