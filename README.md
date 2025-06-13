# CS2110 Assignment 5 - Interactive Target Game GUI

## Overview
A Java Swing-based interactive game where players click on moving targets to score points. This assignment demonstrates GUI programming concepts including event-driven architecture, widget composition, layout management, and the Model-View-Controller design pattern.

## Learning Objectives
- Build interactive GUIs using Java Swing components
- Implement event-driven programming with listeners and handlers
- Practice inversion of control and separation of concerns
- Work with layout managers and custom painting
- Create menus, dialogs, and file I/O functionality
- Learn to read and apply API documentation independently

## Game Features

### Core Gameplay
- Click blue targets before they disappear to score points
- Targets spawn randomly and move around the game board
- Game ends after 15 targets have been spawned
- Score tracking with visual feedback (targets turn red when hit)

### Interactive Controls
- **Start/Restart button** - Begin a new game
- **Size slider** - Adjust target radius (5-50 pixels)
- **Speed slider** - Control target respawn rate (100-2000ms)
- **Score display** - Real-time score updates

### Menu System
- **File Menu**
    - Save Score - Append scores to a file with timestamp
    - Exit - Close the application
- Error handling with dialog boxes for file I/O issues

## Technical Implementation

### Architecture
- **GameComponent** - Custom JPanel handling game logic and rendering
- **GameMain** - Application entry point and GUI assembly
- **Event-Driven Design** - Mouse clicks, timer ticks, and widget interactions
- **Property Change Listeners** - Loose coupling between components

### Key Concepts Demonstrated
1. **Custom Painting** - Override `paintComponent()` for game rendering
2. **Event Handling** - Mouse events, action events, change events
3. **Layout Management** - BorderLayout with nested panels
4. **Swing Threading** - Proper use of Event Dispatch Thread
5. **File I/O** - Save functionality with exception handling

## Project Structure
```
a5/
├── src/
│   ├── GameComponent.java    # Game logic and rendering (YOUR CODE)
│   ├── GameMain.java         # GUI assembly and event wiring (YOUR CODE)
│   └── Target.java           # Target data model (provided)
├── reflection.txt            # Assignment reflection questions
└── README.md                 # This file
```

## Building and Running

### Prerequisites
- Java 11 or higher
- Java Swing library (included with JDK)

### Compilation
```bash
javac -d out src/*.java
```

### Running the Application
```bash
java -cp out GameMain
```

## Implementation Tasks (TODOs)

### Layout & Widgets (TODOs 1-3)
1. Add score label to north panel
2. Add start button to south panel
3. Wire start button to begin game

### Game Logic (TODOs 4-8)
4. Implement game board painting
5. Handle timer tick events
6. Draw target dots
7. Implement hit detection
8. Process mouse click events

### Widget Integration (TODOs 9-11)
9. Update score label on property changes
10. Connect size slider to target radius
11. Connect speed slider to respawn rate

### Polish & Features (TODOs 12-15)
12. Add slider endpoint labels
13. Create labeled slider panels
14. Build file menu structure
15. Implement save score functionality

## Testing Guidelines

### Interactive Testing Checklist
- [ ] Score label appears in correct location
- [ ] Start button appears and functions
- [ ] Game board shows black when inactive
- [ ] Targets appear and move when game active
- [ ] Clicking targets increases score
- [ ] Sliders control size and speed
- [ ] Labels properly describe sliders
- [ ] File menu saves scores correctly
- [ ] Error dialogs appear for I/O failures

### Coordinate System Notes
- Origin (0,0) is upper-left corner
- X increases rightward
- Y increases downward
- Use `getWidth()` and `getHeight()` for dynamic sizing

## Design Principles

### Separation of Concerns
- **GameComponent** - Game logic only, no knowledge of external UI
- **GameMain** - GUI assembly only, no game logic
- **Event Listeners** - Minimal glue code between components

### Why This Architecture?
- Promotes reusability of GameComponent
- Avoids circular dependencies
- Follows MVC-inspired patterns
- Enables independent testing of components

## Optional Enhancements
Feel free to add creative touches after completing requirements:
- Custom colors or shapes for targets
- High score tracking across sessions
- Difficulty levels or game modes
- Sound effects or animations
- Player name entry for leaderboards

## Academic Integrity
This is a CS2110 assignment at Cornell University. Students must follow the course collaboration policy and work only with their declared partner.

## Authors
Cameron Brady and Grant Wu

## Acknowledgments
Assignment created by the CS2110 course staff at Cornell University.