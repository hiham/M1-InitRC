# Projet M1-InitRc

Il s'agit d'un projet Java Swing construit à l'aide de Maven dans le cadre du cours InitRc.

## Installation

### Maven

Maven est un outil d'automatisation de construction principalement utilisé pour les projets Java. Suivez ces étapes pour installer Maven sur Windows :

1. **Téléchargement de Maven** : Visitez le [site Web Apache Maven](https://maven.apache.org/download.cgi) et téléchargez la dernière version de Maven.

2. **Extraction de l'archive Maven** : Extrayez l'archive Maven téléchargée dans un emplacement sur votre ordinateur. Par exemple, `C:\Program Files\Apache\maven`.

3. **Configuration des variables d'environnement** :
    - **M2_HOME** : Ajoutez une nouvelle variable d'environnement système nommée `M2_HOME` et définissez sa valeur sur le chemin où Maven est installé (par exemple, `C:\Program Files\Apache\maven`).
    - **PATH** : Ajoutez `%M2_HOME%\bin` à la variable d'environnement PATH de votre système.

4. **Vérification de l'installation** : Ouvrez une invite de commandes et tapez `mvn -version`. Vous devriez voir les informations de version de Maven si l'installation a réussi.

### IntelliJ IDEA

IntelliJ IDEA est un environnement de développement intégré (IDE) populaire pour le développement Java. Suivez ces étapes pour utiliser Maven avec IntelliJ IDEA :

1. **Ouvrir le projet** : Ouvrez IntelliJ IDEA et sélectionnez "Ouvrir" depuis l'écran d'accueil. Naviguez jusqu'au répertoire contenant votre projet Maven et sélectionnez le fichier `pom.xml`.

2. **Importer le projet Maven** : IntelliJ IDEA détectera automatiquement le projet Maven et vous proposera de l'importer. Cliquez sur "Importer les modifications" pour continuer.

3. **Configurer Maven dans IntelliJ** : Si IntelliJ IDEA ne détecte pas automatiquement votre installation Maven, vous pouvez le configurer manuellement :
    - Allez dans "Fichier" > "Paramètres" (ou "IntelliJ IDEA" > "Préférences" sur macOS).
    - Naviguez jusqu'à "Build, Execution, Déploiement" > "Outils de construction" > "Maven".
    - Définissez le "Répertoire Maven" sur l'emplacement où Maven est installé.

4. **Construction et exécution** : Vous pouvez maintenant construire et exécuter votre projet Maven à l'aide d'IntelliJ IDEA. Les dépendances Maven seront gérées automatiquement.

## Utilisation
Ce placer à l'endroit ou se situe le pom.xml puis éxecuter

```
mvn compile
```

Puis par la suite , éxecuter

```
mvn compile
```

Ensuite ce déplacer dans le dossier target et éxecuter

```
java -cp rc-1.0.jar main.java.com.exemple.Main
```

## Rapport
[Le rapport](https://docs.google.com/document/d/1CjejVY4Z9kRsRSkG0fEAsckY4BWIbZCgVA6KKZLSk6I/edit#heading=h.z6ne0og04bp5)



