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

data class ExecutionEvent(val exception: Exception?,
                          val timestamp: Instant,
                          val eventInformation: String,
                          val isError: Boolean)

data class CommandInformation(val channel: MessageChannel,
                              val author: User,
                              val guild: Guild,
                              val success: Boolean,
                              val failure: Boolean,
                              val errorMessage: String,)

/**
 * What is the objective with the command class? I want it to:
 * - Be easily extendable by me (adding a new class, for example)
 *   How? Hierarchy
 *
 * - Follow a hierarchy (so I can call the same methods for all commands)
 *   How? Hierarchy
 *
 * - Let a user change the command name
 *   How? Storing command name in persistence
 *
 * - Let a user change the command permissions
 *   How? Storing permissions in persistence
 *
 * What is NOT the objective?
 * - Have users add new commands through the bot
 * - Have users change the command parameters
 * - Have users change the command functionality
 */
abstract class Command(val trigger: Message, val command: String, val help: String, val args: CommandArgs) {

    private val events: MutableList<ExecutionEvent> = mutableListOf()

    val channel
    get() = trigger.channel

    val author
    get() = trigger.author

    val guild
    get() = trigger.guild

    val jda
    get() = trigger.jda

    val success
    get() = events.none { it.isError }

    val failure
    get() = finished && events.last().isError

    var finished = false

    fun events() = events.toList()

    fun addEvent(exception: Exception?, timestamp: Instant, message: String, isError: Boolean) = ExecutionEvent(exception, timestamp, message, isError)

    fun details() = CommandInformation(channel, author, guild, success, failure, events.last().eventInformation)

    fun structure(prefix: String) = "$prefix$command ${args.concat()}"

    abstract fun execute() : Boolean
}