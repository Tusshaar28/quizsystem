# Quiz System

## Overview

The Quiz System is a Java-based application that allows users to take quizzes on various topics. It provides a graphical user interface (GUI) using Swing, where users can select a topic, answer questions, bookmark questions, and view their results.

## Features

- **Topic Selection**: Users can choose from a list of topics to take a quiz on.
- **Question Display**: Questions are displayed with multiple-choice options.
- **Bookmarking**: Users can bookmark questions for later review.
- **Results**: After completing the quiz, users receive a score showing the number of correct answers.

## Requirements

- Java Development Kit (JDK) 8 or higher
- An IDE like IntelliJ IDEA or Eclipse (optional)

## Usage

1. **Run the Application**: Compile and run the `Test` class. A topic selection window will appear.
2. **Select a Topic**: Choose a topic from the list (e.g., OS, DBMS, React, OOP, Compiler Design). The quiz for the selected topic will start.
3. **Answer Questions**: Choose an answer and click "Next" to proceed to the next question.
4. **Bookmark Questions**: Click "Bookmark" to save a question for later review.
5. **View Results**: Click "Result" to see the total number of correct answers after finishing the quiz.

## File Structure

-  src/testtype/os.txt (or other topic files) - Contains questions for the quiz. Each line should be in the format:
    ```
    question;option1;option2;option3;option4;correctOptionIndex
    ```
    Example:
    ```
    What is the capital of France?;Berlin;London;Paris;Rome;2
    ```

## Code Explanation

- **Test Class**: This is the main class that extends `JFrame` and implements `ActionListener`. It manages the GUI and quiz logic.
- **Question Class**: Nested class that represents a question, its options, and the index of the correct answer.
- **loadQuestions()**: Loads questions from a file based on the selected topic.
- **initializeUI()**: Sets up the GUI components and initial state.
- **actionPerformed()**: Handles button clicks, including navigating questions, bookmarking, and displaying results.
- **set()**: Updates the GUI with the current question and options.
- **check()**: Checks if the selected answer is correct.
