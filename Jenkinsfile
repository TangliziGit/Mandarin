#!/groovy
pipeline{
    agent any

    environment {
        REPOSITORY="https://github.com/TangliziGit/Mandarin.git"
        SERVICE_DIR="mandarin-runner"

        DOCKER_REGISTRY_HOST="172.29.7.157:85"
        DOCKER_REGISTRY="172.29.7.157:85/dop/mandarin"

        EMAIL_HOST=credentials('email-host')
        EMAIL_PORT=credentials('email-port')
        EMAIL_USERNAME=credentials('email-username')
        EMAIL_PASSWORD=credentials('email-password')
        MYSQL_PASSWORD=credentials('mysql-password')
    }

    stages {
        stage('pull code') {
            steps {
                echo "fetching code from git:${REPOSITORY}"
                deleteDir()
                checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: "${REPOSITORY}"]]])
                script {
                    time = sh(returnStdout: true, script: 'date "+%Y%m%d%H%M"').trim()
                    git_version = sh(returnStdout: true, script: 'git log -1 --pretty=format:"%h"').trim()
                    build_tag = time + git_version
                }
            }
        }

        stage('fill in secrets') {
            steps {
                echo "fill email contents"
                dir(SERVICE_DIR){
                    sh "ls -l src/main/resources/application.properties"
                    sh "sed -i 's/<EMAIL_HOST>/${EMAIL_HOST}/' src/main/resources/application.properties"
                    sh "sed -i 's/<EMAIL_PORT>/${EMAIL_PORT}/' src/main/resources/application.properties"
                    sh "sed -i 's/<EMAIL_USERNAME>/${EMAIL_USERNAME}/' src/main/resources/application.properties"
                    sh "sed -i 's/<EMAIL_PASSWORD>/${EMAIL_PASSWORD}/' src/main/resources/application.properties"
                }

                echo "fill database contents"
                dir(SERVICE_DIR){
                    sh "ls -l src/main/resources/application.properties"
                    sh "sed -i 's/<MYSQL_PASSWORD>/${MYSQL_PASSWORD}/' src/main/resources/application.properties"
                }
            }
        }

        stage('build docker') {
            steps {
                echo "building image"
                echo "image tag : ${build_tag}"
                sh "ls -l"
                sh "docker build -t ${DOCKER_REGISTRY}:${build_tag} ."
            }
        }

       stage('push docker') {
            steps {
                echo "start push image"
                sh "ls -l"
                withCredentials([usernamePassword(credentialsId: 'docker_registry', passwordVariable: 'docker_registryPassword', usernameVariable: 'docker_registryUsername')]) {
                    sh "docker login -u ${docker_registryUsername} -p ${docker_registryPassword} ${DOCKER_REGISTRY_HOST}"
                    sh "docker push ${DOCKER_REGISTRY}:${build_tag}"
                }
            }
        }

        stage('deploy') {
            steps {
                echo "start deploy"
                sh "ls -l"
                sh "docker rm -f mandarin"
                sh "docker run --name mandarin -d --network=host ${DOCKER_REGISTRY}:${build_tag}"
            }
        }
    }
}
