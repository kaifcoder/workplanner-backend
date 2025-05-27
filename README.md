# WorkPlanner - Project Management System

WorkPlanner is a Spring Boot-based backend for a project management system. It provides RESTful APIs for managing projects, tasks, users, reporting, and includes features like email notifications, JWT-based security, and role-based access control.

## Features
- Project CRUD (create, update, delete, get)
- Task CRUD (create, update, delete, assign, approve, reject, suggest)
- Team member and manager roles with access control
- Email notifications for task assignment, updates, and suggestions (HTML, async)
- JWT authentication and authorization
- CORS and security configuration
- MySQL database with referential integrity
- Environment variable support for sensitive configuration
- Docker and Docker Compose support for easy deployment

## Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL (or use Docker Compose)
- Docker (optional, for containerized deployment)

### Running Locally (Dev)
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/workplanner.git
   cd workplanner
   ```
2. Configure your database and mail settings in `src/main/resources/application.properties` or `.env`.
3. Run the application:
   ```sh
   ./mvnw spring-boot:run
   ```

### Running with Docker
1. Build the Docker image:
   ```sh
   docker build -t workplanner-app:latest .
   ```
2. Run the container (replace env values as needed):
   ```sh
   docker run -p 8080:8080 \
     -e SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/workplanner?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" \
     -e SPRING_DATASOURCE_USERNAME="workplanner" \
     -e SPRING_DATASOURCE_PASSWORD="workplannerpass" \
     -e JWT_SECRET="your_jwt_secret_here" \
     -e SPRING_MAIL_HOST="smtp.example.com" \
     -e SPRING_MAIL_USERNAME="your_email@example.com" \
     -e SPRING_MAIL_PASSWORD="your_email_password" \
     workplanner-app:latest
   ```

### Running with Docker Compose
1. Edit `docker-compose.yml` to set your environment variables (DB, JWT, mail, etc.).
2. Start the stack:
   ```sh
   docker-compose up --build
   ```

## API Documentation
- The API is RESTful and follows standard conventions.
- Use tools like Postman or Swagger UI to explore endpoints.
- Example endpoints:
  - `/api/projects` (CRUD)
  - `/api/tasks` (CRUD, assign, approve, reject, suggest)
  - `/api/reports` (manager/team reports)

## Environment Variables
Sensitive configuration (DB, mail, JWT) should be set via environment variables or a `.env` file. See `docker-compose.yml` for examples.

## Security
- JWT-based authentication
- Role-based access control (manager, team member)
- CORS configuration for frontend integration

## Deployment
- Supports Docker and Docker Compose for local or cloud deployment
- For Cloud Foundry, build and push the Docker image, provision a MySQL service, and use a `manifest.yml`

## License
MIT

---

**Contributions welcome!**
