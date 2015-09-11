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
