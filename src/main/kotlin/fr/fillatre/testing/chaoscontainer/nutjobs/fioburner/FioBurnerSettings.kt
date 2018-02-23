package fr.fillatre.testing.chaoscontainer.nutjobs.fioburner

import org.springframework.boot.context.properties.ConfigurationProperties

// See https://github.com/spring-projects/spring-boot/issues/8762
@ConfigurationProperties("chaos.fio-burner")
class FioBurnerSettings {
    var maxFiles: Int = 100
    var fileSize: Int = 10
    var throughput: Int = 10

    override fun toString(): String {
        return "[maxFiles=$maxFiles, fileSize=$fileSize, throughput=$throughput]"
    }


}