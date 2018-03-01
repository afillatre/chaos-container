package fr.fillatre.testing.chaoscontainer.nutjobs.logburner

import org.springframework.boot.context.properties.ConfigurationProperties

// See https://github.com/spring-projects/spring-boot/issues/8762
@ConfigurationProperties("chaos.log-burner")
class LogBurnerSettings {
    var maxLogs: Int = 10
    var logsPerSec: Int = 100

    override fun toString(): String {
        return "[maxLogs=$maxLogs, logsPerSec=$logsPerSec]"
    }


}