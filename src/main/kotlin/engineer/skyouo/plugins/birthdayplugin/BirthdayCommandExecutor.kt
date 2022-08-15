package engineer.skyouo.plugins.birthdayplugin

import engineer.skyouo.plugins.birthdayplugin.command.GiftSetCommand
import engineer.skyouo.plugins.birthdayplugin.command.HelpCommand
import engineer.skyouo.plugins.birthdayplugin.model.BirthdayCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player


class BirthdayCommandExecutor : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        return if (sender.isOp) {
            if (args.size == 1) {
                BirthdayCommand.values().map { it.command }.toMutableList()
            } else {
                return mutableListOf()
            }
        } else {
            if (args.size == 1) {
                BirthdayCommand.values().filter { !it.admin }.map { it.command }.toMutableList()
            } else {
                return mutableListOf()
            }
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) return true
        if (args.isEmpty()) {
            HelpCommand().onCommand(sender, command, label, args)
            return true
        }

        when (args[0]) {
            BirthdayCommand.Set.command -> {}
            BirthdayCommand.Greetings.command -> {}
            BirthdayCommand.At.command -> {}
            BirthdayCommand.Gift.command -> {}
            BirthdayCommand.Help.command -> HelpCommand().onCommand(sender, command, label, args)
            BirthdayCommand.GiftSet.command -> GiftSetCommand().onCommand(sender, command, label, args)
            else -> {
                HelpCommand().onCommand(sender, command, label, args)
            }
        }

        return true
    }
}