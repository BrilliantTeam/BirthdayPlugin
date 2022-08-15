package engineer.skyouo.plugins.birthdayplugin.command

import org.bukkit.command.Command
import org.bukkit.entity.Player

abstract class CommandHandler {
    abstract fun onCommand(
        sender: Player, command: Command, label: String, args: Array<String>
    )
}