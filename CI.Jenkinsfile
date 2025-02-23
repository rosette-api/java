node ("docker-light") {
    def sourceDir = pwd()
    try {
        env.JAVA_HOME = "${tool 'java21'}"
        env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        def mavenLocalRepo = "$JENKINS_HOME/maven-local-repositories/executor-$EXECUTOR_NUMBER"
        stage("Clean up") {
            step([$class: 'WsCleanup'])
            sh "rm -rf $mavenLocalRepo"
        }
        stage("Checkout Code") {
            checkout scm
        }
        stage("Build") {
            withMaven(maven: "Basis",
                    mavenLocalRepo: mavenLocalRepo,
                    publisherStrategy: "EXPLICIT") {
                sh "mvn clean verify"
            }

        }
        stage("Test with Docker") {
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
                       eclipse-temurin:21-jdk-noble \
                       bash -c \"apt-get update && \
                             apt-get install -y git && \
                             pushd /source && \
                             git config --global --add safe.directory /source && \
                             /opt/maven-basis/bin/mvn --batch-mode clean install sonar:sonar $mySonarOpts; \
                             maven_ret=\\\$?; \
                             echo && \
                             echo [INFO] Set file permissions to UID and GID of jenkins user for cleanup. && \
                             chown -R 9960:9960 /source && \
                             exit \\\$maven_ret\""
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
