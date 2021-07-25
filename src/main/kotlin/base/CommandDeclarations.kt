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

package base

import shared.AdminPermission
import shared.KeywordParameter
import shared.NoPermission
import shared.StringParameter
import simpleCommands.SimpleCommandDeclaration

enum class CommandDeclarations {
    HELP, MODULEMANAGEMENT;

    fun getDeclaration(): SimpleCommandDeclaration {
        return when (this) {
            HELP -> SimpleCommandDeclaration(
                name = "help",
                description = "DMs you help",
                parameters = listOf(),
                permission = NoPermission()
            )
            MODULEMANAGEMENT -> SimpleCommandDeclaration(
                name = "module",
                description = "Adds/Removes modules from the bot",
                parameters = listOf("Mode" to KeywordParameter(setOf("load", "list")), "Module name" to StringParameter(canContainSpaces = false)),
                permission = AdminPermission()
            )
        }
    }
}