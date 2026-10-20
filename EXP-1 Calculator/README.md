# Experiment 1: Swing Calculator (MVC Architecture)

## 📌 Overview
This experiment demonstrates the **Model-View-Controller (MVC)** architectural pattern in Java desktop applications using **Java Swing** and event-driven programming.

Separating business logic from UI rendering ensures clean, modular, and maintainable code.

---

## 🏗️ Architecture & Component Design

The project is structured according to the classic MVC pattern:

```
[ User Action: Button Click ]
          │
          ▼
   ┌─────────────┐       Calls Logic       ┌─────────────┐
   │ Controller  │ ──────────────────────> │    Model    │
   │(Ccontroller)│                         │  (Cmodels)  │
   └─────────────┘                         └─────────────┘
          │                                       │
          │ Updates UI with result                │ Returns calculated
          ▼                                       ▼ double value
   ┌─────────────┐
   │    View     │
   │  (Cviews)   │
   └─────────────┘
```

1. **Model (`Cmodels.java`)**:
   - Contains pure arithmetic business logic without any GUI dependencies.
   - Methods: `add(a, b)`, `subtract(a, b)`, `multiply(a, b)`, `divide(a, b)` (with zero-division protection), and `percentage(a, b)`.
2. **View (`Cviews.java`)**:
   - Extends `javax.swing.JFrame` to render a 400x300 desktop window.
   - Components: Input fields (`txta`, `txtb`), result field (`txtresult`), labels, and operation buttons (`Add`, `Sub`, `Mul`, `Div`, `%`).
3. **Controller (`Ccontroller.java`)**:
   - Implements `java.awt.event.ActionListener`.
   - Listens for button events, parses string inputs to doubles, invokes the appropriate model method, and updates the view.
4. **Main Entry Point (`Main.java`)**:
   - Initializes the MVC triad: creates Model and View instances, and binds them inside Controller.

---

## 🚀 How to Compile and Run

### Prerequisites
- JDK 17 or higher installed.

### Option 1: Run from within this experiment folder
```bash
cd Experiments/Exp-01-Calculator

# Compile placing classes into package hierarchy:
javac -d . *.java

# Run the application:
java Calculator.Main
```

### Option 2: Run from the repository root
```bash
# Compile
javac -d . Experiments/Exp-01-Calculator/*.java

# Run
java Calculator.Main
```

---

## 💡 Key Concepts Covered
- **Java Swing GUI Components**: `JFrame`, `JLabel`, `JTextField`, `JButton`.
- **Absolute Layout Management**: Utilizing `setLayout(null)` and `setBounds(x, y, width, height)`.
- **Event-Driven Architecture**: `ActionListener` registration and `actionPerformed(ActionEvent e)` handling.
- **MVC Design Pattern**: Strict decoupling of data/logic (`Model`) from user interface (`View`).
