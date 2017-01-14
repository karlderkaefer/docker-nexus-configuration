import groovyx.net.http.*
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

/**
 * Created by Karl on 14.01.2017.
 */
class NexusScriptUploadTask extends AbstractNexusTask {

    @InputFiles
    Set<File> files

    @TaskAction
    void upload() {
        files.each { scriptFile ->
            uploadScript(scriptFile)
        }
    }

    void uploadScript(File scriptFile) {
        String scriptName = scriptFile.getName() - '.groovy'
        HTTPBuilder http = super.getDefaultHttp()
        println "uploading script '$scriptName' to nexus"
        println "do post to '${http.getUri()}'"
        http.request(Method.POST) {
            body = [name: scriptName, type: 'groovy', content: scriptFile.text]
            requestContentType = ContentType.JSON
            response.success = { resp ->
                println "Success! script '$scriptName' uploaded ${resp.status}"
            }
            response.failure = { resp, content ->
                println "Request failed with status ${resp.statusLine}"
                if (content.text.contains('found duplicated key')) {
                    println "script '$scriptName' already exists"
                }
            }
        }
    }
}
