# Inventory-Management-System


**A Professional Java Desktop Application with SQLite, Login, Dark Mode & Real-time Stock Alerts**

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.9%2B-green)
![SQLite](https://img.shields.io/badge/Database-SQLite-orange)
![FlatLaf](https://img.shields.io/badge/UI-FlatLaf%20Dark%20Mode-purple)

**Final Release: v3.0** 
**Course: Java + DevOps Project**

---

### Features

- Real **SQLite database** (`inventory.db`) — data survives app restart
- Secure **Login System** (admin / 123)
- Add / Edit / Delete / Search items
- **Color-coded stock levels** in table:
  - **Red** → Low stock (< 10)
  - **Orange** → Medium stock (10–50)
  - **Green** → High stock (> 50)
- **Dark Mode Toggle** — full dark theme (table, buttons, dialogs)
- **Low Stock Report** with **Export to CSV**
- Professional UI with modern buttons and fonts
- Single executable JAR — `java -jar my-app-1.0-SNAPSHOT.jar`
- Built with **Maven** — clean, reproducible build
- Professional Git workflow (feature branches, tags, clean history)

---

### How to Run

```bash
# 1. Download the JAR
# 2. Double-click or run:
java -jar my-app-1.0-SNAPSHOT.jar

--- 

** Project Structure

my-app/
├── pom.xml
├── src/main/java/com/mycompany/app/
│   ├── MainWindow.java
│   ├── InventoryManager.java
│   ├── Database.java (SQLite)
│   ├── LoginDialog.java
│   └── AddEditDialog.java
├── target/my-app-1.0-SNAPSHOT.jar  ← Executable
└── inventory.db                    ← Created on first run


