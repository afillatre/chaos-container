package fr.fillatre.testing.chaoscontainer.nutjobs

/**
 * Describe a job that will try to break the platform. Multiple jobs can be run in parallel
 */
interface NutJob {
    fun goMad()
}