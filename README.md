# SMB215
Le projet Cnam Achat est une partie du projet SMB215 proposé par le Professeur Pascal Fares afin de répondre aux besoins suivants :
	- Répondre au besoin de la direction du CNAM représenté par la demande d'achat des fournisseurs, le management d'achat et des produits et le contrôle de stock.
	- Répondre au besoin des employés CNAM représenté par la facilité de leurs travaux.

Ce projet comprend trois grandes parties :

     I- Les Applications.
    II- La Base de Données.
    III- Les Configurations du projet.

I- Les Applications.
Ce projet comprend trois applications :

a- MainAchat est une application web qui permet de gérer et de ménager l'action d'achat en gérant les sites, les produits, les fournisseurs, les demandes des achats, les réceptions, le stock,la notification pour recharger, les utilisateurs, les rôles.

b- STK_PRD_WS est une application web contenant des Restful Web Services permettant de gérer les données entre la base de données et l'application mobile 'SCANQR'. 

c- SCANQR est une application Android qui permet de balayer les codes-barres des produits demandés dans un PurchaseID spécifié dans cette application , de faire un contrôle sur les quantités demandées, de créer une réception de ce PurchaseID avec les détails , de gérer le stock et de donner des notifications lors d'une diminution d'une quantité d'un produit dans un site spécifié dans les préférences de l'application. 

II- La Base de Données (Oracle XE)
Cette BD possède les tables suivantes :
CATEGORY: représente les catégories des produits. Exemple : fourniture
GROUPS_ACHAT : représente les rôles des utilisateurs. Exemple : Admin
PRODUCT : représente les produits du CNAM. 
PURCHASES : représente la demande d'achat.
PURCHASES_DTL : représente le détails de la demande d'achat (Produits à demander)
RECEPT : représente la réception.
RECEPT_DTL : représente le détails de la réception.
SITES : représente les sites (branches) du CNAM.
STK_PRD : représente le stock des produits. 
STK_TRS : représente chaque transaction faite dans le système. 
SUPPLIER : représente les fournisseurs.
SUPPLIER_PRODUCTS : représente les produits de chaque fournisseur.
USERS_ACHAT : représente les utilisateurs du système. 



III- Les Configurations du projet.
L’environnement technique pour ce projet :

 	MainAchat : 
 		Java EE Version : Java EE 7 Web
		Server : Glassfish Server
		Framework : JSF avec PrimeFaces Suite
		Development IDE : Netbeans.
		Reporting : Jaspersoft iReport Designer 5.6.0

	STK_PRD_WS :
		Java EE Version : Java EE 7 Web
		Server : Glassfish Server
		RestFul Web Services. 
		Development IDE : Netbeans.

	ScanQR :
		Android SDK (8-21)
		Development IDE : Eclipse Mars.



Pour Plus de details voir le document 'Cnam Achat'
