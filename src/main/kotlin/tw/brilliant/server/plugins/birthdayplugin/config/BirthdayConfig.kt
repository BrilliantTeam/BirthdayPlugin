package tw.brilliant.server.plugins.birthdayplugin.config

import tw.brilliant.server.plugins.birthdayplugin.util.Util
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object BirthdayConfig {
    private val file: File = Util.getFileLocation("config.yml")
    private val configuration = YamlConfiguration.loadConfiguration(file)

    val giftCommands: MutableList<String>
        get() = configuration.getStringList("gift_commands")

    val discordSrv: Boolean
        get() = configuration.getBoolean("discord_srv", true)

    val joinServerGiftDelay: Int
        get() = configuration.getInt("join_server_gift_delay", 0)

    fun init() {
        if (!configuration.isSet("gift_commands")) {
            configuration.set("gift_commands", listOf("minecraft:give %player% minecraft:diamond 1"))
            configuration.setInlineComments(
                "gift_commands", arrayListOf("The gift given to the player on his birthday (command)")
            )
        }

        if (!configuration.isSet("discord_srv")) {
            configuration.set("discord_srv", true)
            configuration.setInlineComments(
                "discord_srv", arrayListOf("Whether to send the birthday message to the Discord server (discord srv)")
            )
        }

        if (!configuration.isSet("join_server_gift_delay")) {
            configuration.set("join_server_gift_delay", 0)
            configuration.setInlineComments(
                "join_server_gift_delay",
                arrayListOf("The delay in seconds before the join server birthday gift is given")
            )
        }

        save()
    }

    fun reload() {
        configuration.load(file)
    }

    fun save() {
        configuration.save(file)
    }
}