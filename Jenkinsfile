pipeline {

    agent any

    parameters {
        choice(
            name: 'DEPLOY_ENV',
            choices: ['dev', 'test'],
            description: 'Select deployment environment'
        )
    }

    environment {
        DEPLOY_ENV = "${params.DEPLOY_ENV}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                dir('backend') {
                    bat 'mvnw.cmd clean compile'
                }
            }
        }

        stage('Package') {
            steps {
                dir('backend') {
                    bat 'mvnw.cmd package -DskipTests'
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying to ${env.DEPLOY_ENV} environment"

                bat '''
                    copy /Y "backend\\target\\content-calendar-0.0.1-SNAPSHOT.war" "C:\\Program Files\\Apache Software Foundation\\Tomcat 11.0\\webapps\\"
                '''

                echo "WAR successfully copied to Tomcat webapps"
                echo "Tomcat application URL: http://localhost:8081/content-calendar-0.0.1-SNAPSHOT/"
            }
        }
    }

    post {
        success {
            echo 'Social Media Content Calendar pipeline completed successfully.'
        }

        failure {
            echo 'Pipeline failed. Check the console output.'
        }
    }
}