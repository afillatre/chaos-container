package fr.fillatre.testing.chaoscontainer.nutjobs.cpuburner

import fr.fillatre.testing.chaoscontainer.nutjobs.NutJob
import fr.fillatre.testing.chaoscontainer.nutjobs.cpuburner.model.SystemInfo
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * Burns the CPU by increasing the load by step, at the defined interval
 */
@Component
@ConditionalOnProperty(value = ["chaos.cpu-burner.enabled"], havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(CpuBurnerSettings::class)
class CpuBurner(val settings: CpuBurnerSettings) : NutJob {

    private val log = LoggerFactory.getLogger(CpuBurner::class.java)

    @PostConstruct
    fun postConstruct() {
        log.info("Loading Cpu Burner with settings $settings")
    }

    override fun goMad() {
        val systemInfo = SystemInfo.get()
        log.info(systemInfo.toString())

        val stepPercent = (settings.step * 100).toInt()

        for (i in stepPercent..100 step stepPercent) {
            for (j in 0 until systemInfo.getMaxThread()) {
                LoadThread("Load Thread $j", i.toDouble()/100, settings.duration).start()
            }

            Thread.sleep(settings.duration)
        }
    }
}

private class LoadThread(name: String?, val load: Double, val duration: Long) : Thread(name) {

    private val log = LoggerFactory.getLogger(LoadThread::class.java)

    override fun run() {
        log.info("Starting $name")
        val startTime = System.currentTimeMillis()
        var currentTime = startTime
        try {
            // Be sure to stop the program after the given duration
            while (currentTime - startTime < duration) {
                // stop for some time, each 100ms, depending on the current load. The more load, the less stop
                if (currentTime % 100 == 0L) {
                    val sleepingTime = Math.floor((1 - load) * 100).toLong()
                    log.info("$name is sleeping for $sleepingTime ms")
                    Thread.sleep(sleepingTime)
                }
                currentTime = System.currentTimeMillis()
            }
        } catch (e: InterruptedException) {
            log.error(e.message, e)
        }
        log.info("Stopping $name")

    }
}