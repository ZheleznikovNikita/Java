# 📚 Java Labs Collection

> A comprehensive collection of Java laboratory assignments covering fundamental to advanced programming concepts.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)

---

## 📋 Table of Contents

- [Overview](#overview)
- [Repository Structure](#repository-structure)
- [Lab Descriptions](#lab-descriptions)
  - [Lab 1: Basic I/O & Arithmetic](#java-lab-1-basic-io--arithmetic)
  - [Lab 2: Arrays & Conditionals](#java-lab-2-arrays--conditionals)
  - [Lab 3: File I/O Operations](#java-lab-3-file-io-operations)
  - [Lab 4: Advanced Tasks](#java-lab-4-advanced-tasks)
  - [Lab 5: OOP Fundamentals](#java-lab-5-oop-fundamentals)
  - [Lab 6: Domain Modeling](#java-lab-6-domain-modeling)
  - [Lab 7: Advanced OOP Concepts](#java-lab-7-advanced-oop-concepts)
  - [Lab 8: Phone Book Application](#java-lab-8-phone-book-application)
- [Requirements](#requirements)
- [How to Compile and Run](#how-to-compile-and-run)
- [Project Statistics](#project-statistics)
- [License](#license)

---

## 🎯 Overview

This repository contains a comprehensive collection of Java laboratory assignments designed to build and reinforce fundamental programming concepts, from basic input/output operations to advanced object-oriented programming principles.

---

## 📁 Repository Structure

```
/workspace
├── Java_Lab1/          # Basic I/O, arithmetic operations, and simple tasks
│   └── src/            # 9 Java files (Main + 8 tasks)
├── Java_Lab2/          # Arrays, conditionals, and control structures
│   └── src/            # 9 Java files (Main + 8 tasks)
├── Java_Lab3/          # File I/O operations and buffering
│   └── src/            # 10 Java files (Main + 9 tasks)
├── Java_Lab4/          # Advanced programming tasks
│   └── src/            # 8 Java files with complex implementations
├── Java_Lab5/          # OOP fundamentals (models, services, utils)
│   └── src/
│       ├── main/       # Main application entry point
│       ├── models/     # Data models (Student, Employee, Book, etc.)
│       ├── services/   # Business logic services
│       └── utils/      # Utility classes
├── Java_Lab6/          # Domain-specific modeling
│   └── src/
│       ├── bank/       # Banking system simulation
│       ├── geometry/   # Geometric shapes and calculations
│       ├── library/    # Library management system
│       ├── order/      # Order processing system
│       ├── store/      # Store inventory management
│       └── university/ # University management system
├── Java_Lab7/          # Advanced OOP concepts
│   └── src/
│       ├── main/       # Main application
│       ├── models/     # Advanced model implementations
│       ├── services/   # Service layer implementations
│       └── utils/      # Utility functions
└── Java_Lab8/          # Phone Book Application
    └── src/
        └── phonebook/
            ├── main/       # Main application entry point
            ├── models/     # Contact and PhoneBook models
            └── utils/      # Validators and logging utilities
```

---

## 📖 Lab Descriptions

### Java Lab 1: Basic I/O & Arithmetic

**Focus:** Fundamental programming concepts and console I/O

| Task | Description |
|------|-------------|
| 1 | Greeting and user input |
| 2 | Temperature conversion (Celsius ↔ Fahrenheit) |
| 3 | Arithmetic operations on two numbers |
| 4 | Sum of digits calculation |
| 5 | Circle circumference and area calculation |
| 6 | Distance between two points |
| 7 | Time conversion (minutes to hours and minutes) |
| 8 | Number reversal (three-digit numbers) |

### Java Lab 2: Arrays & Conditionals

**Focus:** Control structures, arrays, and conditional logic

| Task | Description |
|------|-------------|
| 1 | Time of day determination |
| 2 | Array element summation |
| 3 | Calculator with division-by-zero protection |
| 4 | Days in month calculator |
| 5 | Finding maximum and minimum values |
| 6 | Counting positive, negative, and zero values |
| 7 | Multiplication table generator |
| 8 | Magic square verification |

### Java Lab 3: File I/O Operations

**Focus:** File reading/writing, buffering, and text processing

| Task | Description |
|------|-------------|
| 1 | Reading and writing bytes |
| 2 | File copying with bufferization |
| 3 | Counting characters, words, and lines in a text file |
| 4 | Word search in file |
| 5 | Console-to-file writing |
| 6 | Reading numbers from file and computing statistics |
| 7 | Caesar cipher for file encryption |
| 8 | Speed comparison (buffered vs non-buffered reading) |
| 9 | *(Bonus)* Downloading web page content |

### Java Lab 4: Advanced Tasks

**Focus:** Complex algorithmic implementations

- Multiple challenging programming tasks with advanced logic
- Comprehensive problem-solving exercises

### Java Lab 5: OOP Fundamentals

**Focus:** Object-Oriented Programming basics

**Structure:**
- **Models:** `Student`, `Employee`, `Book`, `Product`, `BankAccount`
- **Services:** `ShoppingCart` and business logic
- **Utils:** `Time`, `ValidationUtils`

### Java Lab 6: Domain Modeling

**Focus:** Real-world domain simulations with OOP principles

| Domain | Components |
|--------|------------|
| 🏦 **Bank** | Account, Transaction, Customer, BankService |
| 📐 **Geometry** | Circle, Rectangle, Triangle, Shape, AreaCalculator |
| 📚 **Library** | Book, Reader, LibraryCard, LibraryService |
| 📦 **Order** | Order processing system |
| 🏪 **Store** | Inventory and product management |
| 🎓 **University** | Student and course management |

### Java Lab 7: Advanced OOP Concepts

**Focus:** Advanced object-oriented design patterns

**Key Features:**
- Immutable objects (`ImmutableRectangle`)
- Logging systems (`Logger`)
- Database connection handling
- Initialization demonstrations
- Advanced utility classes

### Java Lab 8: Phone Book Application

**Focus:** Practical application with data management and validation

**Structure:**
- **Models:** `Contact`, `PhoneBook` - data structures for contact management
- **Utils:** `PhoneNumberValidator`, `ActionLogger`, `Checker` - validation and logging utilities
- **Main:** Application entry point with interactive phone book functionality

---

## ⚙️ Requirements

| Requirement | Version |
|-------------|---------|
| **JDK** | Version 8 or higher |
| **IDE** | IntelliJ IDEA (recommended) or any Java-compatible editor |

### Installation

```bash
# Verify Java installation
java -version
javac -version
```

---

## 🚀 How to Compile and Run

### Option 1: Command Line

```bash
# Navigate to the desired lab directory
cd Java_Lab1/src

# Compile all Java files
javac *.java

# Run the main program
java Main
```

### Option 2: IntelliJ IDEA

1. Open IntelliJ IDEA
2. Select **File → Open** and choose the lab directory (e.g., `Java_Lab1`)
3. Wait for project indexing to complete
4. Right-click on `Main.java` and select **Run 'Main.main()'**

### Option 3: Quick Run (for Labs 1-4)

```bash
# One-liner to compile and run
cd Java_Lab1/src && javac *.java && java Main
```

---

## 📊 Project Statistics

| Metric | Value |
|--------|-------|
| **Total Java Files** | 107 |
| **Lab Directories** | 8 |
| **Primary Language** | Java |
| **Code Comments** | Russian |
| **Project Structure** | Modular (by lab/task) |

### File Distribution

| Lab | Java Files | Focus Area |
|-----|------------|------------|
| Lab 1 | 9 | Basic I/O |
| Lab 2 | 9 | Arrays & Logic |
| Lab 3 | 10 | File Operations |
| Lab 4 | 8 | Advanced Algorithms |
| Lab 5 | 9 | OOP Basics |
| Lab 6 | 43 | Domain Models |
| Lab 7 | 13 | Advanced OOP |
| Lab 8 | 6 | Phone Book Application |

---

## 📝 License

This project is created for **educational purposes**.

---

## 👨‍💻 Contributing

Feel free to explore the code, experiment with modifications, and use these labs as a learning resource for Java programming fundamentals.

---

<div align="center">

**Happy Coding!** 💻☕

Made with ❤️ using Java

</div>
