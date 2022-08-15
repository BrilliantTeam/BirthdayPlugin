package engineer.skyouo.plugins.birthdayplugin.command

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class GiftSetCommand : CommandHandler() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        if (sender.isOp) {
            val gift = args.getOrNull(1)
            if (gift != null) {
                val oldGift = BirthdayConfig.gift

                BirthdayConfig.gift = gift
                if (oldGift != null) {
                    sender.sendMessage("設定生日禮包成功，原禮包為：$oldGift，新禮包為：$gift。")
                } else {
                    sender.sendMessage("設定生日禮包成功，禮包為：$gift。")
                }
            } else {
                sender.sendMessage("請輸入要給予的生日禮包。")
            }
        } else {
            sender.sendMessage("您沒有權限使用此指令。")
        }
    }
}