# User API

This API allows you to manage users (retrieve and create) using HTTP requests. Below are the examples of how to make requests to the API.

## Endpoints

### 1. Get

- **Method:** GET
- **URL:** `/user-api-1.0-SNAPSHOT/user`

**Request Example:**


      GET "http://localhost:8080//user-api-1.0-SNAPSHOT/user"
      GET "http://localhost:8080//user-api-1.0-SNAPSHOT/user?id=1"

### 2. POST

- **Method:** POST
- **URL:** `/user-api-1.0-SNAPSHOT/user`
- **Content-Type:** `application/json`

**Request Example:**

      POST "http://localhost:8080/user-api-1.0-SNAPSHOT/user" \

{
    "name": "test",
    "surname": "test1",
    "email": "test@example.com",
    "phone": "+1234567890"
}'
