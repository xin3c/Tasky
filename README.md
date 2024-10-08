# Tasky

**Tasky** is a simple and intuitive task management application built with Java and Spring Boot. It allows users to efficiently manage their tasks, categorize them, and track their completion status.

## Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)
- [Contact](#contact)

## Features

- **User Authentication**
    - Secure user registration and login using Spring Security.
    - Passwords are encrypted using BCrypt.
- **Task Management**
    - Create, edit, and delete tasks.
    - Mark tasks as completed or not completed.
    - Set due dates for tasks.
- **Task Categories**
    - Create custom categories.
    - Assign tasks to categories.
    - Filter tasks by category.
- **Task Display**
    - Filter tasks by status.
    - Sort tasks by creation date or due date.
- **Responsive UI**
    - Clean and modern user interface.
    - Mobile-friendly design.

## Screenshots

_Note: Include actual screenshots of your application here._

_Manage all your tasks in one place._

_Easily create new tasks with due dates and categories._

## Getting Started

### Prerequisites

- **Java 17**
- **Maven 3.6+**
- **MySQL Database**

### Installation

1. **Clone the repository**
    
    bash
    
    Копировать код
    
    `git clone https://github.com/yourusername/tasky.git cd tasky`
    
2. **Set up the database**
    
    - Create a MySQL database named `tasky_db`.
        
    - Update the `application.properties` file with your database credentials.
        
        properties
        
        Копировать код
        
        `spring.datasource.url=jdbc:mysql://localhost:3306/tasky_db?useSSL=false&serverTimezone=UTC spring.datasource.username=your_mysql_username spring.datasource.password=your_mysql_password`
        
3. **Build the project**
    
    bash
    
    Копировать код
    
    `mvn clean install`
    
4. **Run the application**
    
    bash
    
    Копировать код
    
    `mvn spring-boot:run`
    
5. **Access the application**
    
    - Open your browser and navigate to `http://localhost:8080`.

## Usage

1. **Register a new account**
    
    - Click on the "Register" link on the homepage.
    - Fill in your username, email, and password.
2. **Log in**
    
    - Use your credentials to log in.
3. **Create tasks**
    
    - Navigate to the "Tasks" page.
    - Click on "Create New Task" and fill in the details.
4. **Manage categories**
    
    - Navigate to the "Categories" page.
    - Create new categories and assign tasks to them.
5. **Filter and sort tasks**
    
    - Use the filter options to view tasks by category or status.
    - Sort tasks by creation date or due date.

## Project Structure

- **src/main/java/com/tasky**
    - **models**: Entity classes (`User`, `Task`, `Category`).
    - **repositories**: Interfaces for database operations.
    - **services**: Business logic for handling operations.
    - **controllers**: Request handling and routing.
    - **config**: Configuration classes (e.g., `WebSecurityConfig`).
- **src/main/resources**
    - **templates**: Thymeleaf HTML templates.
    - **static**: Static assets (CSS, JS).
    - **application.properties**: Application configuration.
- **pom.xml**: Project dependencies and build configuration.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **Thymeleaf**
- **MySQL**
- **Lombok**
- **Maven**

## Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
    
2. **Create a new branch**
    
    bash
    
    Копировать код
    
    `git checkout -b feature/your-feature-name`
    
3. **Make your changes**
    
4. **Commit your changes**
    
    bash
    
    Копировать код
    
    `git commit -am 'Add new feature'`
    
5. **Push to the branch**
    
    bash
    
    Копировать код
    
    `git push origin feature/your-feature-name`
    
6. **Create a new Pull Request**
    

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Thanks to the [Spring](https://spring.io/) community for their excellent resources and support.
- Inspired by the need for a simple and efficient task management tool.

## Contact

Feel free to reach out for any questions or suggestions:

- **Email**: your.email@example.com
- **GitHub**: [yourusername](https://github.com/yourusername)
