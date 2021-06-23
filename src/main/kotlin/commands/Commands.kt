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

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import java.time.Instant

data class ExecutionEvent(val exception: Exception,
                          val timestamp: Instant,
                          val eventInformation: String,
                          val isError: Boolean)

data class CommandInformation(val channel: MessageChannel,
                              val author: User,
                              val guild: Guild,
                              val success: Boolean,
                              val failure: Boolean,
                              val errorMessage: String,)

sealed class Command(val trigger: Message, val command: String, val help: String, val args: CommandArgs) {

    protected val events: MutableList<ExecutionEvent> = mutableListOf()

    val channel = trigger.channel
    val author = trigger.author
    val guild = trigger.guild
    val jda = trigger.jda

    lateinit var executionStart: Instant
    var finished = false

    val success: Boolean
        get() = events.none { it.isError }

    val failure: Boolean
        get() = finished && events.last().isError

    fun details() = CommandInformation(channel, author, guild, success, failure, events.last().eventInformation)

    fun structure(prefix: String) = "$prefix$command ${args.concat()}"

    abstract fun execute()
}