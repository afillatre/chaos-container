package fr.fillatre.testing.chaoscontainer.nutjobs.fioburner

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
@ConditionalOnProperty(value = ["chaos.fio-burner.enabled"], havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(FioBurnerSettings::class)
class FioBurner(val settings: FioBurnerSettings) : NutJob {

    private val log = LoggerFactory.getLogger(FioBurner::class.java)

    private val MEGA_BYTES = 1024 * 1024

    @PostConstruct
    fun postConstruct() {
        log.info("Loading Fio Burner with settings $settings")
    }

    override fun goMad() {
        val byteArray = ByteArray(MEGA_BYTES * settings.throughput)
        byteArray.fill(0, 0, MEGA_BYTES-1)

        for (i in 0 until settings.maxFiles) {
            val tempFile = createTempFile("Fio-burner-")
            log.debug("Writing file ${tempFile.absoluteFile}")

            var startTime = System.currentTimeMillis()
            tempFile.outputStream().use {
                for (j in 0 until settings.fileSize / settings.throughput) {
                    it.write(byteArray)
                    val endTime = System.currentTimeMillis()

                    val diff = endTime - startTime
                    if (diff < 1000) {
                        val waitTime = 1000 - diff
                        log.debug("Wrote ${settings.throughput}MB, sleeping for $waitTime ms")
                        Thread.sleep(waitTime)
                    }
                    startTime = System.currentTimeMillis()
                }
            }
            log.debug("Closing file ${tempFile.absoluteFile}")
        }
    }
}