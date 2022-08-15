package engineer.skyouo.plugins.birthdayplugin.command

import engineer.skyouo.plugins.birthdayplugin.util.Util
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.command.Command
import org.bukkit.entity.Player

class HelpCommand : CommandHandler() {
    override fun onCommand(sender: Player, command: Command, label: String, args: Array<String>) {
        val components = ComponentBuilder()
        if (sender.isOp) {
            components.append("/btd set <ID> <YYYY> <MM> <DD> - 設定指定玩家的生日\n")
        } else {
            components.append("/btd set <YYYY> <MM> <DD> - 設定您的生日 （設定後不能再重設）\n")
        }

        components.append("/btd gs off - 關閉個人生日祝福（預設開啟）\n")
        components.append("/btd gs on - 開啟個人生日祝福（預設開啟）\n")
        components.append("/btd at off - 關閉全伺服器生日祝福（預設開啟）\n")
        components.append("/btd at on - 開啟全伺服器生日祝福（預設開啟）\n")
        components.append("/btd gift - 領取生日禮包（預設自動領取，只能領取一次）\n")

        if (sender.isOp) {
            components.append("/btd giftset <禮包名> - 設置指定的 kit 為預設給予的生日禮包\n")
        }

        components.append("/btd help - 顯示生日指令幫助列表 (就是此頁面)")

        sender.spigot().sendMessage(*components.create())
    }
}