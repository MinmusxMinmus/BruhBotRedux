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

package commands

import model.CommandDeclaration
import remote.model.AdminPermission
import remote.model.NoPermission

enum class CommandDeclarations {
    HELP, MODULEMANAGEMENT;

    fun getDeclaration(): CommandDeclaration {
        return when (this) {
            HELP -> CommandDeclaration("help", "DMs you help", "", HelpCommand::class, NoPermission())
            MODULEMANAGEMENT -> CommandDeclaration("module", "Adds/Removes modules from the bot", "<add/remove> <module name>", ModuleCommand::class, AdminPermission())
        }
    }
}