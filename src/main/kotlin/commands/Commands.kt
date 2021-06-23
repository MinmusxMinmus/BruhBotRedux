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

abstract class Command(val trigger: Message,) {

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

    abstract fun execute()
}