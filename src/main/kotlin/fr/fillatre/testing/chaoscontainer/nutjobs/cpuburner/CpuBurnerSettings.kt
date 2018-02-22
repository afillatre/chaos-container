package fr.fillatre.testing.chaoscontainer.nutjobs.cpuburner

import org.springframework.boot.context.properties.ConfigurationProperties

// See https://github.com/spring-projects/spring-boot/issues/8762
@ConfigurationProperties("chaos.cpu-burner")
class CpuBurnerSettings {
    var step: Double = 0.2
    var duration: Long = 100L

    override fun toString(): String {
        return "[step=$step, duration=$duration]"
    }

}