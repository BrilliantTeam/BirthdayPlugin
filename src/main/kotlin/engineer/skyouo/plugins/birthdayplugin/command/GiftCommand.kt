package engineer.skyouo.plugins.birthdayplugin.command

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.command.Command
import org.bukkit.entity.Player

class GiftCommand : CommandHandler() {
    override fun onCommand(sender: Player, command: Command, label: String, args: Array<String>) {
        val birthday = BirthdayStorage.get(sender)

        if (birthday.todayIsBirthday()) {
            if (birthday.canGiveGift(sender.isOp)) {
                birthday.giveGift(sender)
            } else {
                Util.sendSystemMessage(sender, "&c您已經領取過生日禮包囉~")
            }
        } else {
            if (birthday.calendar != null) {
                Util.sendSystemMessage(sender, "&c領取生日禮包失敗，今天不是您的生日喔~")
            } else {
                Util.sendSystemMessage(sender, "&c領取生日禮包失敗，您尚未設定生日")
            }
        }
    }
}