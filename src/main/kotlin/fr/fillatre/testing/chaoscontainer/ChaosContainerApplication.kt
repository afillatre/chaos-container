package fr.fillatre.testing.chaoscontainer

import fr.fillatre.testing.chaoscontainer.nutjobs.NutJob
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ChaosContainerApplication

fun main(args: Array<String>) {
    val log = LoggerFactory.getLogger(ChaosContainerApplication::class.java)

    val context = SpringApplication.run(ChaosContainerApplication::class.java, *args)
    val nutJobs = context.getBeansOfType(NutJob::class.java)

    if (nutJobs.isEmpty()) {
        log.warn("Unable to find any enabled nutjob. Nothing to do !")
    }
    nutJobs.forEach { _, u -> u.goMad() }
}
