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

import model.SimpleCommand
import net.dv8tion.jda.api.entities.Message
import remote.model.ParameterError

class ModuleCommand(trigger: Message) : SimpleCommand(trigger) {
    override fun declaration() = CommandDeclarations.MODULEMANAGEMENT.getDeclaration()

    override fun execCommand() {
        channel.sendMessage("trolled").queue()
    }

    override fun execWhenBadArgs() {
        val builder = StringBuilder("Bad args lmao, here's the rundown:\n")

        arguments.filter { it is ParameterError }.forEach { builder.append("/t- \"${it.valueStr}\"") }

        channel.sendMessage(builder.toString()).queue()
    }

    override fun execWhenBadPerms() {
        channel.sendMessage("Lmao no perms").queue()
    }
}