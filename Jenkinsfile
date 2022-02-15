node {
    withEnv(["JAVA_HOME=${ tool 'OpenJDK-17.0.1' }"]) {
        stage('Setup settings') {
            JAVA_VERSION='17.0.1';
            print JAVA_VERSION;
            print payload;
            switch (Target_OS) {
                case 'Windows': 
                    env.BUILD_LWJGL_NATIVES = 'natives-windows'
                    JDK_NAME = "openjdk-${JAVA_VERSION}_windows-x64_bin"
                    FILES = ['Start.bat', 'TOW.exe']
                    PUBLISH_PATH = 'windows'
                    break
                case 'Linux':
                    env.BUILD_LWJGL_NATIVES = 'natives-linux'
                    JDK_NAME = "openjdk-${JAVA_VERSION}_linux-x64_bin"
                    FILES = ['TOW.sh', 'TOW.png']
                    PUBLISH_PATH = 'linux'
                    break
                case 'MacOS':
                    env.BUILD_LWJGL_NATIVES = 'natives-macos'
                    JDK_NAME = "openjdk-${JAVA_VERSION}_osx-x64_bin"
                    FILES = ['TOW-osx.sh', 'TOW.png']
                    PUBLISH_PATH = 'macos'
                    break
                case 'Linux ARM':
                    env.BUILD_LWJGL_NATIVES = 'natives-linux-arm64'
                    JDK_NAME = "openjdk-${JAVA_VERSION}_linux-aarch64_bin"
                    FILES = ['TOW.sh', 'TOW.png']
                    PUBLISH_PATH = 'linux-arm'
                    break
                case 'MacOS ARM':
                    env.BUILD_LWJGL_NATIVES = 'natives-macos-arm64'
                    JDK_NAME = "openjdk-${JAVA_VERSION}_macos-aarch64_bin"
                    FILES = ['TOW-osx.sh', 'TOW.png']
                    PUBLISH_PATH = 'macos-arm'
                    break
            }
            FILES.add("${JDK_NAME}.zip")
            if (Clean_Workspace == "true") {
                cleanWs()
            }
            if (OrchEngine_Branch == '') {
                OrchEngine_Branch = TOW_Branch
            }
        }
        
        stage('Clone sources TOW') {
            dir ('repo') {
                git url: 'https://github.com/KeyJ148/TOW.git', branch: "${TOW_Branch}"
            }
        }
            
        stage('Clone sources OrchEngine') {
            dir('repo/OrchEngine') {
                git url: 'https://github.com/KeyJ148/OrchEngine.git', branch: "${OrchEngine_Branch}"
            }
        }
            
        stage('Gradle build') {
            dir ('repo') {
                sh './gradlew distZip'
            }
        }
        
        stage('Check and update caches') {
            dir ('cache'){
                for (String file : FILES) {
                    if (fileExists(file)) {
                        echo "Use ${file} from cache"
                    } else {
                        sh "wget https://build.tow.abro.cc/${file}"
                    }
                }
                if (fileExists(JDK_NAME)) {
                    echo "Use ${JDK_NAME} from cache"
                } else {
                    if (Target_OS == 'Windows') {
                        sh "unzip ${JDK_NAME}.zip -d ${JDK_NAME}"
                    } else if (Target_OS == 'Linux' || Target_OS == 'MacOS' || Target_OS == 'Linux ARM' || Target_OS == 'MacOS ARM') {
                        sh "unzip -X ${JDK_NAME}.zip -d ${JDK_NAME}"
                    }
                }
            }
        }
        
        stage('Create game client') {
            dir ('repo') {
                VERSION = sh (script: './gradlew properties | grep "version"', returnStdout: true).split(' ')[1].trim()
            }
            dir('game'){
                deleteDir()
            }
            sh 'mkdir game'
            sh "unzip -X repo/build/distributions/TOW-${VERSION}.zip -d game"
            dir("game/TOW-${VERSION}") {
                sh 'mkdir jdk'
                if (Target_OS == 'Windows' || Target_OS == 'Linux' || Target_OS == 'Linux ARM') {  
                    sh "cp -R ../../cache/${JDK_NAME}/jdk-${JAVA_VERSION}/* jdk/"
                } else if (Target_OS == 'MacOS' || Target_OS == 'MacOS ARM') {
                    sh "cp -R ../../cache/${JDK_NAME}/jdk-${JAVA_VERSION}.jdk/* jdk/"
                }
                if (Target_OS == 'Windows') {
                    sh 'cp ../../cache/Start.bat bin/'
                    sh 'cp ../../cache/TOW.exe ./'
                    sh 'rm bin/TOW'
                } else if (Target_OS == 'Linux' || Target_OS == 'Linux ARM') {
                    sh 'cp ../../cache/TOW.sh ./ && chmod +x TOW.sh'
                    sh 'cp ../../cache/TOW.png ./'
                    sh 'rm bin/TOW.bat'
                } else if (Target_OS == 'MacOS' || Target_OS == 'MacOS ARM') {
                    sh 'cp ../../cache/TOW-osx.sh ./ && chmod +x TOW-osx.sh'
                    sh 'cp ../../cache/TOW.png ./'
                    sh 'rm bin/TOW.bat'
                }
            }
            dir('publish'){
                deleteDir()
            }
            sh 'mkdir publish'
            if (TOW_Branch == 'master' && OrchEngine_Branch == 'master') {
                BUILD_FILE_NAME = "TOW-${VERSION}"
            } else {
                if (TOW_Branch == OrchEngine_Branch) {
                    BUILD_FILE_NAME = "TOW-${TOW_Branch}-${VERSION}"
                } else {
                    BUILD_FILE_NAME = "TOW-${TOW_Branch}-${OrchEngine_Branch}-${VERSION}"
                }
            }
            switch (Type_of_archive) {
                case '.zip':
                    env.BUILD_FILE_NAME_FULL = "${BUILD_FILE_NAME}.zip"
                    zip zipFile: "publish/${BUILD_FILE_NAME_FULL}", dir: "game"
                    break;
                case '.tar':
                    env.BUILD_FILE_NAME_FULL = "${BUILD_FILE_NAME}.tar.gz"
                    sh "cd game && tar -czvf ../publish/${BUILD_FILE_NAME_FULL} TOW-${VERSION} && cd .."
                    break;
            }
        }
        
        stage('Create docs') {
            dir ('repo') {
                sh './gradlew javadoc'
                zip zipFile: '../publish/javadoc.zip', dir: 'build/docs/javadoc'
            }
        }
        
        stage('Publish game client') {
            dir('publish') {
                withCredentials(bindings: [sshUserPrivateKey( \
                        credentialsId: 'd3a1c598-5d55-4dc7-9d2e-675720b09486', \
                        keyFileVariable: 'SSH_KEY', \
                        usernameVariable: 'SSH_USER')]) {
                    sh "scp -P 20022 -i $SSH_KEY -o StrictHostKeyChecking=no ${BUILD_FILE_NAME_FULL} $SSH_USER@nginx-tow:/usr/share/nginx/html/download/${PUBLISH_PATH}/"
                }
            }
        }
        
        stage('Publish docs') {
            
        }
    }
}
