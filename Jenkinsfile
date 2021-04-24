pipeline {
    agent none;

    environment {
        PROJECT_NAME = 'secret-filter'
        DOCKER_REGISTRY = credentials('HOST_DOCKER_REGISTRY')
        RUNDECK_TOKEN = credentials('RUNDECK_TOKEN')
        RUNDECK_JOB_ID = '69a6482f-c285-4e14-a915-03f5b4e665f4'
    }

    stages {
        stage('Build Arm64') {
            agent {
                label 'arm64'
            }
            steps {
                doBuild('arm64');
            }
        }
        stage('Deploy Arm64') {
            agent {
                label 'arm64'
            }
            when {
                tag "*"
            }
            steps {
                doDeploy('arm64');
            }
        }
        stage('Clean Arm64') {
            agent {
                label 'arm64'
            }
            steps {
                doClean('arm64');
            }
        }
        stage('Build Amd64') {
            agent {
                label 'amd64'
            }
            steps {
                doBuild('amd64');
            }
        }
        stage('Deploy Amd64') {
            agent {
                label 'amd64'
            }
            when {
                tag "*"
            }
            steps {
                doDeploy('amd64');
                doManifest();
            }
        }
        stage('Clean Amd64') {
            agent {
                label 'amd64'
            }
            steps {
                doClean('amd64');
            }
        }
        stage('Run') {
            agent {
                label 'amd64'
            }
            when {
                tag "*"
            }
            steps {
                doRun();
            }
        }
    }
}

def doBuild(arch) {
    echo 'Building...'

    sh "echo ${getVersion()} > src/main/resources/version"
    sh 'chmod +x ./gradlew'
    sh './gradlew build'
    
    sh "docker build --tag \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest ."
}

def doDeploy(arch) {
    echo 'Deploying...'
    
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest"
}

def doClean(arch) {
    echo 'Cleaning...'
    
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest"
}
def doManifest() {
    sh "docker manifest create --insecure --amend \044DOCKER_REGISTRY/$PROJECT_NAME:latest \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
    sh "docker manifest push --insecure --purge \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
}

def doRun() {
    echo 'Running...'
    sh "curl --request POST https://ci.ivcode.org/rundeck/api/36/job/${RUNDECK_JOB_ID}/executions?authtoken=\044RUNDECK_TOKEN"
}

def getVersion() {
    return "${env.TAG_NAME ? env.TAG_NAME.substring(1) : 'SNAPSHOT.' + env.BUILD_NUMBER}";
}