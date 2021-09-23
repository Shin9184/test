pipeline {
    agent any
    stages {
        stage('Git Progress') {
            steps {
                git branch: 'master', credentialsId: 'Shin9184', url: 'https://github.com/Shin9184/test.git'
            }
        }
        stage('Gradle Build & Junit Test') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew clean build'
                sh 'chmod +x gradlew'
                sh './gradlew test'
            }
        }
        stage('Publish test results') {
            steps {
                junit '**/build/test-results/test/*.xml'
            }
        }
        stage ('Push image') {
            steps {
                script {
                    checkout scm
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                        def customImage = docker.build("tlqkddk123/spring")
                        customImage.push("${env.BUILD_ID}")
                        customImage.push("latest")
                    }
                }
            }
        }

        stage ('Anchore test') {
            steps {
                script {
                    def imageLine = 'tlqkddk123/spring:latest'
                    writeFile file: 'tlqkddk123/spring', text: imageLine
                    anchore name: 'tlqkddk123/spring', engineCredentialsId: 'anchore', bailOnFail: false
                    }
                }
            }
        }
    }
}
