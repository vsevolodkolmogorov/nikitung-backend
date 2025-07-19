### 🏷️ Type: Personal Project

# NIKITUNG — MVP for Rating Swimming Spots

## 📌 Overview

**NIKITUNG** is an MVP platform where users can **discover**, **rate**, and **discuss** swimming spots — lakes, rivers, beaches, and more.

This is the **first working version** of the project. It’s designed using **microservice architecture** and will continue to evolve over time.

## 🧩 Architecture

The project is split into 8 microservices:

| Service            | Description |
|---------------------|-------------|
| **config-service**  | Centralized configuration service (Spring Cloud Config) |
| **gateway-service** | API Gateway for routing and CORS handling |
| **eureka-server**   | Service discovery (Netflix Eureka) |
| **auth-service**    | Authentication and authorization (JWT-based) |
| **user-service**    | Manages users |
| **place-service**   | Handles creation and storage of swimming spots |
| **rating-service**  | Manages rating logic for each place |
| **comment-service** | Manages user comments and reviews for each place |

## 🛠️ Tech Stack

- Java 17 + Spring Boot
- Spring Cloud (Gateway, Config, Eureka)
- PostgreSQL
-  React (generated with v0.dev)
-  Feign + WebClient
-  JWT (security)
-  Docker & Docker Compose

## 🚀 Features

### auth-service
- User authorization and login functionality

### place-service
- Display list of swimming spots with sorting and search
- Create new swimming spots
- View detailed information of a spot by ID, including average ratings and user comments

### rating-service
- Submit and manage ratings for swimming spots using a 5-point scale
- Additional parameters and descriptions for detailed rating

### comment-service
- Add and manage user comments and reviews for swimming spots

### user-service
- Display general user information

## ▶️ How to Run

### 1. 📄 Environment Variables

You must configure the following environment files:

- `.env` — used by Docker Compose
- `.env.local` — used for local development

These contain required variables for service communication and database access.

### 2. 🖥️ Local Development

To run locally, it is recommended to use the **`.envFile` plugin** (e.g. in IntelliJ IDEA or VS Code).  
This allows automatic injection of variables from `.env.local`.

You can start each service from your IDE using its main class.

### 3. 🐳 Docker Compose

To run the entire platform with Docker Compose:

```bash
docker-compose up --build
```

Make sure .env is correctly configured in the root directory before launching.
