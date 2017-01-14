import groovyx.net.http.HTTPBuilder
import org.gradle.api.DefaultTask

/**
 * Created by Karl on 14.01.2017.
 */
class AbstractNexusTask extends DefaultTask {

    AbstractNexusTask() {
        group = 'nexus'
    }

    HTTPBuilder getDefaultHttp() {
        String url = "${project.properties.host}/service/siesta/rest/v1/script"
        String username = project.properties.username
        String password = project.properties.password
        String basicAuth = "$username:$password".bytes.encodeBase64().toString()
        def http = new HTTPBuilder(url)
        http.headers.'Authorization' = "Basic $basicAuth"
        return http
    }
}
