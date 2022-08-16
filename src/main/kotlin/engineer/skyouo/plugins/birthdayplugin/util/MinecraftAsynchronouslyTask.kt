package engineer.skyouo.plugins.birthdayplugin.util

import engineer.skyouo.plugins.birthdayplugin.BirthdayPlugin
import org.bukkit.scheduler.BukkitRunnable

class MinecraftAsynchronouslyTask(private val unit: () -> Unit, private var delay: Int) : BukkitRunnable() {
    override fun run() {
        if (delay == 0) {
            BirthdayPlugin.LOGGER.info("run task")
            cancel()
            unit()
        } else {
            delay--
        }
    }

    fun start() {
        runTaskTimer(BirthdayPlugin.INSTANCE, 20, 20)
    }
}