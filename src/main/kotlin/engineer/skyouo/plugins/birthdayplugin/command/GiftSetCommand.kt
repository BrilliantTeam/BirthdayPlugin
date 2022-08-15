package engineer.skyouo.plugins.birthdayplugin.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class GiftSetCommand : CommandHandler() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        if (sender.isOp) {
            TODO("Not implemented yet")
        } else {
            sender.sendMessage("您沒有權限使用此指令")
        }
    }
}