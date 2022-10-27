node ("docker-light") {
    def sourceDir = pwd()
    try {
        stage("Clean up") {
            step([$class: 'WsCleanup'])
        }
        stage("Checkout Code") {
            checkout scm
        }
        stage("Maven Build") {
//             echo "${env.ALT_URL}"
//             def useUrl = ("${env.ALT_URL}" == "null") ? "${env.BINDING_TEST_URL}" : "${env.ALT_URL}"
//             withEnv(["API_KEY=${env.ROSETTE_API_KEY}", "ALT_URL=${useUrl}"]) {
//                 sh "docker run --rm \
//                                --pull always \
//                                --env API_KEY=${API_KEY} \
//                                --env ALT_URL=${ALT_URL} \
//                                --volume ${sourceDir}:/source maven:eclipse-temurin-17-focal \
//                                bash -c \"pushd /source; mvn -B clean install\""
//             }
            withEnv([]) {
                sh "docker run --rm \
                               --pull always \
                               --volume ${sourceDir}:/source maven:eclipse-temurin-17-focal \
                               bash -c \"pushd /source && \
                                         mvn -B clean install\""
            }
        }
        slack(true)
    } catch (e) {
        currentBuild.result = "FAILED"
        slack(false)
        throw e
    }
}

def slack(boolean success) {
    def color = success ? "#00FF00" : "#FF0000"
    def status = success ? "SUCCESSFUL" : "FAILED"
    def message = status + ": Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
    slackSend(color: color, channel: "#p-n-c_jenkins", message: message)
}
