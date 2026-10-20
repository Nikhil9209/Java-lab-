# Experiment 4: Server-Client Basics (TCP Sockets)

## 📌 Overview
This experiment illustrates fundamental **TCP Network Socket Programming** in Java using the `java.net` package. It demonstrates establishing a reliable, connection-oriented byte-stream communication channel between a multi-endpoint Server and Client over port `5000`.

---

## 🏗️ TCP Communication Lifecycle

```
    Server (server.java)                               Client (client.java)
┌───────────────────────────┐                      ┌───────────────────────────┐
│ ServerSocket(5000)        │                      │                           │
│ serverSocket.accept()     │ ◄── SYN / Connect ── │ Socket("localhost", 5000) │
│ (Blocks until client joins)                      │                           │
│                           │                      │                           │
│ input.readLine()          │ ◄── "Hello Server" ─ │ output.println(...)       │
│ output.println(...)       │ ─── "Hello Client" ─►│ input.readLine()          │
│                           │                      │                           │
│ socket.close()            │                      │ socket.close()            │
│ serverSocket.close()      │                      │                           │
└───────────────────────────┘                      └───────────────────────────┘
```

### Components
1. **`server.java`**:
   - Creates a listening socket on TCP port `5000` via `ServerSocket(5000)`.
   - Blocks at `accept()` awaiting an inbound client connection request.
   - Reads the client message via `BufferedReader(new InputStreamReader(socket.getInputStream()))`.
   - Responds with `"Hello Client"` via `PrintWriter(socket.getOutputStream(), true)`.
   - Closes the active client session and server socket gracefully.
2. **`client.java`**:
   - Initiates a three-way TCP handshake to `localhost:5000` via `Socket("localhost", 5000)`.
   - Sends `"Hello Server"` to the server.
   - Reads the server's reply and prints it to the console.

---

## 🚀 How to Compile and Run

### Prerequisites
- JDK 17 or higher.

Open **two separate terminals** in the experiment directory:

```bash
cd Experiments/Exp-04-Server-Client-Basics

# Compile both files:
javac server.java client.java
```

### Terminal 1: Run the Server First
```bash
java server
```
*Expected Output:*
```
Server Started....
Waiting For Client.....
```

### Terminal 2: Run the Client
```bash
java client
```
*Terminal 2 Output:*
```
Server: Hello Client
```

*Terminal 1 Output Updates to:*
```
Client: Hello Server
```

---

## 💡 Key Concepts Covered
- **TCP Stream Sockets**: Reliable byte stream transmission via `java.net.Socket` and `java.net.ServerSocket`.
- **I/O Buffering**: Wrapping raw network streams with `InputStreamReader`, `BufferedReader`, and auto-flushing `PrintWriter`.
- **Blocking I/O**: Understanding the synchronous blocking nature of `accept()` and `readLine()`.
- **Resource Management**: Safe closure of network sockets and stream endpoints.
