# BirdRio - Windows Port

Bird Rio ported to Windows from Android

## Structure

- `core`: Original code and compatibility layers (`org.cocos2d`, `android.*`).
- `desktop`: Launcher for Windows.
- `assets`: Images, sounds and levels.

## Compile and run for Windows

Install **Java Development Kit (JDK 8 or higher)**.

1. Run a terminal (PowerShell or CMD) in the root of the project.
2. Run with Gradle:
   ```bash
   ./gradlew desktop:run
   ```
   (On Windows you use `gradlew.bat desktop:run`)

3. Make JAR (JAR executable):
   ```bash
   ./gradlew desktop:dist
   ```
   JAR will be in `desktop/build/libs/`.

## Notes
 
- Resolution set to 1280x800.
- Touch mapped to click.
- Sound has been converted to LibGDX compatible.

Happy playing, modding or whatever !
