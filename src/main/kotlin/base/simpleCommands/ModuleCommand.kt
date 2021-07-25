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

package base.simpleCommands

import base.CommandDeclarations
import net.dv8tion.jda.api.entities.Message
import other.ModuleManager
import rmi.BBModule
import shared.Logging
import shared.ParameterError
import shared.logger
import simpleCommands.SimpleCommand
import java.rmi.NotBoundException
import java.rmi.registry.LocateRegistry
import java.util.*

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
                    logger.info("Command was called in mode 'load'")
                    logger.debug("Searching for module in registry")
                    val obj = lookup(module.valueStr) as BBModule
                    logger.debug("Building JDA and adding module")
                    ModuleManager.addModule(obj, true)
                    logger.info("Success loading module '${module.valueStr}'")
                    channel.sendMessage("Success!").queue()
                    // TODO better exception handling
                } catch (e: NotBoundException) {
                    logger.warn("Unable to find module \"${module.valueStr}\" in RMI registry")
                    logger.warn("Error: ${e.stackTraceToString()}")
                    channel.sendMessage("Can't find the module. Check logs for more details.").queue()
                } catch (e: Exception) {
                    logger.warn("Unable to load module \"${module.valueStr}\" from RMI registry")
                    logger.warn("Trace: \n${e.stackTraceToString()}")
                    channel.sendMessage("Can't load the module, for some reason. Check logs for more details.").queue()
                }
                "list" -> try {
                    logger.info("Command was called in mode 'list'")
                    // TODO prettify
                    channel.sendMessage(Arrays.toString(list())).queue()
                } catch (e: Exception) {
                    logger.warn("Exception raised while executing command")
                    logger.warn("Trace: ${e.stackTraceToString()}")
                }
            }
        }

    }

    override fun execWhenBadArgs() {
        val builder = StringBuilder("Bad args lmao, here's the rundown:\n")

        arguments.filterIsInstance<ParameterError>().forEach { builder.append("/t- \"${it.valueStr}\"") }
        builder.append("\n\n")
        events.forEach { builder.append("${it.timestamp} : ${it.info}") }

        channel.sendMessage(builder.toString()).queue()
    }

    override fun execWhenBadPerms() {
        channel.sendMessage("Lmao no perms").queue()
    }
}