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

package other

import net.dv8tion.jda.api.entities.Message
import rmi.BBModule
import rmi.MessageOrigin
import simpleCommands.SimpleCommandDeclaration
import token

object ModuleManager {
    private val SimpleCommandMap = mutableMapOf<BBModule, MutableMap<String, SimpleCommandDeclaration>>()

    val commands get() = SimpleCommandMap.flatMap { it.value.values }.sortedBy { it.name }

    fun addModule(module: BBModule, buildJDA: Boolean) {
        SimpleCommandMap[module] = mutableMapOf()
        module.commands().forEach { SimpleCommandMap[module]?.set(it.name, it) }
        module.buildJDA(token)
    }

    /**
     * Returns the [SimpleCommandDeclaration] associated to this command name. Returns `null` if no such command exists
     */
    fun getSimpleCommand(commandName: String): SimpleCommandDeclaration? {
        return SimpleCommandMap.filter { it.value.containsKey(commandName) }.values.first()[commandName]
    }

    fun getModule(commandName: String): BBModule? {
        return SimpleCommandMap.filter { it.value.containsKey(commandName) }.keys.firstOrNull()
    }

    fun executeCommand(declaration: SimpleCommandDeclaration, message: Message) {
        SimpleCommandMap.filter { it.value.containsKey(declaration.name) }.keys.firstOrNull()?.executeSimpleCommand(declaration, MessageOrigin.from(message))
    }
}