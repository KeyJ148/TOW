# Tanks: Orchestra of War

## Project Description
This is a small 2D arcade game with a top-down view, designed for network play with other players. Each player controls a tank. The objective is to destroy the tanks of other players and survive. To assist players, special bonus boxes periodically appear on the map: three types of boxes for changing tank equipment and one repair kit box.

Equipment-changing boxes allow players to acquire a new hull, weapon, or type of projectile. This system creates a large number of combinations, each of which can be strategically utilized depending on the situation.

<img src="https://raw.githubusercontent.com/KeyJ148/TOW/217798b51c5c92f33d487d1c3744cfb83578f53c/1.png" alt="Tanks: Orchestra of War" width="270"/>  <img src="https://raw.githubusercontent.com/KeyJ148/TOW/217798b51c5c92f33d487d1c3744cfb83578f53c/2.png" alt="Tanks: Orchestra of War" width="270"/>  <img src="https://raw.githubusercontent.com/KeyJ148/TOW/217798b51c5c92f33d487d1c3744cfb83578f53c/3.png" alt="Tanks: Orchestra of War" width="270"/>

<details>
  <summary>Screenshots</summary>
  <img src="https://github.com/KeyJ148/TOW/blob/217798b51c5c92f33d487d1c3744cfb83578f53c/1.png" alt="Tanks: Orchestra of War"/>  
  <img src="https://github.com/KeyJ148/TOW/blob/217798b51c5c92f33d487d1c3744cfb83578f53c/2.png" alt="Tanks: Orchestra of War"/>  
  <img src="https://github.com/KeyJ148/TOW/blob/217798b51c5c92f33d487d1c3744cfb83578f53c/3.png" alt="Tanks: Orchestra of War"/>
</details>

## Controls
* **W/S** - Move forward/backward
* **A/D** - Rotate the tank around its axis
* **Left Mouse Button** - Fire the tank's weapon in the direction of the cursor
* **1/2/3/4** - Block pickup of boxes (hull/weapon/projectile/repair kit)
* **F2** - Display tank information
* **F3** - Display debug information

### Single-player Only
* **N** - Commit suicide
* **T/G/B** - Change hull, weapon, or projectile type, respectively
* **H/F** - Restore 40% or 100% health, respectively
* **V** - Maximize vampirism effect

## Technical Details
This project is developed in Java 17 using the [LWJGL3](https://github.com/LWJGL/lwjgl3) library for OpenGL 3.3 functionality and [LeGUI](https://github.com/SpinyOwl/legui) for UI management.

We use a custom [engine](https://github.com/KeyJ148/TOW/tree/develop/OrchEngine), which was maintained in a separate [repository](https://github.com/KeyJ148/OrchEngine) until version [v4.2.0](https://github.com/KeyJ148/TOW/releases/tag/v4.2.0). The project combines component-oriented programming for game object development and service-oriented programming for engine functionality.

### Game Engine
#### Service Components and Dependency Injection
The engine employs the PicoContainers library for dependency injection and inversion of control.  
At startup, the engine scans specified packages for annotated classes (`EngineService`, `GameService`, `TestService`) and adds them to the context.  
The application context is divided into groups based on the parent thread, with each group having unique service visibility, simplifying parallelized testing of multiple game clients.

Using PicoContainers and a custom annotation system, test services can replace standard ones, such as rendering or texture services. This enables headless testing, ideal for environments like Jenkins. Services can also be configured dynamically using `ProfilesService` with environment variables.

#### Game Loop
Game world updates (logic) and rendering are executed sequentially. Object interpolation for rendering without updating the game state is avoided. Each world update passes the elapsed nanoseconds since the previous update to objects, ensuring consistent state synchronization across clients with varying frame rates.

Vertical synchronization (VSync) is used to cap the frame rate, adjustable via a VSync divisor in the settings (0 = uncapped, 1 = monitor refresh rate, 2 = half refresh rate, etc.). On systems lacking VSync support, frame rate limits can be enforced by putting the game thread to sleep.

#### World Processing
The game world is divided into locations, with only one active at a time. Active locations are further subdivided into chunks, optimizing updates and rendering. Chunks outside the screen are updated only when active objects are present.

#### Resources
Configuration files are categorized as internal (packaged in the jar) and external (stored in the root directory). External configurations override internal defaults, allowing user-specific settings like graphics and audio. All configurations are in JSON format.

#### Game Objects
Game objects inherit from a common base class with `update` and `render` methods. Since version 2.0.0, a component-oriented architecture has replaced a complex inheritance hierarchy. Components implement interfaces like `Updatable`, `Drawable`, and `Collidable`, enabling modular behavior.

#### Client-Server Interaction
The server processes client messages containing an ID and corresponding data. Clients establish both TCP and UDP connections. TCP is optimized for low latency, while UDP handles non-critical but time-sensitive data (e.g., player positions).

#### Engine Usage
The engine functions as a framework with inversion of control. To initialize, implement required interfaces and pass them to `OrchEngine#start`. Game logic is customized via `GameInterface` methods (`init`, `update`, `render`), game object behavior, or annotated `GameService` classes.

### Game Mechanics
#### Equipment
Equipment (armor, weapons, projectiles) is configured via JSON files. Mechanics and properties (e.g., speed, damage, fragmentation) are defined within these files. The system dynamically associates mechanics with classes using reflection, streamlining the addition of new equipment.

#### Effects System
The effects system manages changes to player attributes from equipment or events. Effects are additive or percentage-based and are calculated sequentially for clarity. This modular design decouples attribute calculations from specific mechanics.

#### Testing
Integration tests validate game functionality using multiple simultaneous game clients. The PicoContainers library ensures isolated service contexts for each client, enabling robust testing of multiplayer mechanics.

### CI/CD
#### Jenkins
Each commit triggers Jenkins to build the project for multiple platforms (Windows/Linux/MacOS, including ARM variants), run tests, and publish builds on our [website](https://tow.abro.cc/).
