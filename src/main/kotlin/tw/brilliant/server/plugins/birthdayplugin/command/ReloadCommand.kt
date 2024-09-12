package tw.brilliant.server.plugins.birthdayplugin.command

import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayConfig
import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayStorage
import tw.brilliant.server.plugins.birthdayplugin.util.Util
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ReloadCommand : CommandHandler() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        if (sender.isOp) {
            Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7正在重新載入生日插件資料...")
            BirthdayStorage.reload()
            BirthdayConfig.reload()
            Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7成功重新載入生日插件資料！")
        } else {
            Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7您沒有權限執行此指令。")
        }
    }
}
