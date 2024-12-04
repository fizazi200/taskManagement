#Introduction
Une  application web de gestion des tâches qui permettra aux utilisateurs de créer, consulter, modifier et supprimer leurs tâches personnelles


#security
pour lancer l'application il est obligatoire de declarer le variable d'environement ** jwt.secret **
pour tester l'application il faut creér un token en utilisant l'algorithme **HS256** et avec le méme secret definit dans jwt.secret

le playlod du token doit contenir {"sub":"ownerUsername"} , ownerUsername c'est le nom d'utilisateur propriétaire du task

#compilation 
mvn clean package