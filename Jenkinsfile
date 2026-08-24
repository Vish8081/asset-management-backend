pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Pulling backend code from Git...'
                checkout scm
            }
        }

        stage('Build Java App using Docker') {
            steps {
                echo "Running Maven build inside a Docker container..."
                // We mount the workspace to /app and run Maven inside a maven container
                sh 'docker run --rm -v /var/jenkins_home/workspace/Asset-Backend:/app -w /app maven:3.9-eclipse-temurin-17 sh -c "mvn clean package -DskipTests"'
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