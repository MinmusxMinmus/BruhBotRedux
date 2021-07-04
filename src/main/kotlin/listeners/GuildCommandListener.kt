/*
 * Copyright © 2021 MinmusxMinmus. This file is part of "BruhBot"
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

import commands.CommandDeclarations
import model.CommandDeclaration
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import other.Logging
import other.logger

class GuildCommandListener(var prefix: String) : ListenerAdapter(), Logging {
    companion object : Logging {
        private val commandSet = mutableSetOf(CommandDeclarations.HELP.getDeclaration(), CommandDeclarations.MODULEMANAGEMENT.getDeclaration())
        val commands: Set<CommandDeclaration>
        get() = commandSet.toSet()
        private val logger = logger()
    }


    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        logger.debug("Message received in guild '${event.guild.name}' (${event.guild.id})")
        logger.debug("Message channel: '${event.channel.name}' (${event.channel.id})")
        logger.debug("Message author: '${event.author.name}' (${event.author.id})")

        if (event.message.contentRaw.startsWith(prefix)) {
            logger.debug("Message contained prefix '${prefix}', should be a command")

            // Parse command and shit
            val begin = prefix.length
            val end =
                if (event.message.contentRaw.indexOf(' ') == -1) event.message.contentRaw.length
                else event.message.contentRaw.indexOf(' ')
            val strippedCommand = event.message.contentRaw.substring(begin, end)
            logger.debug("Message's command is '$strippedCommand'")

            commandSet.find { strippedCommand.equals(it.name) }?. let {
                logger.debug("Command '$strippedCommand' found")

                // Finds the constructor that only takes the trigger, and creates the command. If there's no such
                // constructor, it will print an error message and ignore the command.
                val command = it.command.constructors
                    .filter { it.parameters.size == 1 && it.parameters.first().name.equals("trigger") }
                    .firstOrNull()?.call(event.message)

                command?.run {
                    logger.info("Executing command '$strippedCommand'")
                    execute()
                    logger.info("Command '$strippedCommand' executed")
                    if (command.failure) {
                        logger.warn("Command '$strippedCommand' failed execution. See log for more information.")
                        logger.debug("Failure reason: ${command.details().errorMessage}")
                        // Complete error trace
                        // TODO edit library to allow seeing the events
                    }
                } ?: run {
                    logger.error("Command '$strippedCommand' had no valid constructor, unable to execute (must have a single argument constructor with field 'trigger')")
                    return@let
                }
            }
        }
    }
}