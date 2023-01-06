package tw.brilliant.server.plugins.birthdayplugin.util

import tw.brilliant.server.plugins.birthdayplugin.BirthdayPlugin
import org.bukkit.scheduler.BukkitRunnable

class MinecraftAsynchronouslyTask(private val unit: () -> Unit, private var delay: Int) : BukkitRunnable() {
    override fun run() {
        if (delay == 0) {
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