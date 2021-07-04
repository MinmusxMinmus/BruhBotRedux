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
import java.util.*

class HelpCommand(override val trigger: Message) : Command(trigger) {
    override fun declaration() = CommandDeclarations.HELP.getDeclaration()

    override fun exec() {
        val builder = StringJoiner("\n")
        GuildCommandListener.commands.sortedBy { it.name }.forEach { builder.add("**${it.name} ${it.parameters}**\n- ${it.description}") }
        author.openPrivateChannel().queue { it?.sendMessage(builder.toString())?.queue() }
    }
}