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

import listeners.GuildCommandListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import org.slf4j.LoggerFactory
import java.security.Permission
import java.security.Policy
import java.security.ProtectionDomain
import java.time.LocalDate
import java.util.*


fun main(args: Array<String>) {
    // Arg check
    if (args.size != 1) {
        println("Bad arguments: include the bot token (and only that!)")
        return
    }

    // Security manager shenanigans
    val allPermissionPolicy = object : Policy() {
        override fun implies(domain: ProtectionDomain?, permission: Permission?) = true
    }
    Policy.setPolicy(allPermissionPolicy)
    System.getSecurityManager() ?: System.setSecurityManager(SecurityManager())

    // Copyright shenanigans
    println("""BruhBot Copyright (C) 2021 MinmusxMinmus
    |This program comes with ABSOLUTELY NO WARRANTY.
    |This is free software, and you are welcome to redistribute it under certain conditions.
    |For additional details, see "GPL.txt" in the GitHub repository: 
    |https://github.com/MinmusxMinmus/BruhBotRedux/blob/master/GPL.txt
    |""".trimMargin())

    // Inicialization

    // Log beginning
    val logger = LoggerFactory.getLogger("BruhBotRedux")
    logger.info("--------------------------------------------------")
    logger.info("----------------BruhBot Redux begin---------------")
    logger.info("-----------------Date: ${LocalDate.now()}-----------------")
    logger.info("--------------------------------------------------")

    // TODO register core functionality in registry. What? What to add? Don't ask me, I don't know

    val jda = JDABuilder.createDefault(args[0], EnumSet.allOf(GatewayIntent::class.java))
        .setActivity(Activity.playing("with your feelings"))
        .addEventListeners(GuildCommandListener("b!"))
        .build()
        .awaitReady()

}