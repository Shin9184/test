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
        
        stage('ArgoCD Deploy') {
            steps {
                script {
                    sshagent (credentials: ['argoCD']) {
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@3.35.220.0 argocd repo add https://github.com/tlqkddk123/spring.git"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@3.35.220.0 argocd app create test --repo https://github.com/tlqkddk123/spring.git --sync-policy automated --path templates --dest-server https://kubernetes.default.svc --dest-namespace default"
                    }
                }
            }
        }
    }
}
