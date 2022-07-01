package com.altaie.domain.utils

import kotlin.math.log10
import kotlin.math.pow

fun Long.convertNumber(): String {
    if (this < 1000) {
        return this.toString()
    }
    val exp = (log10(this.toDouble()) / log10(1000.0)).toInt()
    return String.format("%.1f%c", this / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
}

fun Long.convertToSize(): String {
    if (this < 1024) {
        return this.toString() + "KB"
    }
    val exp = (log10(this.toDouble()) / log10(1024.0)).toInt()
    return String.format("%.1f%cB", this / 1024.0.pow(exp.toDouble()), "KMGTPE"[exp - 1])
}

fun Regex.search(string: String) = find(string)?.groups?.last()?.value