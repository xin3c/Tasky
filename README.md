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
    
    ```bash
       git clone https://github.com/yourusername/tasky.git cd tasky
    ```
    
2. **Set up the database**
    
    - Create a MySQL database named `tasky_db`.
        
    - Update the `application.properties` file with your database credentials.
        
        ```
        spring.datasource.url=jdbc:mysql://localhost:3306/tasky_db?useSSL=false&serverTimezone=UTC
        spring.datasource.username=your_mysql_username
        spring.datasource.password=your_mysql_password
        ```
        
3**Build the project**
    ```bash
       mvn clean install
    ```
4**Run the application**
    
    ```bash
       mvn spring-boot:run
    ```
    
5. **Access the application**
    
    - Open your browser and navigate to `http://localhost:8080`.

## Usage

1. **Register a new account**
    
    - Click on the "Register" link on the homepage.
    - Fill in your username, email, and password.
2. **Log in**
    
    - Use your credentials to log in.
3. **Manage categories**

    - Navigate to the "Categories" page.
    - Create new categories and assign tasks to them.
4. **Create tasks**

    - Navigate to the "Tasks" page.
    - Click on "Create New Task" and fill in the details.


## Project Structure

- **src/main/java/com/tasky**
    - **models**: Entity classes (User, Task, Category).
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


## License

This project is licensed under the MIT License - see the LICENSE file for details.


## Contact

Feel free to reach out for any questions or suggestions:

- **Email**: vkupin@neoflex.ru
- **GitHub**: [xin3c](https://github.com/xin3c)
