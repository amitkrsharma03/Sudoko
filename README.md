# Sudoku Game

## Overview
The **Sudoku Game** is a classic puzzle game implementation that allows users to play and solve Sudoku puzzles. 
The game features an intuitive graphical user interface (GUI) and includes logic to validate the correctness of the solution upon completion.
It is developed using Java swing utilites and awt tool kit

## Features
- Interactive Sudoku board
- Real-time input validation
- Automatic success checking
- User-friendly GUI

## Logic for Checking the Success of the Game
The game determines whether a Sudoku puzzle is successfully solved using the following validation methods:

1. **Row Validation**
   - Each row must contain digits **1-9** without repetition.
   - A set is used to track unique numbers in each row.

2. **Column Validation**
   - Each column must also contain digits **1-9** without repetition.
   - The game iterates through all columns and verifies uniqueness using a set.

3. **Subgrid (3x3 Box) Validation**
   - The 9 smaller **3×3 grids** within the Sudoku board must contain digits **1-9** without duplication.
   - The board is divided into sections, and each box is checked separately.

4. **Final Validation Check**
   - The game checks all three conditions (rows, columns, and subgrids) before confirming a successful solution.
   - If all constraints are met, the game displays a success message.

## Graphical User Interface (GUI)
The Sudoku Game uses a clean and interactive GUI to enhance user experience. The key elements of the GUI include:

- **Grid Layout**: The board is represented as a 9×9 grid where users can input numbers.
- **Cell Highlighting**: Selected cells are highlighted to improve visibility.
- **Error Indication**: Invalid inputs are visually flagged.
- **Check Solution Button**: Users can click a button to verify if the solution is correct.
- **Success Message**: If the solution is valid, a success message appears.

## How to Play
1. Start the game to load an unsolved Sudoku puzzle.
2. Click on a cell and enter a number (1-9).
3. Ensure each row, column, and 3x3 grid follows Sudoku rules.
4. Click the **Check Solution** button to validate your solution.
5. If correct, a success message will be displayed!

## Future Enhancements
- Add a hint system for beginners.
- Implement different difficulty levels.
- Include a timer to track performance.

## License
This project is open-source and available for modifications and improvements.

---
Let me know if you want to add anything else!

