# SHOPSPHERE - Viva Examination & Demonstration Defense Guide
**Rajasthan Technical University (RTU) Advanced Java Practical Examination**

This comprehensive guide prepares students to ace every technical question and demonstration requested by the external examiner.

---

## 1. Top Viva Questions & Model Answers

### Q1: What is the N-Tier Architecture implemented in ShopSphere?
**Answer:**  
ShopSphere implements a 5-tier enterprise architecture:
1. **Client Tier:** Modern Web Browser (HTML5/CSS3/JavaScript) and Java Swing Desktop GUI.
2. **Presentation / Web Tier:** Apache Tomcat 11 Web Container hosting Jakarta Servlets (Controllers) and JavaServer Pages (Views).
3. **Business Logic / Service Tier:** Services (`CheckoutService`, `ProductService`, `OrderService`, `UserService`) encapsulating enterprise business rules, server-side price validation, and coupon rules.
4. **Data Access Layer (DAO Tier):** DAO classes (`ProductDAO`, `OrderDAO`, `UserDAO`, etc.) interfacing with the database through JDBC.
5. **Database Tier:** MySQL/MariaDB database maintaining ACID relational integrity.

---

### Q2: Explain the Servlet Lifecycle and where it is demonstrated.
**Answer:**  
The Servlet lifecycle is managed by the Tomcat Web Container through 4 distinct stages:
1. **Loading & Instantiation:** Container loads the Servlet byte-code and instantiates an instance.
2. **Initialization (`init(ServletConfig config)`):** Invoked once before servicing requests. Demonstrated in [LoginServlet.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/java/com/shopsphere/controller/LoginServlet.java) where `super.init(config)` and services are instantiated.
3. **Request Servicing (`service()` / `doGet()` / `doPost()`):** For every HTTP request, a new thread executes `service()`, dispatching to `doGet()` or `doPost()`.
4. **Destruction (`destroy()`):** Invoked prior to undeployment or server shutdown to release resources. Demonstrated with clean logging in [LoginServlet.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/java/com/shopsphere/controller/LoginServlet.java).

---

### Q3: How do Filters differ from Servlets, and how are they used in ShopSphere?
**Answer:**  
A Servlet handles request processing and generates a response, whereas a **Filter** intercepts requests dynamically before they reach the Servlet, or intercepts responses before they reach the client.
- **[AuthenticationFilter.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/java/com/shopsphere/filter/AuthenticationFilter.java):** Intercepts routes like `/cart`, `/checkout`, `/orders`, `/profile`, `/wishlist`. Checks if `request.getSession(false).getAttribute("user") != null`. If missing, redirects to `/login`.
- **[AdminFilter.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/java/com/shopsphere/filter/AdminFilter.java):** Intercepts `/admin/*`. Validates that the logged-in user possesses the `ADMIN` role, otherwise throwing `403 Forbidden`.

---

### Q4: How is an ACID Transaction implemented in JDBC?
**Answer:**  
Demonstrated in [OrderDAO.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/shopsphere-common/src/main/java/com/shopsphere/dao/OrderDAO.java) inside `placeOrder()`:
1. Disable auto-commit: `conn.setAutoCommit(false);`
2. Insert order header into `orders` table.
3. Batch insert items into `order_items` table.
4. Batch decrement inventory stock in `products` table (`stock = stock - ?`).
5. Insert transaction record into `payments` table.
6. Clear customer cart: `DELETE FROM cart WHERE user_id = ?`.
7. **Commit:** `conn.commit();`
8. **Rollback on Exception:** If any `SQLException` or stock violation occurs in any step, `conn.rollback()` is immediately invoked in the `catch` block to revert the database to its exact prior state.

---

### Q5: What is Java Remote Method Invocation (RMI), and how does ShopSphere demonstrate it?
**Answer:**  
RMI enables an object running in one Java Virtual Machine (JVM) to invoke methods on an object running in another JVM across a network.
- **Remote Interface:** [InventoryService.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/rmi/src/main/java/com/shopsphere/rmi/remote/InventoryService.java) extends `java.rmi.Remote`. Every method declares `throws RemoteException`.
- **Remote Implementation:** [InventoryServiceImpl.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/rmi/src/main/java/com/shopsphere/rmi/server/InventoryServiceImpl.java) extends `UnicastRemoteObject`.
- **RMI Registry:** [RmiServer.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/rmi/src/main/java/com/shopsphere/rmi/server/RmiServer.java) starts `LocateRegistry.createRegistry(1099)` and registers the stub using `Naming.rebind("rmi://localhost:1099/InventoryService", service)`.
- **RMI Client:** [RmiClient.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/rmi/src/main/java/com/shopsphere/rmi/client/RmiClient.java) looks up `Naming.lookup(...)` to obtain a remote proxy stub and invokes remote methods (`getProduct`, `getStock`, `updateStock`).

---

### Q6: How is Java Object Serialization used in the java.net Socket module?
**Answer:**  
- The model classes [Product.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/shopsphere-common/src/main/java/com/shopsphere/model/Product.java) and [NetworkMessage.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/shopsphere-common/src/main/java/com/shopsphere/model/NetworkMessage.java) implement `java.io.Serializable` and declare `private static final long serialVersionUID = 1L;`.
- The [InventorySocketServer.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/network/src/main/java/com/shopsphere/network/server/InventorySocketServer.java) uses `ObjectOutputStream` and `ObjectInputStream` wrapped around client sockets.
- Objects are converted into a binary byte stream on the sender side and reconstituted into memory on the receiver side without manual string parsing.

---

### Q7: Explain the difference between JSP Tag Files and Classic Tag Handlers.
**Answer:**  
- **Tag Files (`.tag`):** Introduced in JSP 2.0. Written in standard JSP/HTML syntax and placed in `/WEB-INF/tags/`. Demonstrated in [productCard.tag](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/webapp/WEB-INF/tags/productCard.tag) and [rating.tag](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/webapp/WEB-INF/tags/rating.tag).
- **Classic Tag Handlers:** Java classes extending `SimpleTagSupport` or `TagSupport`, registered in a `.tld` XML descriptor. Demonstrated in [RatingTag.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/java/com/shopsphere/tag/RatingTag.java) and [shopsphere.tld](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/webapp/WEB-INF/tlds/shopsphere.tld).

---

### Q8: How is Internationalization (I18N) supported?
**Answer:**  
Demonstrated in [I18nUtil.java](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/shopsphere-common/src/main/java/com/shopsphere/util/I18nUtil.java), [messages_en.properties](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/resources/messages_en.properties), and [messages_hi.properties](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/resources/messages_hi.properties).  
The user can toggle between English and Hindi in the navbar dropdown. The `Locale` is saved in `session.getAttribute("lang")`, dynamically formatting currency in Indian Rupees (`₹`) and displaying localized text strings.

---

### Q9: Why are Applets obsolete, and how does ShopSphere demonstrate them?
**Answer:**  
Applets relied on browser plugins (NPAPI) to execute JVM byte-code on the client machine. Major browsers (Chrome, Firefox, Edge) removed NPAPI support due to security vulnerabilities, sandboxing limitations, and mobile incompatibility.  
In ShopSphere, [syllabus-demo.jsp](file:///c:/Users/nikhi/Desktop/JAVA%20LAB/Shopsphere/web/src/main/webapp/syllabus-demo.jsp) provides an authentic, high-speed HTML5 Canvas double-buffered emulation that runs the exact animation loop and graphical render pass `paint(Graphics g)` of a classic Java Applet, explaining the `init()`, `start()`, `stop()`, and `destroy()` lifecycle.

---

## 2. Live Examiner Demonstration Script

Follow these steps during your viva examination to demonstrate every syllabus module live:

1. **Open the Storefront in Browser:**
   - URL: `http://localhost:8080/ShopSphere/`
   - Point out: Tomcat 11 web container, Bootstrap responsive grid, JSP fragments (header, navbar, footer).
2. **Open the RTU Syllabus Demonstration Lab:**
   - Click "RTU Syllabus Lab" in the top bar or visit `http://localhost:8080/ShopSphere/syllabus-demo`.
   - Show:
     - Tab 1: JSP scripting elements (`<%! %>`, `<% %>`, `<%= %>`) and JSTL Core.
     - Tab 2: Canvas Applet lifecycle simulation.
     - Tab 3: JSTL XML parsing of `products-feed.xml` (`<x:parse>`, `<x:out>`).
     - Tab 4: JNDI lookup output and java.net URLConnection header inspector.
3. **Demonstrate Customer Shopping & Transaction:**
   - Login as `nikhil@shopsphere.com` / `password123`.
   - Add product to cart, apply coupon `WELCOME50` (50% off discount).
   - Proceed to checkout, select payment method, click "Confirm & Place Order".
   - Show the generated order tracking timeline and invoice.
4. **Demonstrate Desktop Administration in Swing:**
   - Run `run-swing-admin.bat`.
   - Login as `admin@shopsphere.com` / `admin123`.
   - Show Pluggable Look and Feel, JTable, Restock button (+50 units).
5. **Demonstrate java.net Socket Client-Server:**
   - Double-click `run-socket-server.bat` in one window.
   - Run `run-socket-client.bat` in another window.
   - Point out TCP connection, `NetworkMessage` serialization, and deserialization of `Product` objects.
6. **Demonstrate Java RMI:**
   - Double-click `run-rmi-server.bat` (Registry on port 1099).
   - Run `run-rmi-client.bat` to see remote execution of `getProduct()` and `updateStock()`.
