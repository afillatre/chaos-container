package fr.fillatre.testing.chaoscontainer.nutjobs.pidburner

import fr.fillatre.testing.chaoscontainer.nutjobs.NutJob
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * Create a given number of thread, over time
 */
@Component
@ConditionalOnProperty(value = ["chaos.pid-burner.enabled"], havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(PidBurnerSettings::class)
class PidBurner(val settings: PidBurnerSettings) : NutJob {

    private val log = LoggerFactory.getLogger(PidBurner::class.java)

    @PostConstruct
    fun postConstruct() {
        log.info("Loading Pid Burner with settings $settings")
    }

    override fun goMad() {
        for (i in 0 until settings.maxThreads) {
            Thread.sleep(settings.duration)
            val threadName = String.format("Burning thread %s", i)
            object : Thread(threadName) {
                override fun run() {
                    try {
                        Thread.sleep(java.lang.Long.MAX_VALUE)
                    } catch (e: InterruptedException) {
                        log.error(e.message, e)
                    }

                }
            }.start()
            log.debug("$threadName started")
        }
    }
}