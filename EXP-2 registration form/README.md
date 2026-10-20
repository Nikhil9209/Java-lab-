# Experiment 2: Student Registration Form (Java Swing)

## 📌 Overview
This experiment implements an interactive desktop **Student Registration Form** application built using **Java Swing** and **AWT Event Handling**. It captures student credentials, enforces validation rules, provides modal alerts, and handles dynamic reset/submission flows.

---

## 🎨 UI Components & Controls Used

| Component | Swing Class | Purpose in Application |
| :--- | :--- | :--- |
| **Window Frame** | `JFrame` | Main 400x350 window titled "Student Registration Form" |
| **Labels** | `JLabel` | Field labels for Name, Roll No, Branch, and Gender |
| **Text Fields** | `JTextField` | Single-line text input for student attributes |
| **Radio Buttons** | `JRadioButton` | Mutually exclusive gender selection (Male / Female) |
| **Button Group** | `ButtonGroup` | Logical grouping ensuring only one radio button is selected |
| **Check Box** | `JCheckBox` | Mandatory "Terms & Conditions" agreement confirmation |
| **Action Buttons** | `JButton` | Submit and Reset operations |
| **Dialog Boxes** | `JOptionPane` | Modal alert dialogs for errors and success summary |

---

## ⚙️ Validation Rules Implemented
1. **Mandatory Text Fields**: Name, Roll Number, and Branch cannot be empty.
2. **Gender Selection**: One radio button must be active.
3. **Terms Agreement**: The Terms & Conditions checkbox must be checked.
4. **Reset Functionality**: Clears all input text fields, unselects radio buttons, and unchecks terms.

---

## 🚀 How to Compile and Run

### Prerequisites
- JDK 17 or higher installed.

### Execution Steps
```bash
# Navigate to the experiment directory:
cd Experiments/Exp-02-Registration-Form

# Compile the source file:
javac aniket.java

# Run the GUI application:
java aniket
```

---

## 💡 Key Concepts Covered
- **Layout Management**: Absolute positioning with `setLayout(null)` and `setBounds()`.
- **Event Delegation**: Registering `btnSubmit.addActionListener(this)` and `btnReset.addActionListener(this)`.
- **State Inspection**: Using `isSelected()`, `getText().isEmpty()`, and `genderGroup.clearSelection()`.
- **User Feedback**: Standard Swing popups via `JOptionPane.showMessageDialog()`.
