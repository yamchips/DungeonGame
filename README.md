# README
### _Author: Fan Wu_

This README file is divided into the following parts.

- About/Overview
- List of Features
- How to Run
- How to Use the Program
- Description of Examples
- Design/Model Changes
- Assumptions
- Limitations
- Citations

## About/Overview

The project 5 requires us to build a GUI dungeon game. Its dungeon model is almost the same with project 4, except for adding a two-player game mode. User can set game parameters through menus, the setting includes size, wrapping, interconnectivity, percentage of treasure, number of monsters. Then the user can click on the dungeon map to traverse the dungeon, he/she can pick up arrows and treasure, shoot arrows along the way. If the game mode is one-player, then the game ends when this player reaches the end node or gets killed by an Otyugh. If it's two-player mode, then the game ends if one player gets killed or reaches the end.


To solve this problem, I created:
- Idungeon interface and Dungeon class to represent a dungeon
- Iplayer interface and Player class to represent a player
- Itreasure interface and Diamonds, Sapphires, and Rubbies class to represent treasure
- Inode interface and Node class to represent each node in the dungeon
- Imonster interface and Monster class to represent a monster.
- An enumerate class called Type to faciliate node class.
- An enumerate class called State to represent the state of the player.
- An enumerate class called Turn to represent the turn of two-player game.
- Icontroller interface and Controller class to represent a controller, DungeonMouse class to represent a mouse-adapter.
- DungeonView interface and DungeonGuiView class to represent the view, DungeonPanel class to represent the panel.

The Dungeon class uses player class and is composed of node class. A driver class is created and is used for asking for user inputs to run this game.

The res folder contains:

1. ExampleRun.pdf, which describes an example run.
2. FinalDesign.pdf, which shows my project design in UML diagram.
3. MANIFEST and project05.jar file, which are required by course submission requirement. 

## List of Features

- Run the driver class, the program will ask user to choose gui mode or text-based mode. Type in "gui" or "text" to choose. Then, type in the parameters in the JTextField, in File menu, choose "Set Game" for one-player mode or "Set 2 player" for two-player mode. The program will build a dungeon model, then the game starts.
- The project src folder contains four pacakages: controller, dungeon, player and treasure.
- Dungeon contains interfaces and classes of dungeon, monster, node, type and turn.
- Player contains the interface and concrete class of player as well as State class.
- Treasure contains the interface Itreasure and three concrete class of treasure.
- Controller contains the interface Icontroller and controller class, as well as DungeonMouse class.
- View contains the interface DungeonView and DungeonGuiView class, as well as DungeonPanel class.
- Test folder contains test class.

## How to Run

If you want to run from .java file, go to src/dungeon package, and right click on Driver.java, choose Run as -> Java Application. 

Choose mode: The driver class will ask user to choose gui mode or text-based mode. Type in "gui" or "text" to choose. 
If you choose gui model, a window will pop up. Then you can play game as follows: 
1. Build dungeon: Type in the parameters in the ipnut box. Example: "7 9 3 true 20 6" means a dungeon with 7 rows, 9 columns, 3 interconnectivity, wrapping, 20% caves have treasure and arrow, 6 monsters. 
2. Set dungeon: In File menu, choose "Set Game" for one-player mode or "Set 2 player" for two-player mode. The program will build a dungeon model, then the game starts. 
3. Move: Click on the dungeon map to make a move, If you click out of bounds or the distance between target node and current node is larger than 2, nothing happens.
4. Shoot: In the input box, type in the distance and direction. Example: "1 n" means distance is 1 and direction is north. N, s, w, e means north, south, west and east. Then click "Shoot" button on the menu bar, a shoot result will pop up.
5. Pick up: In the menu bar, click "Pick up" button, a pickup result will pop up.

If you choose text mode, then you can type in commands to move the player across the dungeon as the instructions shown in the command line.

Import the project05-dungeon.jar (in res folder) to a new java project. After that, the jar file should be in the Referenced Libraries of that project. Right-click on the jar file, and choose Run as -> Java application. The rest is the same as above.

## Description of Examples

In res folder, ExampleRun.pdf file contains screenshots of this game.

Example Run  Explanation:
1. Type in "7 9 3 true 20 6", create a dungeon, and click File-Set game, enter one-player mode. The current position is shown in a red circle.
2. Click "Pick up" in the menu bar, the pop-up message shows the result of this movement.
3. Click on the game board and move to a position, the player detects an Otyugh (level 1 warning).
4. Continue moving, the player detects an Otyugh (level 2 warning)
5. Type in "1 w", then click "Shoot" in the menu bar, player shoots an arrow and hits the Otyugh, as shown in the window.
6. Type in "1 w", then click "Shoot" in the menu bar, player shoots an arrow and kills the Otyugh, as shown in the window.
7. Move forward, the player finds the body of a dead Otyugh.


## Design/Model Changes

- I added an interface DungeonView to represent a GUI view, then implemented it by creating DungeonGuiView class. I also created DungeonPanel class to represent a panel.
- I added a DungeonMouse class to represent a mouse adapter since the GUI game need to be played by clicking mouse in the window.
- I added a read-only dungeon interface called ReadonlyDungeon which contains all read methods in the previous dungeon interface Idungeon. This interface interacts with the view.
- I added an enumerate class called Turn to represent the turn of a two-player game.
- I added some methods, fields and change some methods in Dungeon model to give the controller necessary information. For example, startGameAgain for starting a new game, retrieveOriginModel for starting a same game, playerEnterTwo for two-player mode, and so forth.

## Assumptions

1. In two-player mode, the arrow shooted by one player will not hurt the other player.
2. the length between start and end node must be at least five. That means the dungeon size cannot be too small, for instance,  a 3*3 dungeon is not allowed.
3. each node only has 1 arrow. The amount of arrows and treasusre that a player can carry has no limit. 

## Limitations

When the player decides to pick up, he/she can pick up all the arrows and treasure in this node. It would be better if the player can choose to pick up arrow or treasure. Besides, it will be more reasonable if there is a limit for the items a player can carry.

## Citations

Java教程. Java教程 - 廖雪峰的官方网站. (n.d.). Retrieved February 7, 2023, from https://www.liaoxuefeng.com/wiki/1252599548343744 
