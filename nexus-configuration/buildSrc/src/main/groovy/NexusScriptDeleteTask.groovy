import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

/**
 * Created by Karl on 14.01.2017.
 */
class NexusScriptDeleteTask extends AbstractNexusTask {

    @InputFiles
    Set<File> files

    @TaskAction
    void delete() {
        files.each { scriptFile ->
            deleteScript(scriptFile)
        }
    }

    void deleteScript(File scriptFile) {
        String scriptName = scriptFile.getName() - '.groovy'
        HTTPBuilder http = defaultHttp
        println "uploading script '$scriptName' to nexus"
        http.setUri("${http.uri}/$scriptName".toString())
        println "do delete to '${http.getUri()}'"
        http.request(Method.DELETE) {
            response.success = { resp ->
                println "Success! script '$scriptName' deleted ${resp.status}"
            }
            response.failure = { resp, content ->
                println "Request failed with status ${resp.statusLine}"
                println content.text
            }
        }
    }
}
