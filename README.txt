# Configuration et utilisation de la base de données MySQL avec WAMP et phpMyAdmin

Ce guide vous aidera à configurer et à utiliser la base de données MySQL en utilisant WAMP et phpMyAdmin sur votre système.

## Installation de WAMP

1. Téléchargez et installez WAMP à partir du site officiel : [https://www.wampserver.com/en/](https://www.wampserver.com/en/).
2. Suivez les instructions d'installation fournies par le programme d'installation de WAMP.
3. Une fois l'installation terminée, lancez WAMP.

## Configuration de la base de données MySQL

1. Ouvrez votre navigateur web et accédez à l'interface de gestion de WAMP en entrant l'URL suivante dans la barre d'adresse : `http://localhost`.
2. Assurez-vous que les services Apache et MySQL sont démarrés. Vous pouvez le vérifier en regardant les icônes dans la barre des tâches de Windows.
3. Cliquez sur l'icône phpMyAdmin dans l'interface de gestion de WAMP pour accéder à phpMyAdmin.

## Création d'une nouvelle base de données

1. Dans phpMyAdmin, cliquez sur l'onglet "Bases de données".
2. Saisissez un nom pour votre nouvelle base de données dans le champ "Créer une base de données" et cliquez sur le bouton "Créer".

## Importation d'un fichier SQL

1. Dans phpMyAdmin, sélectionnez la base de données dans laquelle vous souhaitez importer le fichier SQL.
2. Cliquez sur l'onglet "Importer" dans la barre de navigation supérieure.
3. Cliquez sur le bouton "Parcourir" pour sélectionner le fichier SQL à importer depuis votre système.
4. Cliquez sur le bouton "Exécuter" pour importer le fichier SQL dans la base de données sélectionnée.

## Utilisation de la base de données dans votre application

Une fois que vous avez configuré et importé votre base de données, vous pouvez maintenant l'utiliser dans votre application. Assurez-vous de spécifier les paramètres de connexion corrects dans votre application pour vous connecter à la base de données MySQL.

## Lancer l'application

Pour lancer l'application exécuter la class SignUpUI.java
