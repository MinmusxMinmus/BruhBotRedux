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

package persistence

import model.Quote
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import persistencelib.Atom
import persistencelib.StorageManager
import persistencelib.Version
import java.io.IOException
import java.time.OffsetDateTime
import java.util.*

typealias AtomMap<T> = MutableMap<String, T>
typealias QuoteSet = MutableSet<Any>

const val ANSWER_ATOM = "ANSWER"
const val REACTION_ATOM = "REACTION"
const val IDENTIFIER_ATOM = "ID"
const val QUOTE_ATOM = "QUOTE"
const val JSON_ATOM = "JSON"
const val MEMBER_ATOM = "MEMBER"

enum class DataManagementProcessor {
    INSTANCE;

    val identifiers: AtomMap<String> = mutableMapOf()
    val answers: AtomMap<String> = mutableMapOf()
    val reactions: AtomMap<String> = mutableMapOf()
    val members: AtomMap<MutableSet<String>> = mutableMapOf()
    val objects: AtomMap<JSONObject> = mutableMapOf()
    val quotes: QuoteSet = mutableSetOf()

    lateinit var manager: StorageManager

    fun addStr(col: AtomMap<String>, atom: Atom) = atom.items?.forEach {
        col[it.toString()] = atom.getItem(it)?.iterator()?.next() ?: return@forEach
    }

    fun addQuotes(atom: Atom) {
        atom.items?.forEach {
            val l: List<String> = LinkedList(atom.getItem(it))
            quotes += Quote(l[0], l[1], l[2], OffsetDateTime.parse(l[3]), it.toString())
        }
    }

    fun addJSON(atom: Atom) {
        val parser = JSONParser()
        var obj = JSONObject()
        atom.items?.forEach {
            try {
                obj = parser.parse(atom.getItem(it).iterator().next()) as JSONObject
                objects[it.toString()] = obj
            } catch (e: ParseException) {
                println("WARNING: Invalid JSON object found:\n$obj\nTrace below:\n${e.stackTraceToString()}")
            }
        }
    }

    fun addMember(atom: Atom) = atom.items?.forEach {
        members.put(it.toString(), HashSet(atom.getItem(it)))
    }

    fun init(filename: String) {
        try {
            manager = StorageManager(filename, Version.V100)
        } catch (e: IOException) {
            println("Unable to open $filename: IO error. Trace below:\n${e.stackTraceToString()}")
            return
        }

        var atom: Atom?
        // Check if region exists first, adds if not
        if (!manager.hasRegion(IDENTIFIER_ATOM)) manager.addRegion(IDENTIFIER_ATOM)
        atom = manager.getRegion(IDENTIFIER_ATOM)
        addStr(identifiers, atom)

        // Check if region exists first, adds if not
        if (!manager.hasRegion(ANSWER_ATOM)) manager.addRegion(ANSWER_ATOM)
        atom = manager.getRegion(ANSWER_ATOM)
        addStr(answers, atom)

        // Check if region exists first, adds if not

        // Check if region exists first, adds if not
        if (!manager.hasRegion(REACTION_ATOM)) manager.addRegion(REACTION_ATOM)
        atom = manager.getRegion(REACTION_ATOM)
        addStr(reactions, atom)

        // Check if region exists first, adds if not

        // Check if region exists first, adds if not
        if (!manager.hasRegion(QUOTE_ATOM)) manager.addRegion(QUOTE_ATOM)
        atom = manager.getRegion(QUOTE_ATOM)
        addQuotes(atom)

        // Check if region exists first, adds if not

        // Check if region exists first, adds if not
        if (!manager.hasRegion(JSON_ATOM)) manager.addRegion(JSON_ATOM)
        atom = manager.getRegion(JSON_ATOM)
        addJSON(atom)

        // Check if region exists first, adds if not

        // Check if region exists first, adds if not
        if (!manager.hasRegion(MEMBER_ATOM)) manager.addRegion(MEMBER_ATOM)
        atom = manager.getRegion(MEMBER_ATOM)
        addMember(atom)
    }



}