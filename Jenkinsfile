pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Pulling backend code from Git...'
                checkout scm
            }
        }

        stage('Build Java App') {
            steps {
                echo 'Compiling and packaging the Spring Boot application...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building backend Docker image...'
                sh 'docker build -t asset-management-backend:latest .'
            }
        }

        stage('Deploy Backend Container') {
            steps {
                echo 'Deploying backend container on port 8080...'
                sh 'docker stop asset-backend || true'
                sh 'docker rm asset-backend || true'
                sh 'docker run -d --name asset-backend -p 8080:8080 asset-management-backend:latest'
            }
        }
    }
}