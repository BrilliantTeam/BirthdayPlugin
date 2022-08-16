package engineer.skyouo.plugins.birthdayplugin.event

import engineer.skyouo.plugins.birthdayplugin.BirthdayPlugin
import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import engineer.skyouo.plugins.birthdayplugin.config.BirthdayStorage
import engineer.skyouo.plugins.birthdayplugin.util.MinecraftAsynchronouslyTask
import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class EventListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        MinecraftAsynchronouslyTask({
            val player = event.player
            val birthday = BirthdayStorage.get(player)

            if (birthday.todayIsBirthday()) {
                if (birthday.greetings) {
                    Util.sendSystemMessage(
                        player,
                        "&a輝煌伺服器祝您生日快樂！&7（可使用 [/btd gs off] 關閉個人祝福）"
                    )
                }

                if (birthday.canGiveGift(player.isOp)) {
                    birthday.giveGift(player)
                }

                for (onlinePlayer in player.server.onlinePlayers) {
                    val data = BirthdayStorage.get(onlinePlayer)
                    if (data.announcement) {
                        Util.sendSystemMessage(
                            onlinePlayer,
                            Util.replacePlaceholders(
                                player,
                                "&6今天是 %playerTitle_use% &a%essentials_nickname% &6的生日，祝他生日快樂吧！&7（可使用 [/btd at off] 關閉全伺服器祝福）"
                            )
                        )
                    }
                }

                Util.sendDiscordMessage(
                    player.server,
                    Util.replacePlaceholders(
                        player,
                        ":M8_RICE:` 今天是 %playerTitle_use% %essentials_nickname% 的生日，祝他生日快樂吧！ `"
                    )
                )
            }
        }, BirthdayConfig.joinServerGiftDelay).start()
    }
}
