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
            withSonarQubeEnv {
                sh "docker run --rm \
                               --pull always \
                               --volume ${sourceDir}:/source \
                               --volume /opt/maven-basis:/opt/maven-basis \
                               --volume /bin/git:/bin/git \
                               eclipse-temurin:17-jdk-focal \
                               bash -c \"pushd /source && \
                                         /opt/maven-basis/bin/mvn --batch-mode clean install && \
                                         /opt/maven-basis/bin/mvn sonar:sonar \
                                                                  -Dsonar.login=${env.SONAR_AUTH_TOKEN} \
                                                                  -Dsonar.host.url=${env.SONAR_HOST_URL}\""
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
