# Running the project locally
To run this project locally, you can use the provided dockerfile and docker-compose.yml <br>
(If you have Docker and docker-compose installed). <br>

Make sure you have exported this project as a .jar file and placed it inside the root folder.
After that you can simply run `docker compose up --build` or `docker-compose up --build` for older docker versions


<br>

# Development

## DevContainer
It is recommended to open this project inside of a vscode DevContainer to make sure you're running the code inside the correct environment. <br>

To do this, you need to have Docker installed and open this project in vscode. <br>
Next, make sure you have the Docker and DevContainer extension installed. <br>
Then press `ctrl + shift + P` and select `Dev Containers: Reopen Folder Locally`

<br>

## Apache Maven
This project is managed using Apache Maven. <br>
<br>

## Project structure

### src
The `src` folder includes all java classes. `App.java` is the main class. <br>

### pom.xml
The pom.xml file is the 'Project Object Model', which Maven uses to to get the necessary information about the project. <br>
