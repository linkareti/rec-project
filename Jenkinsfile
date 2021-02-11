properties(
    [
        [
            $class: 'JiraProjectProperty'
        ],
        authorizationMatrix(
            [
                'hudson.model.Item.Build:Release',
                'hudson.model.Item.Build:SysAdmin',
                'hudson.model.Item.Build:rneto',
                'hudson.model.Item.Cancel:Release',
                'hudson.model.Item.Cancel:SysAdmin',
                'hudson.model.Item.Cancel:rneto',
                'hudson.model.Item.Configure:Release',
                'hudson.model.Item.Configure:SysAdmin',
                'hudson.model.Item.Configure:rneto',
                'hudson.model.Item.Delete:SysAdmin',
                'hudson.model.Item.Read:Release',
                'hudson.model.Item.Read:SysAdmin',
                'hudson.model.Item.Read:rneto',
                'hudson.model.Item.Workspace:Release',
                'hudson.model.Item.Workspace:SysAdmin',
                'hudson.model.Item.Workspace:rneto',
                'hudson.model.Run.Delete:SysAdmin',
                'hudson.model.Run.Delete:rneto',
                'hudson.model.Run.Update:Release',
                'hudson.model.Run.Update:SysAdmin',
                'hudson.model.Run.Update:rneto',
                'hudson.plugins.promoted_builds.Promotion.Promote:Release',
                'hudson.plugins.promoted_builds.Promotion.Promote:SysAdmin',
                'hudson.plugins.promoted_builds.Promotion.Promote:rneto'
            ]
        ), 
        [
            $class: 'BuildConfigProjectProperty',
            name: '',
            namespace: '',
            resourceVersion: '',
            uid: ''
        ],
        buildDiscarder(
            logRotator(
                artifactDaysToKeepStr: '',
                artifactNumToKeepStr: '5',
                daysToKeepStr: '',
                numToKeepStr: '5'
            )
        ),
        [
            $class: 'RebuildSettings',
            autoRebuild: false,
            rebuildDisabled: false
        ],
        parameters(
            [
                [
                    $class: 'ListSubversionTagsParameterDefinition',
                    credentialsId: 'handyman_linkare_svn',
                    defaultValue: 'rel-1.0.x',
                    maxTags: '',
                    name: 'release_branch',
                    reverseByDate: false,
                    reverseByName: true,
                    tagsDir: 'https://svn.linkare.com/internal/rec-project/rec/branches',
                    tagsFilter: ''
                ], 
                validatingString(
                    defaultValue: 'rel-1.0.0',
                    description: 'This is the version that will be used to tag the project release.',
                    failedValidationMessage: 'Version of the release, using the model: rel-<major>.<minor>.<build>',
                    name: 'release_version',
                    regex: 'rel\\-[0-9]+\\.[0-9]+\\.[0-9]+'),
                    validatingString(defaultValue: '',
                    description: 'The username to release the software - create tag, comment on bugzilla and soyon',
                    failedValidationMessage: 'Username should start by a letter, followed by any number of characters',
                    name: 'svn_username',
                    regex: '[a-zA-Z].*'
                ),
                password(
                    defaultValue: '',
                    description: 'The password to release the software - create tag, comment on bugzilla and soyon',
                    name: 'svn_password'
                ), 
                validatingString(defaultValue: '',
                description: 'The id of the bugzilla bug id (release task) to attach the creation of the tag on the build.',
                failedValidationMessage: 'Bug id is required and is an integral positive number',
                name: 'release_task_id',
                regex: '[0-9]+'
                )
            ]
        ), 
        [
            $class: 'ThrottleJobProperty',
            categories: [],
            limitOneJobWithMatchingParams: false,
            maxConcurrentPerNode: 0,
            maxConcurrentTotal: 0,
            paramsToUseForLimit: '',
            throttleEnabled: false,
            throttleOption: 'project'
        ]
    ]
)

// environment(
//     // def linkarePublicKeystorePath = "${env.LNK_PUB_KEYSTORE_PATH}"
//     // def linkarePublicKeystoreAlias = "${env.LNK_PUB_KEYSTORE_ALIAS}"
//     // def linkarePublicKeystorePass = "${env.LNK_PUB_KEYSTORE_PASS}"
//     // def linkarePublicKeystoreType = "${env.LNK_PUB_KEYSTORE_TYPE}"
    
//     // String linkarePublicKeystorePath = "/var/jenkins_home/certs/linkare_public_codesign.jks"
//     //def linkarePublicKeystoreAlias = "linkare_public_codesign"
//     //String teste = "linkare_public_codesign"
//     // String linkarePublicKeystorePass = "AskPass"
//     // String linkarePublicKeystoreType = "JKS"
// )

node(''){
    try {
        stage('Checkout') {
            node(){
                // echo "Pub Keystore Path ${env.LNK_PUB_KEYSTORE_PATH}"
                // echo "Pub Keystore Alias ${env.LNK_PUB_KEYSTORE_ALIAS}"
                // echo "Pub Keystore Pass ${env.LNK_PUB_KEYSTORE_PASS}"
                // echo "Pub Keystore Type ${env.LNK_PUB_KEYSTORE_TYPE}"
                checkout(
                    [
                        $class: 'SubversionSCM',
                        additionalCredentials: [],
                        excludedCommitMessages: '',
                        excludedRegions: '',
                        excludedRevprop: '',
                        excludedUsers: '',
                        filterChangelog: false,
                        ignoreDirPropChanges: false,
                        includedRegions: '',
                        locations: 
                            [
                                [
                                    cancelProcessOnExternalsFail: true,
                                    credentialsId: 'handyman_linkare_svn',
                                    depthOption: 'infinity',
                                    ignoreExternalsOption: true,
                                    local: '.',
                                    remote: 'https://svn.linkare.com/internal/rec-project/rec/branches/${release_branch}'
                                ]
                            ],
                        quietOperation: true,
                        workspaceUpdater: [$class: 'CheckoutUpdater']
                    ]
                )
            }
        }
        stage('Package') {
            node(){
                sh """
                    export LNK_PUB_KEYSTORE_PATH="/var/jenkins_home/certs/linkare_public_codesign.jks"
                    export LNK_PUB_KEYSTORE_ALIAS="linkare_public_codesign"
                    export LNK_PUB_KEYSTORE_PASS="AskPass"
                    export LNK_PUB_KEYSTORE_TYPE="JKS"
                    export JAVA_HOME="/var/jenkins_home/tools/jdk-11.0.2"
                    export PATH="\${PATH}:/var/jenkins_home/tools/hudson.tasks.Ant_AntInstallation/Apache_Ant_1.10.1/bin"
                    export PATH="\${PATH}:/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/maven_3.5.2/bin"
                    mvn clean package -Dmaven.test.skip=true -Djarsigner.alias="REC" -Dbuild.environment=production
                """
            }
        }
        stage('Deploy') {
            node(){
                sh """
                    DOCKER_IMAGES=\$(cat deployment/compose/target/docker-compose-runner-dev.yml |grep -i image |awk '{print \$2}' | grep elab)
                    for image in \${DOCKER_IMAGES}; do
                        printf "Pushing %s\\n" "\${image}"
                        docker push \${image}
                    done
                """
            }
        }
        stage('Archive') {
            node(){
                archiveArtifacts artifacts: 'rec.build.all/target/*.zip', onlyIfSuccessful: true
            }
        }
    } catch (e) {
        currentBuild.result = "FAILURE"
        throw e
    }
}


