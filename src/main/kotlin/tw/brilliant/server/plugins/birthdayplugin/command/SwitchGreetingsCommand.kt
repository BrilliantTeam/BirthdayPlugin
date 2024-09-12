package tw.brilliant.server.plugins.birthdayplugin.command

import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayStorage
import tw.brilliant.server.plugins.birthdayplugin.util.Util
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SwitchGreetingsCommand : CommandHandler() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        if (sender !is Player) return Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7此指令只能在遊戲內使用。")

        val value = args.getOrNull(1)
        val birthday = BirthdayStorage.get(sender)

        when (value) {
            "on" -> {
                BirthdayStorage.set(sender, birthday.copy(greetings = true))
                Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§a已啟用§7個人生日祝福。")
            }

            "off" -> {
                BirthdayStorage.set(sender, birthday.copy(greetings = false))
                Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§c已停用§7個人生日祝福。")
            }

            else -> Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7生日指令格式錯誤（請輸入 [/btd help] 查看用法）")
        }
    }
}