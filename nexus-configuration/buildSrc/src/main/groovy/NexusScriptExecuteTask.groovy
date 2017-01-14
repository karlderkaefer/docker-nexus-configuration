import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

/**
 * Created by Karl on 14.01.2017.
 */
class NexusScriptExecuteTask extends AbstractNexusTask {

    @InputFiles
    Set<File> files

    @TaskAction
    void run() {
        files.each { scriptFile ->
            runScript(scriptFile)
        }
    }

    void runScript(File scriptFile) {
        String scriptName = scriptFile.getName() - '.groovy'
        HTTPBuilder http = super.getDefaultHttp()
        println "execute script '$scriptName' on nexus"
        http.setUri("${http.uri}/$scriptName/run".toString())
        http.headers.'Content-Type' = 'text/plain'
        println "do post to '${http.getUri()}'"
        http.request(Method.POST) {
            response.success = { resp, content ->
                println "Success! script '$scriptName' executed ${resp.status}"
                println content.text
            }
            response.failure = { resp, content ->
                println "Request failed with status ${resp.statusLine}"
                println content.text
            }
        }
    }
}
