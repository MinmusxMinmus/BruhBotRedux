/*
 * Copyright © 2021. This file is part of "BruhBot"
 * "BruhBot" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * "BruhBot" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with "BruhBot".  If not, see <https://www.gnu.org/licenses/>.
 */

package listeners

import commands.HelpCommand
import model.CommandDeclaration
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildCommandListener(var prefix: String) : ListenerAdapter()  {

    val commands = mutableListOf(CommandDeclaration("help", "DMs you help", "(None)", HelpCommand::class))

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.message.contentRaw.startsWith(prefix)) {
            // Parse command and shit
            val begin = prefix.length
            val end =
                if (event.message.contentRaw.indexOf(' ') == -1) event.message.contentRaw.length
                else event.message.contentRaw.indexOf(' ')
            val strippedCommand = event.message.contentRaw.substring(begin, end)

            commands.find { strippedCommand.equals(it.name) }?. let {
                // Finds the constructor that only takes the trigger, and creates the command. If there's no such
                // constructor, it will print an error message and ignore the command.
                val command = it.command.constructors
                    .filter { it.parameters.size == 1 && it.parameters.first().name.equals("trigger") }
                    .firstOrNull()?.call(event.message)

                command.run {

                    if (this == null) {
                                println("Command \"${it}\" has no trigger-only constructor. Discarding...")
                                return@let
                            }
                    println("Executing?")
                    execute()
                }
            }


        }
    }
}