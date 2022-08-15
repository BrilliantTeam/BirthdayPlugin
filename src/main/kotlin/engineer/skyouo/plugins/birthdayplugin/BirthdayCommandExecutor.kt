package engineer.skyouo.plugins.birthdayplugin

import engineer.skyouo.plugins.birthdayplugin.command.*
import engineer.skyouo.plugins.birthdayplugin.model.BirthdayCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player


class BirthdayCommandExecutor : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender, command: Command, label: String, args: Array<out String>
    ): MutableList<String> {
        return when (args.size) {
            1 -> BirthdayCommand.values().filter { if (sender.isOp) true else !it.admin }.map { it.command }
                .filter { it.contains(args[0]) }.toMutableList()

            2 -> {
                val commands = when (args.first()) {
                    BirthdayCommand.Greetings.command -> mutableListOf("on", "off")
                    BirthdayCommand.Announcement.command -> mutableListOf("on", "off")
                    BirthdayCommand.Set.command -> {
                        if (!sender.isOp) return mutableListOf()

                        sender.server.offlinePlayers.map { it.name ?: it.uniqueId.toString() }
                    }

                    else -> mutableListOf()
                }

                commands.filter { it.contains(args[1]) }.toMutableList()
            }

            else -> {
                mutableListOf()
            }
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) return true
        if (args.isEmpty()) {
            HelpCommand().onCommand(sender, command, label, args)
            return true
        }

        when (args.first()) {
            BirthdayCommand.Set.command -> SetCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Greetings.command -> SwitchGreetingsCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Announcement.command -> SwitchAnnouncementCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Gift.command -> {}
            BirthdayCommand.Help.command -> HelpCommand().onCommand(sender, command, label, args)
            BirthdayCommand.GiftSet.command -> GiftSetCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Reload.command -> ReloadCommand().onCommand(sender, command, label, args)
            else -> {
                HelpCommand().onCommand(sender, command, label, args)
            }
        }

        return true
    }
}