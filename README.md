# sudoku
[Java Swing] 

Sudoku puzzle generator, game and solver.

Compatible with any OS, but only tested on Mac so far.

# Build

Compile simply with
```bash
  cd src
  javac Sudoku.java
```
and run with
```bash
  java -ea Sudoku
 ```

*Alternatively* use
```bash
  cd src
  chmod 711 make
  ./make
```
to create an executable .jar on Mac/Linux

# Use

Follow the instructions on the main menu. For an explanation of the scoring system, refer to comments in ```MyGui.calcUserScore()```.

# Looks like this

![Menu](../master/screenshots/menu.png?raw=true "Menu")
![Game](../master/screenshots/game.png?raw=true "Game")
