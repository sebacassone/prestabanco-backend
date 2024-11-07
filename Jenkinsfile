pipeline {
    agent any

    environment {
        GITHUB_REPO = 'https://github.com/sebacassone/prestabanco-backend'
        GITHUB_BRANCH = 'main'
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        DOCKER_IMAGE = 'sebacassone/prestabanco-backend'
        DB_HOST = 'postgres:5432'
        DB_NAME = 'presta_banco'
        DB_USERNAME = 'postgres'
        DB_PASSWORD = '123admin'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', 
                          branches: [[name: "*/${GITHUB_BRANCH}"]], 
                          userRemoteConfigs: [[url: "${GITHUB_REPO}"]]])
            }
        }

        stage('Setup Environment') {
            steps {
                script {
                    // Crear archivo .env con las variables de entorno necesarias
                    writeFile file: '.env', text: """
                    DB_HOST=${DB_HOST}
                    DB_NAME=${DB_NAME}
                    DB_USERNAME=${DB_USERNAME}
                    DB_PASSWORD=${DB_PASSWORD}
                    """
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    // Exportar las variables de entorno y ejecutar las pruebas unitarias con Gradle
                    sh '''
                        export $(cat .env | xargs)
                        ./gradlew clean build
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Construir la imagen Docker con las variables de entorno
                    sh '''
                        docker build \
                        --build-arg DB_HOST=$DB_HOST \
                        --build-arg DB_NAME=$DB_NAME \
                        --build-arg DB_USERNAME=$DB_USERNAME \
                        --build-arg DB_PASSWORD=$DB_PASSWORD \
                        -t $DOCKER_IMAGE .
                    '''
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "Logging into DockerHub..."
                    // Inicio de sesión en DockerHub usando password-stdin
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'

                    echo "Pushing Docker Image..."
                    sh 'docker push $DOCKER_IMAGE'
                }
            }
        }
    }

    post {
        always {
            junit 'build/test-results/**/*.xml' // Publicar resultados de pruebas
            cleanWs() // Limpiar el espacio de trabajo
        }
    }
}
