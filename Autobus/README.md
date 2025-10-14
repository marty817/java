
# Spring Bus App

Minimal Spring Boot application that manages users and bus trips with JWT authentication and roles.

## How to run

Requires Java 17 and Maven.

```bash
mvn spring-boot:run
```

H2 console: http://localhost:8080/h2-console (jdbc:h2:mem:db)

Pre-created users:
- admin@example.com / adminpass (ROLE_ADMIN)
- user@example.com / userpass (ROLE_USER, credit 50.00)

## Endpoints (summary)

POST /register?admin=false    - register (public)
POST /login                   - login (public)
GET  /me                      - authenticated user info
PATCH /me/credit/toup         - top up own credit (authenticated)
GET  /trips                   - list trips (public)
POST /trips/{tripId}/buy      - buy trip (authenticated)
POST /trips                   - create trip (admin only)
GET  /users/{id}              - get user public info
PATCH /users/{id}/credit/toup - top up user credit (exists for Phase1)

