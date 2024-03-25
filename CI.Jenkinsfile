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
                mySonarOpts="-Dsonar.login=${env.SONAR_AUTH_TOKEN} -Dsonar.host.url=${env.SONAR_HOST_URL}"
                 if ("${env.CHANGE_BRANCH}" != "null") {
                     mySonarOpts="$mySonarOpts -Dsonar.pullrequest.key=${env.CHANGE_ID} -Dsonar.pullrequest.base=${env.CHANGE_TARGET} -Dsonar.pullrequest.branch=${env.CHANGE_BRANCH}"
                }
                echo("Sonar Options are: $mySonarOpts")
                sh "docker run --rm \
                       --pull always \
                       --volume ${sourceDir}:/source \
                       --volume /opt/maven-basis:/opt/maven-basis \
                       eclipse-temurin:17-jdk-focal \
                       bash -c \"apt-get update && \
                             apt-get install -y git && \
                             pushd /source && \
                             /opt/maven-basis/bin/mvn --batch-mode clean install sonar:sonar $mySonarOpts && \
                             echo && \
                             echo [INFO] Set file permissions to UID and GID of jenkins user for cleanup. && \
                             chown -R 9960:9960 /source\""
            }
        }
        postToTeams(true)
    } catch (e) {
        currentBuild.result = "FAILED"
        postToTeams(false)
        throw e
    }
}

def postToTeams(boolean success) {
    def webhookUrl = "${env.TEAMS_PNC_JENKINS_WEBHOOK_URL}"
    def color = success ? "#00FF00" : "#FF0000"
    def status = success ? "SUCCESSFUL" : "FAILED"
    def message = "*" + status + ":* '${env.JOB_NAME}' - [${env.BUILD_NUMBER}] - ${env.BUILD_URL}"
    office365ConnectorSend(webhookUrl: webhookUrl, color: color, message: message, status: status)
}
