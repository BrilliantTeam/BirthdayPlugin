package tw.brilliant.server.plugins.birthdayplugin.command

import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayStorage
import tw.brilliant.server.plugins.birthdayplugin.util.Util
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiftCommand : CommandHandler() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        if (sender !is Player) return Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7此指令只能在遊戲內使用")

        val birthday = BirthdayStorage.get(sender)

        if (birthday.todayIsBirthday()) {
            if (birthday.canGiveGift(sender.isOp)) {
                birthday.giveGift(sender, false)
            } else {
                Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7您已經領取過生日禮包囉~")
            }
        } else {
            if (birthday.calendar != null) {
                Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7領取生日禮包失敗，今天不是您的生日喔~")
            } else {
                Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7領取生日禮包失敗，您尚未設定生日")
            }
        }
    }
}