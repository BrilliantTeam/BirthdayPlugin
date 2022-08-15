package engineer.skyouo.plugins.birthdayplugin.config

import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object BirthdayConfig {
    private val file: File = Util.getFileLocation("config.yml")
    private val configuration = YamlConfiguration.loadConfiguration(file)

    val giftCommands: MutableList<String>
        get() = configuration.getStringList("gift_commands")

    fun init() {
        if (!configuration.isSet("gift_commands")) {
            configuration.set("gift_commands", listOf("minecraft:give %player% minecraft:diamond 1"))
            configuration.setInlineComments(
                "gift_commands", arrayListOf("The gift given to the player on his birthday (command)")
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