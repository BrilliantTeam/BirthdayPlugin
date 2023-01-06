package tw.brilliant.server.plugins.birthdayplugin.util

import tw.brilliant.server.plugins.birthdayplugin.BirthdayPlugin
import tw.brilliant.server.plugins.birthdayplugin.config.BirthdayConfig
import me.clip.placeholderapi.PlaceholderAPI
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
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

    fun sendSystemMessage(sender: CommandSender, message: String) {
        sender.spigot().sendMessage(*getSystemMessage(message))
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

    fun hasAvailableSlot(player: Player, count: Int): Boolean {
        val inventory: Inventory = player.inventory
        var empty = 0

        for (item in inventory.storageContents) {
            if (item == null) {
                empty++
            }
        }

        return empty >= count
    }

    fun replacePlaceholders(player: Player, text: String): String {
        return try {
            PlaceholderAPI.setPlaceholders(player, text)
        } catch (e: Exception) {
            BirthdayPlugin.LOGGER.severe("Failed to replace placeholders")
            text
        }
    }
}