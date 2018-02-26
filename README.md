# Chaos Container

This Spring-boot-based project helps you test how you environment reacts to deficient
containers. Does it takes the whole system down, or does it get killed at some point ?

This container can be configured to misbehave :
 * burn the CPU
 * take all the RAM
 * spawn lots of threads (i.e. pids)
 * etc.
 
## Build
In order to build the container, launch an `mvn clean package` command. This will create a jar,
alongside with a docker image. You can configure some things during the image creation (by
passing parameters with the`-D` option):
* `-Ddocker.image.base=<base_image_name`: the base image to build your own image from. Should
be at least a Java runtime 8+ (alpine and slim versions seem to work as well)
* `-Ddocker.image.name=<final_image_name`: the name you want for your image. By default, it's
the artifact's name
* `-Ddocker.image.prefix=<a_prefix>`: the image prefix - useful if you want to push it in
a custom registry. By default, it's your env username

## Run
Run the image with the following command: `docker run [--rm] <my_image>`.

Optionally, you can set the `JAVA_ARGS` environment variable to pass more JMV arguments. This
is useful to activate specific nutjobs.

Example: `docker run --rm -e JAVA_ARGS="-Dchaos.ram-burner.enabled=true -Xms1G -Xmx1G" afillatre/chaos-container:0.0.1`

## Configuration
The configuration can be found [here](https://github.com/afillatre/chaos-container/blob/master/src/main/resources/application.yml).
The explanation of each param can be found [here](https://github.com/afillatre/chaos-container/blob/master/src/main/resources/META-INF/additional-spring-configuration-metadata.json)

In order to change the configuration (i.e. unable jobs, and change default values), you have
several possible ways:
* pass jvm arg with `-Dconfig.key=value` (using the `JAVA_ARGS` env variable for Docker)
* use any of the [Spring boot ways](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
of providing external configuration