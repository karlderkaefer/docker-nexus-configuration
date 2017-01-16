# Getting started #
Starting the nexus docker container
```bash
docker-compose up -d
```
After the nexus jetty is up on `http://localhost:8081`, you are able to use the [REST API of Nexus](https://books.sonatype.com/nexus-book/3.0/reference/scripting.html). Gradle will take care of uploading, deleting und executing your scripts.
```bash
cd nexus-configuration
gradlew executeScripts
```
search for results in docker logs
```bash
cd ..
docker logs $container_id
> nexus_1  | creating private maven repository completed successfully
> nexus_1  | 2017-01-14 18:08:53,475+0000 INFO  [qtp152340627-200] admin 
> org.sonatype.nexus.script.plugin.internal.rest.ScriptResource$$EnhancerByGuice$$dc3abf69 - User Jenkins Jenkins created
```
# Scripting with Groovy #
Nexus offers a Groovy Engine to execute configuration script. The gradle project also provides IDE support for the nexus REST API. You can find some script examples [here](https://github.com/sonatype/nexus-book-examples/tree/nexus-3.x/scripting). 
```groovy
import groovy.json.JsonOutput
import org.sonatype.nexus.security.user.User
def role = ['nx-admin']
User jenkinsUser = security.addUser(
        'jenkins',
        'Jenkins',
        'Jenkins',
        'jenkins@example.com',
        true,
        'changMe789',
        role
)
log.info("User ${jenkinsUser.name} created")
return JsonOutput.toJson([jenkinsUser])
```

