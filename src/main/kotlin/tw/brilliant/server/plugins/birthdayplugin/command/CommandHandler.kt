package tw.brilliant.server.plugins.birthdayplugin.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class CommandHandler {
    abstract fun onCommand(
        sender: CommandSender, command: Command, label: String, args: Array<String>
    )
}