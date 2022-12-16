Projet fait à l'occasion de la session d'Hiver 2022 du cours de *CSI 2520 : Paradigmes de programmations*, Université d'Ottawa

# DBSCAN fait en 4 langages 
*Java, Go , Prolog, Scheme*

Ce projet a été documenté en français. 
Cependant, le nom des dossiers et des fonctions de ce programme sont en anglais à cause de préférences personnelles.

J'ai particulièrement eu du fun en réalisant la [partie Java](https://github.com/Doodoal/DBSCAN-en-4-langages/tree/main/Java%20Part/Final%20version) (Orientée objet) du projet :D

Le programme consiste à grouper les positions GPS trouvées dans les données recueillies pour plusieurs voyages en taxi.
Ces dites données ont été transcrites dans les fichiers:
- *yellow_tripdata_2009-01-15_1hour_clean.csv*   **[Partie Java]**
- *yellow_tripdata_2009-01-15_9h_21h_clean.csv*  **[Partie Golang]**
- *partition65.csv , partition74.csv, partition75.csv, partition76.csv, partition84.csv, partition85.csv, partition86.csv*  **[Partie Prolog]**
- *partition65.csm , partition74.csm, partition75.csm, partition76.csm, partition84.csm, partition85.csm, partition86.csm*  **[Partie Scheme]**



### Partie Java (Programmation Orienté-Objet): 
Trouver différents groupes (clusters) d'effectif minimal minPts et de points séparés au plus d'une distance eps 

### Partie Golang (Programmation concurrente): 
Même chose que la partie Java, mais de manière simultanée sur 16 partitions constituant le plan entier.

### Partie Prolog (Programmation logique): 
Fusionner les clusters s'intersectant parmi la liste de groupes trouvés grâce à la partie Golang (Certains clusters ne se limitent pas à la partition dans laquelle ils ont été trouvés, et donc ont d'autres éléments dans différentes partitions)

### Partie Scheme (Programmation fonctionnelle): 
Même chose que la partie Prolog
