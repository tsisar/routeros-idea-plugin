package com.mikrotik.routeros

object RouterOSKeywords {
    val commands: Set<String> = load("commands.txt")
    val paths: Set<String> = load("paths.txt")
    val properties: Set<String> = load("properties.txt")

    private fun load(name: String): Set<String> {
        val stream = RouterOSKeywords::class.java
            .getResourceAsStream("/com/mikrotik/routeros/$name") ?: return emptySet()
        return stream.bufferedReader(Charsets.UTF_8).use { reader ->
            reader.lineSequence()
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toHashSet()
        }
    }
}
