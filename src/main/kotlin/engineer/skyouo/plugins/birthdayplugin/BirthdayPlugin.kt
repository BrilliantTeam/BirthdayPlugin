package engineer.skyouo.plugins.birthdayplugin

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class BirthdayPlugin : JavaPlugin() {
    companion object {
        val LOGGER: Logger = Logger.getLogger("BirthdayPlugin")
    }

    override fun onEnable() {
        LOGGER.info("BirthdayPlugin is enabled")
    }

    override fun onDisable() {
        LOGGER.info("BirthdayPlugin is disabled")
    }
}