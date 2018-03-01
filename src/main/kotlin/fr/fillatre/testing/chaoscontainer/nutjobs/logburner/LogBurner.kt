package fr.fillatre.testing.chaoscontainer.nutjobs.logburner

import fr.fillatre.testing.chaoscontainer.nutjobs.NutJob
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * Create files on the FS over time
 */
@Component
@ConditionalOnProperty(value = ["chaos.log-burner.enabled"], havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(LogBurnerSettings::class)
class LogBurner(val settings: LogBurnerSettings) : NutJob {

    private val log = LoggerFactory.getLogger(LogBurner::class.java)

    @PostConstruct
    fun postConstruct() {
        log.info("Loading Log Burner with settings $settings")
    }

    override fun goMad() {

        var i = 0

        while (i < settings.maxLogs * 1000) {

            val startTime = System.currentTimeMillis()
            for (j in 0 until settings.logsPerSec) {
                log.info("Generating log ${++i}")
            }
            val endTime = System.currentTimeMillis()

            val diff = endTime - startTime
            if (diff < 1000) {
                val waitTime = 1000 - diff
                Thread.sleep(waitTime)
            }
        }
    }
}