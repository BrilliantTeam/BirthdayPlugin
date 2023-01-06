package tw.brilliant.server.plugins.birthdayplugin.event

import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayConfig
import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayStorage
import tw.brilliant.server.plugins.birthdayplugin.util.MinecraftAsynchronouslyTask
import tw.brilliant.server.plugins.birthdayplugin.util.Util
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
                        BirthdayConfig.personalGreetingsMessage
                    )
                }

                if (birthday.canGiveGift(player.isOp)) {
                    birthday.giveGift(player, true)
                }

                for (onlinePlayer in player.server.onlinePlayers) {
                    val data = BirthdayStorage.get(onlinePlayer)
                    if (data.announcement) {
                        Util.sendSystemMessage(
                            onlinePlayer,
                            Util.replacePlaceholders(
                                player,
                                BirthdayConfig.serverAnnouncementMessage
                            )
                        )
                    }
                }

                Util.sendDiscordMessage(
                    player.server,
                    Util.replacePlaceholders(
                        player,
                        BirthdayConfig.serverAnnouncementMessageDiscord
                    )
                )
            }
        }, BirthdayConfig.joinServerGiftDelay).start()
    }
}
