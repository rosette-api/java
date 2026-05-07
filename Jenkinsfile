node ("docker-light") {
    def SOURCE_DIR = pwd()
    def LOCAL_DOCKER_IMAGE = "rosette/jenkins-java:jenkins-${env.BUILD_NUMBER}"
    try {
        env.JAVA_HOME = "${tool 'java25'}"
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
        stage("Build Docker Image") {
            sh "docker build -f jenkins.Dockerfile -t ${LOCAL_DOCKER_IMAGE} ."
        }
        stage("Test with Docker") {
            echo "${env.ALT_URL}"
            def useUrl = ("${env.ALT_URL}" == "null") ? "${env.BINDING_TEST_URL}" : "${env.ALT_URL}"
            withEnv(["API_KEY=${env.ROSETTE_API_KEY}", "ALT_URL=${useUrl}"]) {
                sh "docker run --rm -e API_KEY=${API_KEY} -e ALT_URL=${ALT_URL} -v ${SOURCE_DIR}:/source ${LOCAL_DOCKER_IMAGE}"
            }
        }
        postToTeams(true)
    } catch (e) {
        currentBuild.result = "FAILED"
        postToTeams(false)
        throw e
    } finally {
        sh "docker image rm ${LOCAL_DOCKER_IMAGE} || true"
    }
}

def postToTeams(boolean success) {
    def webhookUrl = "${env.TEAMS_PNC_JENKINS_WEBHOOK_URL}"
    def color = success ? "#00FF00" : "#FF0000"
    def status = success ? "SUCCESSFUL" : "FAILED"
    def message = "*" + status + ":* '${env.JOB_NAME}' - [${env.BUILD_NUMBER}] - ${env.BUILD_URL}"
    office365ConnectorSend(webhookUrl: webhookUrl, color: color, message: message, status: status)
}
