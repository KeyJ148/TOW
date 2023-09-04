# Tanks: Orchestra of War
## Project Description
This is a small 2D arcade game with a top view, designed for network play with other people. Each player is given control of a tank. The goal of the game is to destroy the tanks of other players and stay alive. To help the players, special bonus boxes periodically appear on the map: 3 types of boxes for changing the tank equipment and 1 repair kit box.

Boxes for changing equipment allow the player to get a new hull, weapon, or type of projectiles. As a result, there are a large number of combinations, each of which can find its application depending on the situation.

<img src="https://raw.githubusercontent.com/KeyJ148/TOW/217798b51c5c92f33d487d1c3744cfb83578f53c/1.png" alt="Tanks: Orchestra of War" width="270"/>  <img src="https://raw.githubusercontent.com/KeyJ148/TOW/217798b51c5c92f33d487d1c3744cfb83578f53c/2.png" alt="Tanks: Orchestra of War" width="270"/>  <img src="https://raw.githubusercontent.com/KeyJ148/TOW/217798b51c5c92f33d487d1c3744cfb83578f53c/3.png" alt="Tanks: Orchestra of War" width="270"/>

<details>
  <summary>Screenshots</summary>
  <img src="https://github.com/KeyJ148/TOW/blob/217798b51c5c92f33d487d1c3744cfb83578f53c/1.png" alt="Tanks: Orchestra of War"/>  
  <img src="https://github.com/KeyJ148/TOW/blob/217798b51c5c92f33d487d1c3744cfb83578f53c/2.png" alt="Tanks: Orchestra of War"/>  
  <img src="https://github.com/KeyJ148/TOW/blob/217798b51c5c92f33d487d1c3744cfb83578f53c/3.png" alt="Tanks: Orchestra of War"/>
</details>

## Control
* WS - move forward/backward
* AD - rotate the tank around its axis
* Left Mouse Button - tank shot in the direction of the cursor
* 1/2/3/4 - block the pickup of boxes (for the hull/weapon/type of projectile/repair kit)
* F2 - tank information
* F3 - debug information
### For single-player only
* N - suicide
* T/G/B - change the hull, weapon or type of projectile respectively
* H/F - restore 40% or 100% of health respectively
* V - maximize the effect of vampirism

## Technical part
This project is developed in Java 17, using the [LWJGL3](https://github.com/LWJGL/lwjgl3) library, which allows accessing OpenGL 3.3 functions, and  [LeGUI](https://github.com/SpinyOwl/legui) for working with the interface.
We use our own [engine](https://github.com/KeyJ148/TOW/tree/develop/OrchEngine), which until version [v4.2.0](https://github.com/KeyJ148/TOW/releases/tag/v4.2.0) was in a separate [repository](https://github.com/KeyJ148/OrchEngine).  
The project combines a component-oriented programming to developing game objects and a service-oriented programming to accessing functional parts of the engine.
### Game engine
###### Service components and dependency injection
The engine uses the PicoContainers library for dependency injection and inversion of control.  
Upon launch, the engine automatically scans all classes in the specified package, looks for our annotations EngineService, GameService, and TestService, and adds these classes to the context.  
The application context is divided into several groups (in accordance with the parent thread) and each group has its own service visibility scope. This makes it easier to parallelize the launch of several game clients during testing, each of which has its own unique list of containers.

Thanks to PicoContainers and our annotation system, we can replace some services with others (test ones) during loading. This allows, for example, to replace services RenderService, TextureService, and similar ones with stubs, which completely excludes the use of OpenGL and allows automatic testing on systems without a monitor or video driver, for example, in Jenkins.  
Service configuration is also available through ProfilesService, which allows enabling/disabling and replacing services using environment variables.
###### Game cycle
The update of the game world state (update) and rendering of the image (render) are performed strictly one after the other. Interpolation of object positions for rendering without updating the game world state is not used. With each world update, the number of nanoseconds that have passed since the last update is passed to the objects. On the basis of this value, changes in the state of game world objects are calculated (for example, movement). This is done to avoid desynchronization of the game world state for clients with different frame rates.

Vertical synchronization is used to limit the frame rate. In the settings, you can set the vertical synchronization divider: 0 - no limit, 1 - monitor frequency, 2 - half the monitor frequency, etc. If vertical synchronization is not supported by video card drivers (for example, in some Unix systems), then you can set a frame rate limit in the settings, which will be performed by sending the main game thread to sleep. The implementation of the frame rate limit function takes into account the peculiarities of stopping the thread, due to which it is not guaranteed that the thread will wake up on time, so the thread is stopped several times for ever decreasing periods of time.
###### Game world processing
The game world is divided into locations. Only one location can be active at one time. Update and render functions are called on objects on the active location. To save resources, the location is divided into chunks and contains a list of visible chunks that need to be rendered. Chunks beyond the screen boundary are updated only in case there are active objects on them, for which the update function needs to be called.
###### Resources
Configuration files are divided into 2 groups: internal and external. Internal configuration files are saved in the jar file during project assembly, they include paths to textures, sounds, etc. External configuration files are located in the root of the project, and when they are deleted, default values from internal configuration files are set. External configuration files store user sound, graphics settings, etc. Configuration files are presented in JSON format. A class has been developed to work with them, which allows you to load or save data from internal/external configuration files to the corresponding object using generics.
###### Game objects
All objects located in the location are inherited from the common class of game objects. Game objects have update and render functions, which are called by the engine at the appropriate moment of the game cycle. Until version 2.0.0 of the game, the game object class had a complex inheritance chain, which made it difficult to expand its functionality when implementing a specific game element. At the moment, a component-oriented approach is used when working with game objects.

Components can implement the Updatable, Drawable, Positionable, Collidable interfaces. Depending on the implemented interfaces, the game engine will call the corresponding functions on the component every game tick.
###### Client-server interaction
To create a server, you need to pass a class to the constructor that implements the function of processing client messages. The message contains an ID and data specific to the given ID. To connect to the server, you need to create a client, into which a class is also passed, implementing the processing of messages received from the server.

When a client connects, two connections are created at once: TCP and UDP. Despite the fact that the TCP connection is set to the minimum delays (for example, the tcp_nodelay option is enabled), in case of packet loss, there are quite long delays. Therefore, for events that are critical to speed and not critical to packet delivery (for example, the current position of the player), the UDP protocol is used.
###### Game engine usage
The engine is a framework and when using it, there is an inversion of control. To start, you need to implement several interfaces and pass them to the OrchEngine#start function. One of these interfaces is GameInterface, containing the init, update, and render functions. Init is called before the start of the main game cycle, update and render - before updating and rendering the game world respectively.  
The second main way to interfere with the game process is to create a game object and override its update and render functions, or override these functions in any of the object's components.  
The third way is to create a class with the GameService annotation and then it will be automatically added to the game context when the application starts. This class can override start, stop functions and interact with other services of the engine and the game.
### Game
###### Equipment
During the game, the player uses various equipment: armor, weapons, and projectiles. Adding equipment to the game and its configuration has been greatly simplified. Equipment properties are described in a configuration file. Properties include both general properties for all equipment of a certain type (for example, armor speed, maneuverability, texture, etc.), and specific to a certain mechanic (for example, the number of fragments in a fragmentation projectile). At the same time, the mechanic itself is also described in the configuration file. For each type of equipment (armor, weapon, projectile), a parent class has been created, from which all classes of various mechanics are inherited.  
When a player picks up a box with equipment, a random configuration file is selected from those located in the folder with configuration files of this equipment. From this file, the equipment mechanic is read, after which, using reflection, a class with the same name is found, which already requests parameters specific to this mechanic.

This mechanism has allowed getting rid of manual indication of a large number of connections between mechanics, configuration files, textures, etc. To add new equipment to the game, you just need to put one configuration file in the corresponding folder. To add a new mechanic, you just need to inherit a new class from the parent class and after that, this mechanic can be indicated in any configuration file.
###### Effects System
Our game features a wide array of equipment, which can alter the character's attributes to varying extents. The equipment can either increase/decrease a certain characteristic or modify it in a percentage-based manner. To make the calculation of these changes more manageable and clear, we have developed a special effects system.   
Every event that can alter the player's attributes triggers a corresponding effect. An effect describes all the changes that occur to the character. The calculation of attributes takes into account all additive effects first, followed by all percentage increases of the characteristic. This allows the player class to remain independent of the specific classes and mechanics that apply effects.
###### Testing
We have developed several integration tests for automatic functionality checks of the game. With the use of PicoContainers library and service visibility restrictions in accordance with ThreadGroup and the parent thread, it is possible to simultaneously launch multiple game clients directly from a single test. This way, we can test mechanisms of player connection to the server and other fundamental aspects of the game's operation.
### CI/CD
###### Jenkins
After each commit to this repository, GitHub automatically sends a notification to our Jenkins server. Jenkins then builds the latest commit from the respective branch for various operating systems (Windows/Linux/Linux ARM/MacOS/MacOS ARM), runs the automatic tests, and publishes the ready build on our [website](https://tow.abro.cc/).
