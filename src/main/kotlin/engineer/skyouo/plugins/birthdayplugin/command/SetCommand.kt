package engineer.skyouo.plugins.birthdayplugin.command

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.entity.Player

class SetCommand : CommandHandler() {
    override fun onCommand(sender: Player, command: Command, label: String, args: Array<String>) {
        if (sender.isOp && args.size == 5) {
            val player = sender.server.offlinePlayers.find { it.name == args[1] || it.uniqueId.toString() == args[1] }

            if (player != null) {
                setBirthday(sender, player, args, 2)
            } else {
                Util.sendSystemMessage(sender, "找不到此玩家：${args[1]}")
            }
        } else if (args.size == 4) {
            val oldBirthday = BirthdayStorage.get(sender.uniqueId.toString())
            if (oldBirthday != null) return Util.sendSystemMessage(sender, "您已經設定過生日了，生日只能設定一次！")

            setBirthday(sender, sender, args, 1)
        } else {
            Util.sendSystemMessage(sender, "設定生日失敗，請檢查輸入的日期格式是否正確 (請輸入 [/btd help] 查看用法)")
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
                    Util.sendSystemMessage(sender, "設定生日失敗，你不是未來人 ._.")
                    return
                }

                BirthdayStorage.set(player, calendar, false)
                Util.sendSystemMessage(sender, "成功設定生日，${player.name} 的生日為：$year 年 $month 月 $day 日")
            } catch (e: Exception) {
                Util.sendSystemMessage(
                    sender,
                    "設定生日失敗，請檢查輸入的日期格式是否正確 (請輸入 [/btd help] 查看用法)"
                )
            }
        } else {
            Util.sendSystemMessage(sender, "設定生日失敗，請檢查輸入的日期格式是否正確 (請輸入 [/btd help] 查看用法)")
        }
    }
}