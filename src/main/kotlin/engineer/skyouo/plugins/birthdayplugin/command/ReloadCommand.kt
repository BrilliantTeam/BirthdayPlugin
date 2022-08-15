package engineer.skyouo.plugins.birthdayplugin.command

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ReloadCommand : CommandHandler() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        sender.sendMessage("Reloading birthday plugin...")
        BirthdayStorage.reload()
        BirthdayConfig.reload()
        sender.sendMessage("Birthday birthday plugin reloaded.")
    }
}