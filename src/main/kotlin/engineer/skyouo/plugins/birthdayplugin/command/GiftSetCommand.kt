package engineer.skyouo.plugins.birthdayplugin.command

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.command.Command
import org.bukkit.entity.Player

class GiftSetCommand : CommandHandler() {
    override fun onCommand(sender: Player, command: Command, label: String, args: Array<String>) {
        if (sender.isOp) {
            val gift = args.getOrNull(1)
            if (gift != null) {
                val oldGift = BirthdayConfig.gift

                BirthdayConfig.gift = gift
                if (oldGift != null) {
                    Util.sendSystemMessage(sender, "設定生日禮包成功，原禮包為：$oldGift，新禮包為：$gift。")
                } else {
                    Util.sendSystemMessage(sender, "設定生日禮包成功，禮包為：$gift。")
                }
            } else {
                Util.sendSystemMessage(sender, "請輸入要給予的生日禮包。")
            }
        } else {
            Util.sendSystemMessage(sender, "您沒有權限使用此指令。")
        }
    }
}