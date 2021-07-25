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

package base/*
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

import base.simpleCommands.HelpCommand
import base.simpleCommands.ModuleCommand
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.requests.GatewayIntent
import rmi.BBModule
import simpleCommands.SimpleCommand
import simpleCommands.SimpleCommandDeclaration
import java.util.*

class BasicModule: BBModule {
    override fun commands() = setOf(CommandDeclarations.HELP.getDeclaration(), CommandDeclarations.MODULEMANAGEMENT.getDeclaration())

    override fun getCommand(declaration: SimpleCommandDeclaration, trigger: Message): SimpleCommand =
        when (declaration.name) {
            CommandDeclarations.HELP.getDeclaration().name -> HelpCommand(trigger)
            CommandDeclarations.MODULEMANAGEMENT.getDeclaration().name -> ModuleCommand(trigger)
            else -> HelpCommand(trigger)
        }

    override fun intents(): Collection<GatewayIntent> = EnumSet.allOf(GatewayIntent::class.java)

    override fun name() = "BASE"
}