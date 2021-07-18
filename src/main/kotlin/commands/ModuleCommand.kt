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
import model.SimpleCommand
import net.dv8tion.jda.api.entities.Message
import other.Logging
import other.logger
import remote.BBModule
import remote.model.ParameterError
import java.rmi.registry.LocateRegistry

class ModuleCommand(trigger: Message) : SimpleCommand(trigger), Logging {

    companion object : Logging {
        private val logger = logger()
    }

    override fun declaration() = CommandDeclarations.MODULEMANAGEMENT.getDeclaration()

    override fun execCommand() {
        val cmd = arguments[0]
        val module = arguments[1]

        with(LocateRegistry.getRegistry()) {
            when (cmd.valueStr) {
                "load" -> try {
                    val obj = lookup(module.valueStr) as BBModule
                    // For now just dump it all
                    GuildCommandListener.addCommands(obj.commands())
                    channel.sendMessage("Success!").queue()
                } catch (e: Exception) {
                    logger.warn("Unable to find module \"${module.valueStr}\" in RMI registry")
                    channel.sendMessage("Can't find that module").queue()
                }
            }
        }

    }

    override fun execWhenBadArgs() {
        val builder = StringBuilder("Bad args lmao, here's the rundown:\n")

        arguments.filterIsInstance<ParameterError>().forEach { builder.append("/t- \"${it.valueStr}\"") }

        channel.sendMessage(builder.toString()).queue()
    }

    override fun execWhenBadPerms() {
        channel.sendMessage("Lmao no perms").queue()
    }
}