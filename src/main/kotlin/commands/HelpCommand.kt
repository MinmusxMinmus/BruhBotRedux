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

package commands

import listeners.GuildCommandListener
import model.Command
import net.dv8tion.jda.api.entities.Message
import other.Logging
import other.logger
import remote.model.ExecutionError
import remote.model.ExecutionEvent
import java.util.*

class HelpCommand(trigger: Message) : Command(trigger), Logging {
    companion object : Logging {
        private var logger = logger()
    }

    override fun declaration() = CommandDeclarations.HELP.getDeclaration()

    override fun execWhenBadArgs() {
        channel.sendMessage("This takes no args, how do you fuck this up").queue()
    }

    override fun execWhenBadPerms() {
        channel.sendMessage("Everyone can use this, how do you fuck this up").queue()
    }

    override fun execCommand() {
        val builder = StringJoiner("\n\n")
        GuildCommandListener.commands.sortedBy { it.name }.forEach {
            val paramBuilder = StringJoiner("> <", "<", ">")
            it.parameters.forEach { paramBuilder.add("${it.first}: ${it.second.name}") }
            builder.add("**${it.name} $paramBuilder**\n- ${it.description}")
            events.add(ExecutionEvent("Added information about command '${it.name}'"))
        }
        // TODO take into account character limit
        author.openPrivateChannel().queue {
            it?.sendMessage(builder.toString())?.queue() ?: events.add(ExecutionError("Unable to send DM to user. Private channel is null? ${it == null}", null))
        }
    }
}