# Toby.me-BE
![Software system architecture diagram](https://drive.google.com/drive/my-drive)


The components in the system include:
• Nginx - was employed as a proxy server to forward external requests to internal services, effectively concealing the underlying components of the system. Additionally, Nginx served as a load balancer, evenly distributing incoming requests among multiple service instances, safeguarding the system against overloading.

• toby-auth service - processes authorization, handles registration and login, managing user information, and retrieving user information requests. When a user logins, toby-auth handles this request, verifies login information. Then, this service generates access token, and saves it to Redis. In this project, a decentralized authentication model is used, where each service independently authenticates requests by checking with the access token stored in Redis by the toby-auth service. This approach reduces dependencies between services and allows them to operate independently when processing requests.

• toby-be service - handles the core business logic of the system, including requests related to posts, comments, post approval, and following. This service receives and processes user interaction requests, then sends corresponding
messages to the Kafka broker.

• toby-notification service - gets real-time messages from the Kafka broker, then creates notifications, stores them in the database, and sends real-time notifications to users via websocket.

• Kafka - as a real-time stream-processing platform. Instead of synchronous communication between services through APIs, services can interact asynchronously with each other using Kafka as an intermediary.

• Redis - to store access tokens and cache frequently accessed data. By doing so, the number of database queries was reduced, leading to improved system performance.

• Database MySQL - In the system, toby-auth and toby-be services are designed following "Shared database" pattern. They share the same database because their data needs to be closely related and interlinked. This allows them to access and manage data in a cohesive manner. On the other hand, tobynotification service follows a different approach and uses its own database. This decision aligns with the best practices for designing databases in a microservices architecture. Each service maintains its own isolated data store, adhering to the principles of loose coupling and independence between services. By adopting the shared database pattern for toby-auth and toby-be, and the separate database approach for toby-notification, the system achieves a balance between data coherence and service autonomy, ensuring smooth communication and efficient data handling in the microservices system.
