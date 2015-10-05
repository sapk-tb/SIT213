# SIT213 [![Build Status](https://travis-ci.org/sapk/SIT213.svg?branch=master)](https://travis-ci.org/sapk/SIT213)

```bash
#Generation de la Javadoc :
ant javadoc

#Lancement du programme avec les pamètre par défaut
ant run

#Lancement des Tests
ant test

#Package de l'application
ant jar

#Il est possible de cumuler les taches en une seule commande
ant javadoc run

#Commande unique :
Une fois l'application packagé (ant jar) executé comme ceci :
java -classpath ./dist/SIT213.jar Simulateur ARGS
Exemple : java -classpath ./dist/SIT213.jar Simulateur -seed 42
```
