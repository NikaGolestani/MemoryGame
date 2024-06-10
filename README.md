# Memory Game

This is a simple Memory Game built using Java Swing. The game involves finding matching pairs of cards. 

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Features
- Multiple levels of difficulty (Easy, Medium, Hard)
- Keeps track of high scores
- Graphical user interface built with Java Swing
- Shuffle functionality for increased difficulty
- Game over screen with points display

## Requirements
- Java Development Kit (JDK) 8
- Eclipse IDE (recommended) or any other Java IDE

## Installation
1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/memory-game.git
   ```
2. **Open the project in Eclipse:**
   - Open Eclipse IDE.
   - Go to `File -> Import...`.
   - Select `Existing Projects into Workspace` and click `Next`.
   - Browse to the cloned repository directory and click `Finish`.

3. **Ensure Java 8 is set up:**
   - Right-click on the project in the Package Explorer.
   - Select `Properties`.
   - Navigate to `Java Build Path -> Libraries`.
   - Ensure the JRE System Library is set to JavaSE-1.8. If not, remove the existing one and add JavaSE-1.8.

4. **Configure the compiler compliance level:**
   - Go to `Properties -> Java Compiler`.
   - Check the `Enable project specific settings` checkbox.
   - Set the `Compiler compliance level` to `1.8`.

## Usage
1. **Run the Game:**
   - Right-click on the `GameMenu` class in the Package Explorer.
   - Select `Run As -> Java Application`.

2. **Gameplay:**
   - Select the desired level of difficulty from the main menu.
   - Click on the cards to reveal them and find matching pairs.
   - Your points and remaining attempts are displayed at the top.
   - The game ends when all pairs are matched or attempts run out.

## Configuration
- **High Scores:**
  - High scores are stored in a CSV file located at `src/data/highscores.csv`.
  - To reset high scores, simply delete the contents of this file.

- **Assets:**
  - Card images are stored in the `src/Java Project Assets` directory.
  - You can replace these images to customize the game.

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

