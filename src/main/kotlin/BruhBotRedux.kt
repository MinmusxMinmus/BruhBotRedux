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

import commands.ArgumentTypes
import commands.CommandArgument
import commands.concat

fun main(args: Array<String>) {
    println("""BruhBot Copyright (C) 2021 MinmusxMinmus
    |This program comes with ABSOLUTELY NO WARRANTY.
    |This is free software, and you are welcome to redistribute it under certain conditions.
    |For additional details, see "GPL.txt" in the GitHub repository: 
    |https://github.com/MinmusxMinmus/BruhBotRedux.
    |""".trimMargin())

    val temp: List<CommandArgument> = listOf(CommandArgument(ArgumentTypes.USER_ID, "User ID"), CommandArgument(ArgumentTypes.TEXT, "Desc2"))
    println(temp.concat())
}