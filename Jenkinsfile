pipeline {
    agent any

	environment {
		PROJECT_NAME = 'secret-filter'
    	DOCKER_REGISTRY = credentials('HOST_DOCKER_REGISTRY')
  	}

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                
                sh "echo ${getVersion()} > src/main/resources/version"
                
                sh 'chmod +x ./gradlew'
                sh './gradlew build'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
        stage('Deploy') {
        	when {
        		tag "*"
        	}
        	
            steps {
                echo 'Deploying...'
                
                sh "docker build --tag \044DOCKER_REGISTRY/$PROJECT_NAME:${getVersion()} ."
                sh "docker tag \044DOCKER_REGISTRY/$PROJECT_NAME:${getVersion()} \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
                
                sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:${getVersion()}"
                sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:${getVersion()}"
                
                sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
                sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
            }
        }
    }
}

def getVersion() {
	return "${env.TAG_NAME ? env.TAG_NAME : 'SNAPSHOT.' + env.BUILD_NUMBER}";
}
