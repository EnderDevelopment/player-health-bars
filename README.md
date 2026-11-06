# Player Health Display Minecraft Plugin

Player Health Display is a Minecraft plugin that shows each online player's current and maximum health above their head using a floating text display. The display includes a heart icon, health values, automatic color changes, and follows players as they move.

## Install

1. Install Java 21.
2. Use a Paper Minecraft server compatible with API version 1.21.
3. Build the plugin with Maven:

   ```text
   mvn package
   ```

4. Copy the generated `target/plugin.jar` file into your server's `plugins` folder.
5. Start or restart the server.

## Use

1. Join the server with one or more players.
2. Each online player automatically receives a health display above their head.
3. The display shows health in the format `❤ current/max`.
4. Health text colors change automatically:
   - Green when health is above 50%.
   - Gold when health is above 25% and up to 50%.
   - Dark red when health is 25% or lower.
5. The display follows the player's position and updates automatically.
6. No commands or configuration are required.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/07375a6e-a7af-4f09-ab83-ba46681e1422?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=07375a6e-a7af-4f09-ab83-ba46681e1422
