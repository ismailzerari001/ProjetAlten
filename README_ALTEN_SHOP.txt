
====================
ALTEN SHOP - README
====================

Technologies utilisées
-------------------------

Backend – Spring Boot:
- Java 17
- Spring Boot 
- Spring Security (JWT)
- Spring Web
- JSON File DB (mock)
- Maven

Frontend – Angular:
- Angular 18
- PrimeNG
- Angular Router & Forms
- JWT (localStorage)

Backend - API Principales
--------------------------
POST    /account         : Créer un compte utilisateur
POST    /token           : Authentifier (JWT)
GET     /products        : Lister les produits
POST    /products        : Créer un produit (admin)
PUT     /products/{id}   : Modifier un produit (admin)
DELETE  /products/{id}   : Supprimer un produit (admin)

Connexion Admin par défaut
--------------------------
Email    : admin@admin.com
Password : admin

Frontend - Pages Angular
-------------------------
- /login     : Connexion
- /register  : Inscription
- /products  : Liste des produits
- /cart      : Panier
- /contact   : Page contact

Fonctionnalités
----------------
- Authentification avec JWT
- CRUD Produit (admin)
- Gestion panier
- Recherche par nom
- Composants PrimeNG
- Responsive partiel

Fichiers supplémentaires
------------------------
- Collection Postman : Alten API.postman_collection.json
- Documentation OpenAPI (Swagger) : openapi.yaml

Auteur
------
Ismail Zerari

