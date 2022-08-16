package engineer.skyouo.plugins.birthdayplugin.command

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.command.Command
import org.bukkit.entity.Player

class ReloadCommand : CommandHandler() {
    override fun onCommand(sender: Player, command: Command, label: String, args: Array<String>) {
        if (sender.isOp) {
            Util.sendSystemMessage(sender, "Reloading birthday plugin config ...")
            BirthdayStorage.reload()
            BirthdayConfig.reload()
            Util.sendSystemMessage(sender, "birthday plugin config reloaded.")
        } else {
            Util.sendSystemMessage(sender, "&c您沒有權限執行此指令")
        }
    }
}