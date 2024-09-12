package tw.brilliant.server.plugins.birthdayplugin.command

import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayStorage
import tw.brilliant.server.plugins.birthdayplugin.util.Util
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetCommand : CommandHandler() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        if (sender !is Player) return Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7此指令只能在遊戲內使用。")

        if (sender.isOp && args.size == 5) {
            val player = sender.server.offlinePlayers.find { it.name == args[1] || it.uniqueId.toString() == args[1] }

            if (player != null) {
                setBirthday(sender, player, args, 2)
            } else {
                Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7找不到此玩家：${args[1]}。")
            }
        } else if (!sender.isOp && args.size == 4) {
            val oldBirthday = BirthdayStorage.get(sender)
            if (oldBirthday.calendar != null) {
                Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7您已經設定過生日了，生日只能設定一次！")
                return
            }

            setBirthday(sender, sender, args, 1)
        } else {
            Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7設定生日失敗，請檢查輸入的日期格式是否正確（請輸入 [/btd help] 查看用法）")
        }
    }

    private fun setBirthday(sender: Player, player: OfflinePlayer, args: Array<String>, index: Int) {
        val year = args[index].toIntOrNull()
        val month = args[index + 1].toIntOrNull()
        val day = args[index + 2].toIntOrNull()

        if (year != null && month != null && day != null) {
            try {
                val calendar = Util.getTaipeiCalendar()
                calendar.set(year, month - 1, day)

                if (calendar.after(Util.getTaipeiCalendar())) {
                    Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7設定生日失敗，你不是未來人 ._.")
                    return
                }


                val oldData = BirthdayStorage.get(player)

                BirthdayStorage.set(
                    player,
                    oldData.copy(calendar = calendar)
                )
                Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7成功設定生日，${player.name} 的生日為：$year 年 $month 月 $day 日")
            } catch (e: Exception) {
                Util.sendSystemMessage(
                    sender,
                    "§7｜§6系統§7｜§f飯娘：§7設定生日失敗，請檢查輸入的日期格式是否正確（請輸入 [/btd help] 查看用法）"
                )
            }
        } else {
            Util.sendSystemMessage(sender, "§7｜§6系統§7｜§f飯娘：§7"設定生日失敗，請檢查輸入的日期格式是否正確（請輸入 [/btd help] 查看用法）")
        }
    }
}