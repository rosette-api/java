node ("docker-light") {
    def SOURCEDIR = pwd()
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
            echo "${env.ALT_URL}"
            def useUrl = ("${env.ALT_URL}" == "null") ? "${env.BINDING_TEST_URL}" : "${env.ALT_URL}"
            withEnv(["API_KEY=${env.ROSETTE_API_KEY}", "ALT_URL=${useUrl}"]) {
                sh "docker run --rm -e API_KEY=${API_KEY} -e ALT_URL=${ALT_URL} -v ${SOURCEDIR}:/source rosette/docker-java"
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
