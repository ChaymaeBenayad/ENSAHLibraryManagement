# ENSAH Library Management System
Development of a desktop application for the management of ENSAH library:management of loans, reservations, and returns of books using Java, JavaFX and MySQL.

Au démarrage de l’application cette fenêtre s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146660740-28a43d45-4225-43eb-bf1d-dfce51997411.png) 

   ## Utilisateur:
  
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

Pour faire une réservation,l’utilisateur peut remplir les champs (code, titre, thème) manuellement, comme il peut sélectionner la ligne du tableau où se trouve le livre qui veut réserver.
Au cas où l’utilisateur sélectionne une ligne du tableau où se trouve le livre qu’il veut réserver, les informations de ce livre (Code,Titre,Thème) seront remplis automatiquement dans les champs correspondants.

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


   ## Administrateur:

	Authentification:
	
![image](https://user-images.githubusercontent.com/78702422/146661563-74cc3a4d-337c-4bb9-bb3c-038b1c1ce9ec.png)

	Accueil:
![image](https://user-images.githubusercontent.com/78702422/146661574-21e921e6-b000-4407-98ee-f7280a2e1132.png)

	Affichage des livres:
Une fois on clique sur le bouton « Livres » cette fenêtre s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146661584-c86cab3c-5fcd-4d3a-84d1-503f0a8211c2.png)

	Modifier un livre:
Pour modifier un livre, il suffit de cliquer sur l’icône de modification du livre qu’on veut modifier, ensuite la fenêtre de modification s’affiche:

![image](https://user-images.githubusercontent.com/78702422/146661612-3356b831-859b-4f65-ae58-1f088883b4d0.png)

	Supprimer un livre:
Concernant la suppression, on clique sur l’icône de suppression du livre qu’on veut supprimer, ensuite un message montrant que le livre a été bien supprimé s’affiche:

![image](https://user-images.githubusercontent.com/78702422/146661630-87544ac5-bd4d-461d-85a5-06044de71dd2.png)

	Affichage des étudiants:
Une fois on clique sur le bouton « Etudiants », cette fenêtre s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146661650-06c80056-e1d3-4c69-92db-0a093bcfbb5f.png)

Le champ de la recherche par filtrage permet à l’administrateur de rechercher un étudiant par son CNE ou par son nom comme suit :

![image](https://user-images.githubusercontent.com/78702422/146661655-e84a6bb2-404b-46df-8a68-34262a9024c4.png)

	Ajouter un livre:
Une fois on clique sur le bouton « Ajouter Livre » cette fenêtre s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146661676-7f74769d-db71-40fa-8b99-2115673daef3.png)

	Emprunter un livre:
Avant d’emprunter un livre à un étudiant, l’administrateur peut vérifier si le livre est réservé par cet étudiant ou pas en cliquant sur le bouton « Livres Réservés » qui permet d’afficher tous les livres réservés par l’étudiant :

![image](https://user-images.githubusercontent.com/78702422/146661699-d2d00066-5113-4fca-8de8-c2ed54b82e4d.png)

Une fois on clique sur le bouton « Emprunter Livre », cette fenêtre s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146661705-f8ec90d4-9940-4820-a5e3-7172632d4dde.png)


L’administrateur saisit l’id de l’étudiant qui veut emprunter un livre, ensuite il clique sur la clé « Entrer » du clavier pour afficher les informations de cet étudiant. De plus, il saisit l’id du livre à emprunter, ensuite il clique sur la clé « Entrer » du clavier pour afficher les informations du livre.
Le système vérifie les différents cas :
<li>Si l’étudiant n’a pas réservé le livre qu’il veut emprunter, un message d’erreur s’affiche:</li></br>

![image](https://user-images.githubusercontent.com/78702422/146661793-932b877a-e96a-4d3f-83e2-05247401ec60.png)

<li>Si le livre est déjà emprunté par l’étudiant, un message d’erreur s’affiche :</li></br>

![image](https://user-images.githubusercontent.com/78702422/146661801-5ef9e4fd-e4ce-4e2f-8bfa-8289c0af9957.png)

<li>Si les informations saisies sont correctes, on affiche ce message :</li></br>

![image](https://user-images.githubusercontent.com/78702422/146661883-08421f1b-cef9-4e8e-8bfc-29eb4e832b10.png)

Pour vérifier que le livre est bien emprunté, on clique sur le bouton « Livres Empruntés » qui permet d’afficher tous les livres empruntés par tous les étudiants :

![image](https://user-images.githubusercontent.com/78702422/146661896-edf1ab2b-0a80-4e94-b5c6-2fbff5e36ff1.png)

Après l’emprunt du livre, il va être retiré de la table des livres réservés :

![image](https://user-images.githubusercontent.com/78702422/146661909-63631746-a40c-46af-b1a6-22252912dbdf.png)

	Retourner un livre:
Une fois on clique sur le bouton « Retourner Livre », cette fenêtre s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146661926-73d5256a-38eb-4e7e-a569-41ddd339ecfb.png)


L’administrateur doit saisir l’ID du livre et l’ID de l’étudiant pour retourner le livre emprunté, ensuite il doit cliquer sur le bouton « Entrer » pour afficher les informations relatives au livre emprunté et à l’étudiant. Le système vérifie les cas suivants :
<li>Si le livre qu’on veut retourner n’est pas emprunté, un message d’erreur s’affiche :</li></br>

![image](https://user-images.githubusercontent.com/78702422/146661933-c7b018d3-1946-46b0-ac26-c1e35ac0ee50.png)

<li>Si le livre qu’on veut retourner existe dans la table des livres empruntés, les informations relatives au livre et à l’étudiant s’affichent :</li></br>

![image](https://user-images.githubusercontent.com/78702422/146661947-cc33ffaf-824d-40d9-ab6c-6f1384324d5c.png)

Une fois on clique sur le bouton « Retourner », ce message s’affiche :

![image](https://user-images.githubusercontent.com/78702422/146661956-87200567-78bd-493d-aeb0-91768cf37536.png)

Après le retour du livre, il va être retiré de la table des livres empruntés :

![image](https://user-images.githubusercontent.com/78702422/146661961-525aec7e-79a3-430d-b435-aeebcdd342ce.png)
