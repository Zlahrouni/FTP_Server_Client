# TransferEase

TransferEase is a file transfer application that allows you to send and receive files between a client and a server. This project contains the source code for both the server and client applications.

This project is currently in **beta** mode, which means that it may contain bugs and is not yet fully stable.

## Server

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
4. The server close automatically when you close the app

## Client

The client is a Java Swing application that allows you to send and receive files from the server. To start the client, simply run the `FtpClientFrame` class.

### Prerequisites

- Java Development Kit (JDK) version 8 or later

### Getting started

1. Clone this repository to your local machine.
2. Open the project in your Java IDE.
3. Build and run the `FtpClientFrame` class.

### Usage

1. Enter a command in the text field and press `Enter` to send it to the server.
2. The server will send a response to the client, which will be displayed in the GUI.

### Authors
- Ziad Lahrouni : [Website](www.ziadlahrouni.com) - [Github](www.github.com/Zlahrouni)
- Hanane Erraji : [Website](www.hananeerraji.info) - [Gitub](www.github.com/HananeErra)

## License

This project is licensed under the MIT License - see the `LICENSE` file for details.

## Acknowledgments

- [JavaFX With Eclipse](https://openjfx.io/openjfx-docs/#)
- [Ahcène Bouncer - ASSOCIATE PROFESSOR](http://bounceur.com/)
