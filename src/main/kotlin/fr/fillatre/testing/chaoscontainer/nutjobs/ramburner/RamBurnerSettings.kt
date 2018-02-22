package fr.fillatre.testing.chaoscontainer.nutjobs.pidburner

import org.springframework.boot.context.properties.ConfigurationProperties

// See https://github.com/spring-projects/spring-boot/issues/8762
@ConfigurationProperties("chaos.ram-burner")
class RamBurnerSettings {
    var maxMemory: Int = 100
    var duration: Long = 100L

    override fun toString(): String {
        return "[maxMemory=$maxMemory, duration=$duration]"
    }


}