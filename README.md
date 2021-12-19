# ENSAH Library Management System
Development of a desktop application for the management of ENSAH library:management of loans, reservations, and returns of books using Java, JavaFX and MySQL.

Au démarrage de l’application cette fenêtre s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146660740-28a43d45-4225-43eb-bf1d-dfce51997411.png)

	Utilisateur:
  
	Authentification:
Une fois on clique sur l’icône « Etudiant » on se redirige vers la page d’authentification.

![image](https://user-images.githubusercontent.com/78702422/146660759-d0eddc12-e719-43cd-a6c5-9f577e04ef4a.png)

Au cas où le mot de passe est oublié, l'utilisateur peut le modifier.

![image](https://user-images.githubusercontent.com/78702422/146660828-ae3f276c-1e1c-4571-8105-83ef7417e85f.png)

	Inscription:
Si l’étudiant n’est pas encore membre dans l’application, une fois il clique sur le bouton « S’inscrire » il sera redirigé vers l’interface d’inscription.

![image](https://user-images.githubusercontent.com/78702422/146660855-da011215-04f2-4680-9f1d-30f104a42cfa.png)

	Accueil:
Si l’authentification d’étudiant est faite correctement, la page suivante s’affiche:

![image](https://user-images.githubusercontent.com/78702422/146660888-9c4de66a-f92e-4bb0-a969-d51c6865fcc7.png)

	Compte:
Si on clique sur le bouton « Mon compte » cette page s’affiche, contenant toutes les informations de l’étudiant connecté.

![image](https://user-images.githubusercontent.com/78702422/146660897-5b8ee933-800b-490c-abe4-31334a03d9fb.png)

	Modifier le mot de passe:
Si on clique sur le bouton « Changer mot de passe » cette page s’affiche, qui nous permet de changer l’ancien mot de passe avec un autre.

![image](https://user-images.githubusercontent.com/78702422/146660923-2ca142f6-403d-4f5b-a9b4-05e97bdbdcd6.png)

	Réserver un livre:
En effet, l'espace étudiant dispose d'un type de la gestion de réservation (Faire une réservation, affichages des livres réservés et annuler une réservation). Commençons avec la réservation d’un livre.
Une fois l’utilisateur clique sur le bouton « Réserver » cette fenêtre s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146660938-9167dacb-0e15-40b7-99bc-47c4f466d702.png)

Pour faire une réservation,l’utilisateur peut remplir les champs (code, titre, thème) manuellement, comme il peut séléctionner la ligne du tableau où se trouve le livre qui veut réserver.
Au cas où l’utilisateur séléctionne une ligne du tableau où se trouve le livre qu’il veut réserver, les informations de ce livre (Code,Titre,Thème) seront remplis automatiquement dans les champs correspondants.

![image](https://user-images.githubusercontent.com/78702422/146661154-183b671d-1931-4683-b0c0-8db374d62d7e.png)

En cliquant sur le bouton « Réserver », le livre sera réservé avec l'affichage du message « Le livre xxx est bien réservé ».

![image](https://user-images.githubusercontent.com/78702422/146661166-1b155c59-d9db-4412-a7e0-b6ec890e6e88.png)

L’utilisateur a le droit de réserver une seule fois le même livre sinon un message d’erreur sera affiché.

![image](https://user-images.githubusercontent.com/78702422/146661219-cb521984-a789-4805-acf1-ad50954c2325.png)

Si le livre n’est pas DISPONIBLE, dans ce cas l’étudiant ne peut pas réserver le livre ,et un message d’erreur sera affiché.

![image](https://user-images.githubusercontent.com/78702422/146661239-d2fd3d3b-1a08-4cec-b6b0-03b0e66d14f4.png)

L’utilisateur a la possibilité de réserver au maximum 3 livres.
S’il a déjà réservé 3 livres et il veut réserver un autre dans ce cas un message d’erreur sera affiché.

![image](https://user-images.githubusercontent.com/78702422/146661297-abb77b74-5eb8-4466-8d8b-dfe3fd74c294.png)

	Afficher les livres réservés:
L’utilisateur peut consulter tous les livres qui sont déjà réservés en cliquant sur le bouton « Livres réservés ».

![image](https://user-images.githubusercontent.com/78702422/146661321-04e0310b-b723-4957-9b96-f5175e6ff0f7.png)

	Annuler une réservation:
L’utilisateur peut annuler une réservation en cliquant sur le bouton "Annuler".

![image](https://user-images.githubusercontent.com/78702422/146661330-c3369823-863f-4578-8ba9-4b60bc1611a9.png)

Après l’annulation de la réservation du livre avec ID=5.

![image](https://user-images.githubusercontent.com/78702422/146661340-8732097a-4fcc-4e24-98b1-b710cdb8b2d0.png)

	Afficher les livres empruntés
Si on clique sur le bouton «Livres empruntés» cette page s’affiche, elle nous permet de consulter tous les livres empruntés.

![image](https://user-images.githubusercontent.com/78702422/146661436-ce495843-2145-448d-b222-66ee74a34f05.png)


