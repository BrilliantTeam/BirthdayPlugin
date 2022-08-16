package engineer.skyouo.plugins.birthdayplugin.command

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ReloadCommand : CommandHandler() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        if (sender.isOp) {
            Util.sendSystemMessage(sender, "&7正在重新加載生日插件配置...")
            BirthdayStorage.reload()
            BirthdayConfig.reload()
            Util.sendSystemMessage(sender, "&7重新加載生日插件配置完畢！")
        } else {
            Util.sendSystemMessage(sender, "&c您沒有權限執行此指令")
        }
    }
}
