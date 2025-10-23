# Patience (Solitaire) Project

## Overview

This is a command-line Solitaire (Klondike) game implemented in Java. It supports the full core gameplay experience including card shuffling, layout setup, valid move enforcement, scoring, and game completion detection. The code is modular and includes custom exception handling and a simple scoring system.

## Features

- Full implementation of Solitaire (Klondike) game mechanics.
- Automated deck shuffling and draw pile handling.
- Command-based interface for making moves.
- Move validation including multi-card stack moves.
- Score tracking based on move types.
- Win condition detection and end-game message.
- JUnit-based test cases for core game functionality.

## Prerequisites

### For Running the Project
- Java 11 or higher  
  Install from: [OpenJDK Downloads](https://jdk.java.net/)  
  Verify with: `java -version`

### For Building and Running Tests
- No external dependencies required (standard `javac` and `java` commands)
- JUnit (for test execution)

## How the Game Works

### Start the Game:
The game starts via `GameTest.java`, displaying the layout of the board with 7 lanes, a draw pile (P), and 4 foundation piles (H, S, C, D). Players interact via keyboard input.

### Gameplay:
- Cards must be moved according to Solitaire rules (descending rank, alternating color).
- You can draw cards, move them between piles, and build foundations.
- Multi-card moves are supported if the sequence is valid.

### Commands:
- `D` – Draw a card from the deck.
- `Q` – Quit the game.
- `<src><dst>` – Move one card (e.g., `P2`, `2D`).
- `<src><dst><n>` – Move `n` cards (e.g., `413` for moving 3 cards from lane 4 to 1).

### Scoring:
- Lane → Foundation: +20  
- Draw → Foundation: +10  
- Lane → Lane: +5

### Winning:
The game ends when all 52 cards are successfully placed into the 4 foundation piles (in-suit, in-order).

## Extra Features

Typing an invalid command gives feedback and help. The game is designed to be robust to invalid input and includes exception handling for illegal moves.

