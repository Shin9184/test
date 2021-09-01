// pipeline {
//     agent any
//     stages {
//         stage('Git Progress') {
//             steps {
//                 git  branch: 'master', credentialsId: 'Shin9184', url: 'https://github.com/Shin9184/test.git'
//             }
//         }
//         stage('Gradle Build & Junit Test') {
//             steps {
//                 sh 'chmod +x ./gradlew'
//                 sh './gradlew clean build'
//                 sh 'chmod +x gradlew' 
//                 sh './gradlew test'
//             }
//         }
//         stage('Publish test results') {
//             steps {
//                 junit '**/build/test-results/test/*.xml'
//             }
//         }
//         stage ('Push image') {
//             agent any
//             steps {
//                 script {
//                     checkout scm
//                     docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
//                         def customImage = docker.build("tlqkddk123/spring")
//                         customImage.push("${env.BUILD_ID}")
//                         customImage.push("latest")
//                     }
//                 }
//             }
//         }
//     }
// }

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
        stage ('Deploy & Run Docker') {
            steps {
                script {
                    def newDockerRun = "docker run -p 8080:8080 -d --name myspring tlqkddk123/spring:${env.BUILD_ID}"
                    def lastDockerRun = "docker rm -f myspring"
                    sshagent(['test-web']) {
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@3.38.99.210 ${lastDockerRun}"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@3.38.99.210 ${newDockerRun}"
                    }
                }
            }
        }
    }
}