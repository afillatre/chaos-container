package fr.fillatre.testing.chaoscontainer.nutjobs.ramburner

import fr.fillatre.testing.chaoscontainer.nutjobs.NutJob
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

/**
 * Consume the RAM over time, to a defined amount.
 * <br>
 * <b>Note:</b> You may need to set the -Xms and -Xmx values for better results
 */
@Component
@ConditionalOnProperty(value = ["chaos.ram-burner.enabled"], havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(RamBurnerSettings::class)
class RamBurner(val settings: RamBurnerSettings) : NutJob {

    private val log = LoggerFactory.getLogger(RamBurner::class.java)
    private val HUNDRED_MEGA_BYTES = 100 * 1024 * 1024

    @PostConstruct
    fun postConstruct() {
        log.info("Loading Ram Burner with settings $settings")
    }

    override fun goMad() {
        val v = Vector<ByteArray>()
        while (v.size < settings.maxMemory / 100) {
            val b = ByteArray(HUNDRED_MEGA_BYTES)
            v.add(b)
            val rt = Runtime.getRuntime()
            log.debug("Free memory: " + rt.freeMemory())
            Thread.sleep(settings.duration)
        }
    }
}