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

        // Docker Hub configuration
        DOCKERHUB_USERNAME = "atharva756"
        IMAGE_NAME = "social-media-calendar"

        // Jenkins automatically increments BUILD_NUMBER
        IMAGE_TAG = "build-${BUILD_NUMBER}"

        // Final Docker image name
        FULL_IMAGE_NAME = "${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG}"
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

        stage('Selenium Tests') {
            steps {
                dir('backend') {
                    bat '''
                        mvnw.cmd test -Dtest=DashboardTest,CreatePostTest,SearchPostTest,UpdatePostTest
                    '''
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

        // EXISTING TOMCAT DEPLOYMENT - KEPT
        stage('Deploy to Tomcat') {
            steps {
                echo "Deploying to ${env.DEPLOY_ENV} environment"

                bat '''
                    copy /Y "backend\\target\\content-calendar-0.0.1-SNAPSHOT.war" "C:\\Program Files\\Apache Software Foundation\\Tomcat 11.0\\webapps\\"
                '''

                echo "WAR successfully copied to Tomcat webapps"
                echo "Tomcat application URL: http://localhost:8081/content-calendar-0.0.1-SNAPSHOT/"
            }
        }

        // NEW
        stage('Docker Build') {
        steps {
            withEnv(["PATH+DOCKER=${env.DOCKER_PATH}"]) {
                echo "Building Docker image: ${env.FULL_IMAGE_NAME}"

                dir('frontend') {
                    bat "docker build -t ${env.FULL_IMAGE_NAME} ."
                }

                echo "Docker image built successfully."
            }
        }
    }

    stage('Docker Login') {
        steps {
            withEnv(["PATH+DOCKER=${env.DOCKER_PATH}"]) {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials-system',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    bat '''
                        echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin
                    '''
                }

                echo "Docker Hub login successful."
            }
        }
    }

    stage('Docker Push') {
        steps {
            withEnv(["PATH+DOCKER=${env.DOCKER_PATH}"]) {
                echo "Pushing Docker image: ${env.FULL_IMAGE_NAME}"

                bat "docker push ${env.FULL_IMAGE_NAME}"

                echo "Docker image pushed successfully to Docker Hub."
            }
        }
}

stage('Docker Deploy') {
    steps {
        withEnv(["PATH+DOCKER=${env.DOCKER_PATH}"]) {

            echo "Deploying Docker container..."

            bat '''
                docker stop social-media-calendar-container 2>NUL || exit /B 0
            '''

            bat '''
                docker rm social-media-calendar-container 2>NUL || exit /B 0
            '''

            bat """
                docker run -d ^
                --name social-media-calendar-container ^
                -p 8083:80 ^
                ${env.FULL_IMAGE_NAME}
            """

            echo "Fresh Docker container deployed successfully."
            echo "Docker application URL: http://localhost:8083"
        }
    }
}
    }

    post {

        always {
            junit 'backend/target/surefire-reports/*.xml'
        }

        success {
            echo 'Social Media Content Calendar CI/CD pipeline completed successfully.'
            echo "Docker image: ${env.FULL_IMAGE_NAME}"
            echo "Docker container: social-media-calendar-container"
            echo "Application URL: http://localhost:8083"
        }

        failure {
            echo 'Pipeline failed. Check the console output.'
        }
    }
}