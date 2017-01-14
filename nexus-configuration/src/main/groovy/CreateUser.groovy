import groovy.json.JsonOutput
import org.sonatype.nexus.security.user.User

def role = ['nx-admin']

User jenkinsUser = security.addUser(
        'jenkins',
        'Jenkins',
        'Jenkins',
        'jenkins@signavio.com',
        true,
        'changMe789',
        role
)

log.info("User ${jenkinsUser.name} created")

return JsonOutput.toJson([jenkinsUser])