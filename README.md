# Interactive Weather Application

## Project Overview
Learning to code is a fundamental skill in computer science education, but many novice programmers often struggle to stay motivated when completing abstract exercises like calculating the Fibonacci sequence or manipulating simple variables. While these exercises are important for understanding programming foundations, they can feel disconnected from real-world applications.  

This project addresses that challenge by integrating live environmental data into early programming education. Designed for sixth-form students, it provides a meaningful context to apply programming skills using real-world data rather than abstract examples.  

An interactive weather application was developed in Java using the Greenfoot IDE to support this goal. The system retrieves current and forecast weather data from the [OpenWeatherMap API](https://openweathermap.org/api) and displays it visually and interactively. Emphasis is placed on intuitive user interface design, enhancing the learning experience and making programming more enjoyable for novice programmers.

---

## Key Features

### Map Screen
- Displays a map of the United Kingdom.
- Marks the locations of cities with small dots.
- Shows current weather information for selected cities.
- Toggle buttons allow users to update the type of weather information displayed.
- Icons highlight in response to keyboard inputs for navigation.

### Graph Screen
- Displays a line graph of weather forecast data for the next 5 days.
- Drop-down menu to switch the type of forecast data displayed.
- Navigation buttons allow browsing through each day of the forecast.
- Summary button provides an overview of the displayed forecast data.

---

## Functional Requirements

### Map Screen
| ID   | User Input                        | Expected Output |
|------|----------------------------------|----------------|
| 1  | User clicks on the map            | Marks the point of the mouse click |
| 2  | User clicks on the map            | Highlights the nearest city to the mouse click |
| 3  | User clicks on the map            | Displays current weather at the nearest city |
| 4  | User clicks an inactive toggle button for a weather metric | Updates the display to show the selected weather metric |
| 5  | User clicks an active toggle button for a weather metric   | Updates the display to hide the selected weather metric |
| 6  | User presses the W key            | Pans the map up |
| 7  | User presses the A key            | Pans the map left |
| 8  | User presses the S key            | Pans the map down |
| 9  | User presses the D key            | Pans the map right |
| 10 | User presses the Up Arrow key     | Zooms in on the map |
| 11 | User presses the Down Arrow key   | Zooms out on the map |
| 12 | User presses the Right Arrow key  | Switches to the graph screen |

### Graph Screen
| ID   | User Input                        | Expected Output |
|------|----------------------------------|----------------|
| 13 | User clicks a button to view the next day of the forecast | The graph updates to show the forecast for the next day |
| 14 | User clicks a button to view the previous day of the forecast | The graph updates to show the forecast for the previous day |
| 15 | User selects a weather metric from the drop-down menu | The graph updates to display the chosen weather metric forecast |
| 16 | User clicks a button to print a summary of the line graph | A summary of the weather data for the selected day is printed in the terminal |
| 17 | User presses the Left Arrow key  | Switches back to the map screen |

---
