# System design
- LobbyManager is responsible for managing lobbies, base class of, well, lobbies
- Server part of Web is responsible for creating new lobbies or directing players to existing ones AND displaying proper websites
- (web.socket) WebSocket is responsible for server<->player interactions during the game
- WebSocket delegates incoming messages to GameSocketHandler, which handles it further using appropriate commands
- Sending messages is tied to Player object (with list of open sessions)
- Event system is designed to be as flexible as possible, all elements of model are interacting with it
- EventListener are - simply put - only Cards (power ones), which modify Event object
- Config design is currently yet to be decided



# Class diagram
Some relations might be missing.

[Class diagram](diagram.png)

Highlights:
- events package
- model subpackages

# Design patterns
- Observer - event listeners
- Singleton - LobbyManager, config reading
- MVC - typical split for video game
- State - state of the lobby
- Façade - hiding complexities of spring, mainly used for WebSocket wrapping\
- Builder - construction of Event object

# SOLID implementation 
- Single-Responsibility mostly compliant, although sometimes breached for simplicity (eg. Game)
- Open/Closed Principle was kept in mind while designing Events system and Cards system, which are the main areas for further extension
- Liskov Substitution compliant
- Interface Segregation compliant - interfaces contain only necessary methods and nothing more
- Dependency Inversion applied where adequate


# Afterthoughts
- UML for games is painful
- PlantUML Integration for IntelliJ is a godsend
- Markdown is nice
- ChatGPT can generate file structure from provided UML
- I'm not sure if I prefer graphic UML editor or text-based one (maybe text- for object design, but graphic for relations?)