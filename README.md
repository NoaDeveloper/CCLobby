# 🌐 CCLobby - Network Hub Manager

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Network](https://img.shields.io/badge/Network-BungeeCord_Messaging-339933?style=for-the-badge&logo=serverless&logoColor=white)
![Version](https://img.shields.io/badge/Version-0.1_Beta-orange?style=for-the-badge)

## 📡 Overview

**CCLobby** is a specialized Minecraft server plugin designed to act as the central Hub (Lobby) controller for a multi-server network.

Unlike standard lobby plugins, CCLobby implements a custom **Plugin Messaging Protocol** to communicate in real-time with a BungeeCord proxy. It allows for dynamic server status monitoring, player count synchronization, and remote server management (specifically for UHC game instances).

## ⚙️ Key Features

* **⚡ Real-Time Network Sync:** Uses the BungeeCord API (`ByteArrayDataInput`) to receive live updates from other servers.
    * **Status Monitoring:** Instantly updates signs or GUIs when a game server goes online/offline (`ServerStatus`).
    * **Player Tracking:** Synchronizes player counts across the network (`ServerCount`).
* **🛠️ Administration Tools:**
    * `/openserv`: Command to remotely trigger the startup of UHC game servers.
    * `/setgrade`: Integrated rank and permission management system.
* **🔌 Custom Channel Implementation:** Listens on the custom `CCChannel` to handle incoming data streams securely.

## 💻 Technical Highlights

The project showcases advanced usage of **I/O Streams** within the Spigot API to handle cross-server data packets.

### BungeeCord Messaging Implementation
The core logic resides in `BungeeMessageListener.java`, handling byte arrays to decode server information:

```java
@Override
public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
    if(channel.equals("CCChannel")){
        final ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        final String sub = in.readUTF(); // Reads the sub-channel (e.g., "ServerStatus")
        
        if(sub.equals("ServerStatus")){
            String serv = in.readUTF();
            String status = in.readUTF();
            lobby.handleStatus(serv, status); // Updates the lobby state
        }
    }
}
