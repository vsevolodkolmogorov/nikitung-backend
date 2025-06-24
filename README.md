# 🐬 NIKITUNG — MVP for Rating Swimming Spots

## 📌 Overview

**NIKITUNG** is an MVP platform where users can **discover**, **rate**, and **discuss** swimming spots — lakes, rivers, beaches, and more.

This is the **first working version** of the project. It’s designed using **microservice architecture** and will continue to evolve over time.

---

## 🧩 Architecture

The project is split into 8 microservices:

- `CONFIG` — centralized configuration service (Spring Cloud Config)
- `GATEWAY` — API gateway for routing and CORS handling
- `EUREKA` — service discovery (Netflix Eureka)
- `AUTH` — authentication and authorization (JWT-based)
- `USER` — user management service
- `PLACE` — handles creation and storage of swimming spots
- `RATING` — manages rating logic for each place
- `COMMENT` — user comments and reviews per place

---

## 🚀 Running the Project

### 🛠️ Tech Stack

- Java 17 + Spring Boot
- Spring Cloud (Gateway, Config, Eureka)
- PostgreSQL
-  React (generated with v0.dev)
-  Feign + WebClient
-  JWT (security)
-  Docker & Docker Compose

### 1. 📄 Environment Variables

You must configure the following environment files:

- `.env` — used by Docker Compose
- `.env.local` — used for local development

These contain required variables for service communication and database access.

---

### 2. 🖥️ Local Development

To run locally, it is recommended to use the **`.envFile` plugin** (e.g. in IntelliJ IDEA or VS Code).  
This allows automatic injection of variables from `.env.local`.

You can start each service from your IDE using its main class.

---

### 3. 🐳 Docker Compose

To run the entire platform with Docker Compose:

```bash
docker-compose up --build
```

Make sure .env is correctly configured in the root directory before launching.
