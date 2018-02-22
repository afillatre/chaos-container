package fr.fillatre.testing.chaoscontainer.nutjobs.cpuburner.model

import java.io.BufferedReader
import java.io.InputStreamReader

data class SystemInfo(val cpus: Int, val threadsByCpu: Int) {
    companion object Factory {
        fun get(): SystemInfo = getSystemInfo()
    }

    fun getMaxThread(): Int {
        return cpus * threadsByCpu
    }

    override fun toString(): String {
        return "SystemInfo(cpus=$cpus, threadsByCpu=$threadsByCpu)"
    }

}

fun getSystemInfo(): SystemInfo {
    val arch = Runtime.getRuntime().exec("uname -a")
    BufferedReader(InputStreamReader(arch.inputStream)).use {
        val line = it.readLine()

        return when {
            line.startsWith("Darwin") -> getDarwinSystemInfo()
            line.startsWith("Linux") -> getLinuxSystemInfo()
            else -> TODO("Not implemented") // Windows ?
        }
    }

}

private fun getLinuxSystemInfo(): SystemInfo {

    // FIXME How to do better, to have a "lateinit" like behavior ?
    var cpus = -1
    var threadsByCpu = -1

    val lscpu = Runtime.getRuntime().exec("lscpu")
    BufferedReader(InputStreamReader(lscpu.inputStream)).use {
        while (true) {
            val line = it.readLine() ?: break

            when {
                line.startsWith("CPU(s):") -> cpus = getLineValue(line).toInt()
                line.startsWith("Thread(s) per core:") -> threadsByCpu = getLineValue(line).toInt()
            }
        }
    }

    when {
        cpus == -1 || threadsByCpu == -1 -> throw UninitializedPropertyAccessException()
    }

    return SystemInfo(cpus, threadsByCpu)
}

private fun getLineValue(line: String) = line.split("\\s+").last()

private fun getDarwinSystemInfo(): SystemInfo {

    // FIXME How to do better, to have a "lateinit" like behavior ?
    var cpus = -1
    val threadsByCpu = 1 // TODO How to get that ?

    val lscpu = Runtime.getRuntime().exec("sysctl -n hw.ncpu")
    BufferedReader(InputStreamReader(lscpu.inputStream)).use {
        cpus = it.readLine().toInt()
    }

    when (cpus) {
        -1 -> throw UninitializedPropertyAccessException()
    }

    return SystemInfo(cpus, threadsByCpu)
}
