# Library Management System

A full-stack library management system built with **React** (frontend) and **Spring Boot** (backend). The system allows users to manage books, authors, categories, and checkouts, as well as view reports. The project also includes features such as adding, editing, and deleting book entries, along with the ability to track availability and copies.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Overview

The **Library Management System** is a web-based application designed to help manage books, authors, categories, fines, and checkouts. The system uses a dashboard that allows the user to view and manipulate book entries, track available copies, and perform basic CRUD operations. It also includes a report feature to track analytics.

## Features

- Manage Books (Add, Edit, Delete, View)
- Manage Authors and Categories
- View Checkouts and Fines
- Real-time updates after book deletion
- Responsive design for a better user experience
- Data persistence using **H2 Database** (for development)
- Login functionality using **Firebase Authentication**

## Technologies Used

### Frontend:
- **React**: UI development framework
- **Material-UI (MUI)**: For responsive and accessible components
- **React Router**: For routing and navigation
- **Axios**: For making HTTP requests to the backend
- **DataGrid** (MUI): For displaying and managing tabular data

### Backend:
- **Spring Boot**: Java-based backend framework
- **H2 Database**: In-memory database for development
- **JPA (Java Persistence API)**: For data access and persistence
- **Spring Security**: For securing the API
- **Spring Data JPA**: For database interactions

### API and Services:
- **RESTful API**: Provides endpoints for managing books, authors, categories, and checkouts
- **Firebase Authentication**: Used for user login and authentication

## Installation

### Prerequisites:
- **Node.js** and **npm** installed
- **Java Development Kit (JDK)** installed
- **Maven** (for building Spring Boot)

### Steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/library-management-system.git
   cd library-management-system
   ```

2. **Backend Setup**:
   - Go to the backend directory:
     ```bash
     cd backend
     ```
   - Build the Spring Boot application:
     ```bash
     mvn clean install
     ```
   - Run the application:
     ```bash
     mvn spring-boot:run
     ```

3. **Frontend Setup**:
   - Go to the frontend directory:
     ```bash
     cd frontend
     ```
   - Install dependencies:
     ```bash
     npm install
     ```
   - Start the development server:
     ```bash
     npm start
     ```


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

