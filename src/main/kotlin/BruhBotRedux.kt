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

import base.BasicModule
import listeners.GuildCommandListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import org.slf4j.LoggerFactory
import other.ModuleManager
import rmi.BBModule
import rmi.RMIModuleLoader
import java.rmi.registry.LocateRegistry
import java.time.LocalDate
import java.util.*

var token = ""

fun main(args: Array<String>) {
    // Arg check
    if (args.size != 1) {
        println("Format: java -jar BruhBotRedux.jar <Token>")
        return
    }

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

    token = args[0]
    logger.info("Token saved")

    // Create registry
    LocateRegistry.createRegistry(1099)
    logger.info("Registry created at port 1099")

    // Register basic module
    RMIModuleLoader.register(BasicModule())
    logger.info("Basic command module registered")

    // TODO register core functionality in registry. What? What to add? Don't ask me, I don't know

    logger.info("Building JDA")
    val jda = JDABuilder.createDefault(args[0], EnumSet.allOf(GatewayIntent::class.java))
        .setActivity(Activity.playing("with your feelings"))
        .addEventListeners(GuildCommandListener("b!"))
        .build()
        .awaitReady()
    logger.info("JDA built")

    BBModule.jda = jda
    logger.info("Registering basic module")
    ModuleManager.addModule(BasicModule(), false)
    logger.info("Basic module registered")
}