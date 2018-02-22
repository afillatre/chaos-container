# Chaos Container

This Spring-boot-based project helps you test how you environment reacts to deficient
containers. Does it takes the whole system down, or does it get killed at some point ?

This container can be configured to misbehave :
 * burn the CPU
 * take all the RAM
 * spawn lots of threads (i.e. pids)
 * etc.
 
## Build
In order to build the container, launch an `mvn package` command. This will create a jar,
alongside with a docker image. By default, the docke rimage prefix will be your username,
but you can override it by giving the mven command the following property: `-Ddocker.image.prefix=<a_prefix>`

## Run
Run the image with the following command: `docker run [--rm] <my_image>`.

Optionally, you can set the `JAVA_ARGS` environment variable to pass more JMV arguments. This
is useful to activate specific nutjobs.

Example: `docker run --rm -e JAVA_ARGS="-Dchaos.ram-burner.enabled=true -Xms1G -Xmx1G" afillatre/chaos-container:0.0.1`