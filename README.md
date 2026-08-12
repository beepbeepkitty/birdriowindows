# BirdRio - Windows Port

Questo progetto è un porting del gioco Android "BirdRio" per Windows, realizzato utilizzando il framework **LibGDX** come bridge di compatibilità per le API di Cocos2d-android.

## Struttura del Progetto

- `core`: Contiene il codice originale del gioco e il layer di compatibilità (`org.cocos2d`, `android.*`).
- `desktop`: Contiene il launcher per Windows.
- `assets`: Contiene le immagini, i suoni e i livelli del gioco.

## Come Compilare ed Eseguire su Windows

Per compilare il progetto, assicurati di avere installato il **Java Development Kit (JDK 8 o superiore)**.

1. Apri un terminale (PowerShell o CMD) nella cartella radice del progetto.
2. Esegui il comando Gradle per avviare l'applicazione:
   ```bash
   ./gradlew desktop:run
   ```
   (Su Windows usa `gradlew.bat desktop:run`)

3. Per creare un pacchetto distribuibile (JAR eseguibile):
   ```bash
   ./gradlew desktop:dist
   ```
   Il file JAR verrà generato in `desktop/build/libs/`.

## Note sul Porting

Il porting utilizza un bridge personalizzato che mappa le chiamate Cocos2d alle corrispondenti funzioni LibGDX. 
- La risoluzione è impostata a 1280x800.
- L'input touch è mappato sul click del mouse.
- I suoni sono stati convertiti o mappati per funzionare con le API audio di LibGDX.

Buon divertimento!
