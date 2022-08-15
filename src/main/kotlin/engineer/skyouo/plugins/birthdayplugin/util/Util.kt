package engineer.skyouo.plugins.birthdayplugin.util

import engineer.skyouo.plugins.birthdayplugin.config.BirthdayConfig
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.entity.Player
import java.io.File
import java.util.*

object Util {
    private fun getBaseDirectory(): File {
        return File("plugins/BirthdayPlugin")
    }

    fun getFileLocation(fileName: String): File {
        val baseDirectory: File = getBaseDirectory()
        if (!baseDirectory.exists()) {
            baseDirectory.mkdirs()
        }

        return baseDirectory.resolve(fileName)
    }

    private fun getSystemMessage(message: String): Array<BaseComponent> {
        return ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7[&6系統&7] &f飯娘：&7$message")).create()
    }

    fun sendSystemMessage(player: Player, message: String) {
        player.spigot().sendMessage(*getSystemMessage(message))
    }

    fun sendDiscordMessage(server: Server, message: String) {
        if (BirthdayConfig.discordSrv) {
            server.dispatchCommand(server.consoleSender, "discordsrv bcast $message")
        }
    }

    fun getTaipeiCalendar(): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"))
        calendar.time = Date()
        return calendar
    }
}