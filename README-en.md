>*Vous pouvez trouver la version française de ce texte [ici](https://github.com/Doodoal/DBSCAN-en-4-langages/blob/main/README.md)*
<br>

Project done during the Winter 2022 session of the french equivalent of *CSI 2120 : Programming paradigms*, University of Ottawa



# DBSCAN written in 4 langages 
*Java, Go , Prolog, Scheme*

The code was commented in French. 

I particularly had fun doing the [Java part](https://github.com/Doodoal/DBSCAN-en-4-langages/tree/main/Java%20Part/Final%20version) (Object-Oriented) of the project :D

This program form clusters of GPS positions found in data collected from taxi trips. 
For this project, samples of this data were used:
- *yellow_tripdata_2009-01-15_1hour_clean.csv*   **[Java Part]**
- *yellow_tripdata_2009-01-15_9h_21h_clean.csv*  **[Golang Part]**
- *partition65.csv , partition74.csv, partition75.csv, partition76.csv, partition84.csv, partition85.csv, partition86.csv*  **[Prolog Part]**
- *partition65.csm , partition74.csm, partition75.csm, partition76.csm, partition84.csm, partition85.csm, partition86.csm*  **[Scheme Part]**



### Java Part (Oriented-Object Programming): 
Finding clusters containing a minimal *minPts* number of points , and far from each other from a maximal *eps* distance 

### Partie Golang (Concurrent Programming): 
Same as the Java Part, but simultaneously on the 16 partitions into which the entire plan was divided.

### Partie Prolog (Logic Programming): 
Merging all the clusters found using the Golang Part (Some clusters overlap on each other)

### Partie Scheme (Functionnal Programming): 
Same as the Prolog Part
