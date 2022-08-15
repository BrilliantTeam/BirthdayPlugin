package engineer.skyouo.plugins.birthdayplugin.event

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class EventListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val birthday = BirthdayStorage.get(event.player)

        if (birthday.todayIsBirthday()) {
            if (birthday.greetings) {
                Util.sendSystemMessage(event.player, "&a輝煌伺服器祝您生日快樂！&7（可使用 [/btd gs off] 關閉個人祝福）")
            }

            if (birthday.canGiveGift()) {
                birthday.giveGift(event.player)
            }

            for (player in event.player.server.onlinePlayers) {
                val data = BirthdayStorage.get(player)
                if (data.announcement) {
                    Util.sendSystemMessage(
                        player,
                        "&6今天是 &a${event.player.name} &6的生日，祝他生日快樂吧！&7（可使用 [/btd at off] 關閉全伺服器祝福）"
                    )
                }
            }

            Util.sendDiscordMessage(
                event.player.server,
                ":M8_RICE: 今天是 ${event.player.name} 的生日，祝他生日快樂吧！[ <@982640257340174396> ]"
            )
        }
    }
}