package tw.brilliant.server.plugins.birthdayplugin

import tw.brilliant.server.plugins.birthdayplugin.command.*
import tw.brilliant.server.plugins.birthdayplugin.model.BirthdayCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

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

                        sender.server.onlinePlayers.map { it.name }
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
        if (args.isEmpty()) {
            HelpCommand().onCommand(sender, command, label, args)
            return true
        }

        when (args.first()) {
            BirthdayCommand.Set.command -> SetCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Greetings.command -> SwitchGreetingsCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Announcement.command -> SwitchAnnouncementCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Gift.command -> GiftCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Help.command -> HelpCommand().onCommand(sender, command, label, args)
            BirthdayCommand.Reload.command -> ReloadCommand().onCommand(sender, command, label, args)
            else -> {
                HelpCommand().onCommand(sender, command, label, args)
            }
        }

        return true
    }
}