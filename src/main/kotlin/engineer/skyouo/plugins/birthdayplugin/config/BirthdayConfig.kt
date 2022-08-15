package engineer.skyouo.plugins.birthdayplugin.config

import engineer.skyouo.plugins.birthdayplugin.util.Util
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object BirthdayConfig {
    private val file: File = Util.getFileLocation("config.yml")
    private val configuration = YamlConfiguration.loadConfiguration(file)

    var gift: String?
        get() = configuration.getString("gift")
        set(value) {
            configuration.set("gift", value)
            save()
        }

    fun reload() {
        configuration.load(file)
    }

    fun save() {
        configuration.save(file)
    }
}