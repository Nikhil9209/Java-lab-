# Experiment 3: Java Remote Method Invocation (RMI)

## 📌 Overview
This experiment demonstrates **Java Remote Method Invocation (RMI)**, a distributed computing paradigm that allows a Java program on one JVM to seamlessly invoke methods on an object residing in another JVM across a network.

---

## 🏗️ Architecture & Distributed Flow

```
   ┌─────────────────────────────────────────────────────────────┐
   │                    RMI Registry (Port 1099)                 │
   └──────────────────────────────┬──────────────────────────────┘
                                  ▲
        Naming.lookup()           │   Naming.rebind()
        ("rmi://localhost/...")   │   ("rmi://localhost/Calculator", obj)
                                  │
    ┌─────────────────────────┐   │   ┌───────────────────────────┐
    │       RMI Client        │───┴───│        RMI Server         │
    │      (Client.java)      │       │       (Server.java)       │
    └─────────────────────────┘       └───────────────────────────┘
                 │                                  ▲
                 │     Remote Method Invocation     │
                 └──────────────────────────────────┘
                      obj.add(10, 20) -> 30
```

### Components
1. **`Calculator.java` (Remote Interface)**:
   - Extends `java.rmi.Remote`.
   - Declares distributed methods: `add(int a, int b)` and `subtract(int a, int b)`.
   - Every method must throw `java.rmi.RemoteException`.
2. **`CalculatorImpl.java` (Remote Object Implementation)**:
   - Extends `java.rmi.server.UnicastRemoteObject` and implements `Calculator`.
   - Listens on an anonymous ephemeral port and dispatches remote calls.
3. **`Server.java` (Server Host)**:
   - Instantiates `CalculatorImpl`.
   - Registers the remote reference in `rmiregistry` under the lookup key `"rmi://localhost/Calculator"`.
4. **`Client.java` (Client Invoker)**:
   - Uses `Naming.lookup()` to obtain the remote stub.
   - Executes remote method calls transparently and displays the return values.

---

## 🚀 How to Compile and Run

### Prerequisites
- JDK 17 or higher.

Open **3 separate terminals** in the experiment directory:

```bash
cd Experiments/Exp-03-RMI

# Compile all source files in any terminal:
javac *.java
```

### Terminal 1: Start RMI Registry
On Windows:
```cmd
start rmiregistry
```
*(On Linux/macOS: `rmiregistry &`)*

### Terminal 2: Start RMI Server
```cmd
java Server
```
*Expected Output:*
```
RMI service started...
```

### Terminal 3: Execute RMI Client
```cmd
java Client
```
*Expected Output:*
```
Addition: 30
Subtraction: 10
```

---

## 💡 Key Concepts Covered
- **Remote Interfaces**: Contracts declaring networked functionality.
- **UnicastRemoteObject**: Point-to-point export of remote objects using TCP.
- **RMI Registry / Naming Service**: Distributed name-to-object lookup mechanism on default port 1099.
- **Remote Exception Handling**: Robust handling of network dropouts and serialization errors (`RemoteException`).
