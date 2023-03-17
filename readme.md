# TransferEase

TransferEase is a file transfer application that allows you to send and receive files between a client and a server. This project contains the source code for both the server and client applications.

This project is currently in **beta** mode, which means that it may contain bugs and is not yet fully stable.

## Server

<div align="center">
  <img src="https://user-images.githubusercontent.com/75903708/225970323-80622a49-be87-4817-a03e-16732cc99637.png" width="500">
</div>
The server is a Java application that uses JavaFX for the graphical user interface. To start the server, simply run the `Server` class. The server listens on port 2121 by default.

### Prerequisites

- Java Development Kit (JDK) version 8 or later
- JavaFX

### Getting started

1. Clone this repository to your local machine.
2. Open the project in your Java IDE.
3. Build and run the `Server` class.

### Usage

1. Click the `Start` button to start the server.
2. The server will start listening for connections on port 2121.
3. Once a client connects to the server, the server will display a log of all activity in the GUI.
4. The server close automatically when you close the app.

## Client

<div align="center">
  <img src="https://user-images.githubusercontent.com/75903708/225971372-e94d851f-7df6-4f42-92de-14294fc8b10b.png" width="500">
</div>

The client is a Java Swing application that allows you to send and receive files from the server. To start the client, simply run the `FtpClientFrame` class.

### Prerequisites

- Java Development Kit (JDK) version 8 or later

### Getting started

1. Clone this repository to your local machine.
2. Open the project in your Java IDE.
3. Build and run the `FtpClientFrame` class.

### Usage

1. Run the client
2. Connection to the server : Put the the host adresse (`localhost` in case you are runing both of the apps in the same laptop).
3. Authentification : You can authentify by going to the item menu authentification or us the command `user theUsername'` and `pass password`.
   1. There is on user 'jhonedoe' to create another user you can create a folder in `ServerGui` the name of the folder will be the username and the password will be the first line of the file pw.txt (without space)
4. Enter a command in the text field and press `Enter` to send it to the server.
5. The server will send a response to the client, which will be displayed in the GUI.

## Important 

When creating a file or a folder, please avoid using spaces and use underscores instead. For example, use `mkdir this_is_a_folder` instead of `mkdir this is a folder`.

### Authors
<ul>
<li>Ziad Lahrouni : <a href="https://www.ziadlahrouni.com" target="_blank">Website</a> - <a href="https://github.com/Zlahrouni" target="_blank">Github</a></li>
<li>Hanane Erraji : <a href="https://www.hananeerraji.info" target="_blank">Website</a> - <a href="https://www.github.com/HananeErra" target="_blank">Github</a></li>
</ul>

## License

This project is licensed under the MIT License - see the `LICENSE` file for details.

## Acknowledgments

- [JavaFX With Eclipse](https://openjfx.io/openjfx-docs/#)
- [Ahcène Bouncer - ASSOCIATE PROFESSOR](http://bounceur.com/)
