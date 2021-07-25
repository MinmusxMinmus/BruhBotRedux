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

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import other.ModuleManager
import shared.Logging
import shared.logger

class GuildCommandListener(var prefix: String) : ListenerAdapter(), Logging {
    companion object : Logging {
        private val logger = logger()
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        logger.debug("Message received in guild '${event.guild.name}' (${event.guild.id})")
        logger.debug("Message channel: '${event.channel.name}' (${event.channel.id})")
        logger.debug("Message author: '${event.author.name}' (${event.author.id})")

        if (event.message.contentRaw.startsWith(prefix)) {
            logger.debug("Message contained prefix '${prefix}'. Parsing")

            // Parse command and shit
            val begin = prefix.length
            val end =
                if (event.message.contentRaw.indexOf(' ') == -1) event.message.contentRaw.length
                else event.message.contentRaw.indexOf(' ')
            val strippedCommand = event.message.contentRaw.substring(begin, end)
            logger.debug("Message's alleged command is '$strippedCommand'. Searching")
            ModuleManager.getSimpleCommand(strippedCommand)?.let {
                val modulename = ModuleManager.getModule(strippedCommand)?.name();
                logger.debug("Command '$strippedCommand' found in module '$modulename'. Executing")
                ModuleManager.executeCommand(it, event.message)
                logger.info("Command '$strippedCommand' executed")
                logger.debug("(Refers to command from module '$modulename')")
            } ?: logger.info("Command '$strippedCommand' not found. Cannot execute")
        }
    }
}