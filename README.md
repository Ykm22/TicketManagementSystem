# Ticket Management System
Developed during Endava's 2023 Summer Apprenticeship

# Description
Application offers available actions for customers and admins </br>
* Customers
  * Paged events view, with filtering options by up to 3 criterias
  * Order place, modify and delete
  * Orders sort option by one of two available criterias, ordered ascending or descending
* Admins
  * Sales across the years graph view, with option to predict rest of the current year using an AI model
  * Percentage view of group ages which have placed orders for an event type, with option to predict future percentages for that event type using an AI model

### Details
Security managed using a JWT created on login and passing it in requests.</br>
Tickets availability for order placing/modifying made concurrent safe in order for customers not to access unavailable tickets.</br>

## Front-end
* Vanilla JS with Vite
* Android

## Back-end
* Spring
* .NET - Concurrency management using ADO.NET
* Flask - Analytics generation
* NodeJS
