#!/usr/bin/bash
################################################################################
#                              devel.sh                                        #
#                                                                              #
# Manage this application                                                      #
# ...                                                                          #
#                                                                              #
#                                                                              #
################################################################################
################################################################################
# Config                                                                       #
################################################################################

SCRIPT_PATH="${BASH_SOURCE[0]}"
while [ -L "${SCRIPT_PATH}" ]; do
    SCRIPT_DIR="$(cd -P "$(dirname "${SCRIPT_PATH}")" >/dev/null 2>&1 && pwd)"
    SCRIPT_PATH="$(readlink "${SCRIPT_PATH}")"
    [[ ${SCRIPT_PATH} != /* ]] && SCRIPT_PATH="${SCRIPT_DIR}/${SCRIPT_PATH}"
done
SCRIPT_PATH="$(readlink -f "${SCRIPT_PATH}")"
SCRIPT_DIR="$(cd -P "$(dirname -- "${SCRIPT_PATH}")" >/dev/null 2>&1 && pwd)"
################################################################################
# Help                                                                         #
################################################################################
Help() {
    # Display Help
    echo "Developer helper."
    echo
    echo "Syntax: devel.sh command"
    echo "command:"
    echo " help                          Print this help"
    echo " config set-db-jdbc-postgresql Enable database jdbc h2 memory"
    echo " config set-db-jdbc-h2         Enable database jdbc postgresl"
    echo " config set-db-memory          Enable database in memory."
    echo " config set-servlet            Set framework servlet."
    echo " config set-batch              Set framework batch."
    echo " b | buid                      Spring boot build"
    echo " r | run                       Spring boot run"
    echo " t | test                      Spring boot test"
    echo " du | docker-compose-up        docker compose up"
    echo " dd | docker-compose-down      docker compose down"
    echo " dr | docker-compose-rm        docker compose remove all containers"
}

################################################################################
# Gradle config                                                                #
################################################################################
config-db-memory-enable() {
    echo "db-memory-enable"
    # -- settings.gradle --
    # Uncomment
    sed "s|\(^[[:space:]]*\)/\{2,\}\(include 'adapters:memory'\)|\1\2|" -i "${SCRIPT_DIR}/settings.gradle"
    sed "s|\(^[[:space:]]*\)/\{2,\}\(implementation project(':adapters:memory')\)|\1\2|" -i "${SCRIPT_DIR}/frameworks/servlet/build.gradle"
    sed "s|\(^[[:space:]]*\)/\{2,\}\(implementation project(':adapters:memory')\)|\1\2|" -i "${SCRIPT_DIR}/frameworks/batch/build.gradle"
}
config-db-memory-disable() {
    echo "db-memory-disable"
    # Comment
    sed "s|\(^[[:space:]]*\)\(include 'adapters:memory'\)|\1//\2|" -i "${SCRIPT_DIR}/settings.gradle"
    sed "s|\(^[[:space:]]*\)\(implementation project(':adapters:memory')\)|\1//\2|" -i "${SCRIPT_DIR}/frameworks/servlet/build.gradle"
    sed "s|\(^[[:space:]]*\)\(implementation project(':adapters:memory')\)|\1//\2|" -i "${SCRIPT_DIR}/frameworks/batch/build.gradle"
}
config-db-jdbc-enable() {
    echo "db-jdbc-enable"
    # Uncomment
    sed "s|\(^[[:space:]]*\)/\{2,\}\(include 'adapters:jdbc'\)|\1\2|" -i "${SCRIPT_DIR}/settings.gradle"
    sed "s|\(^[[:space:]]*\)/\{2,\}\(implementation project(':adapters:jdbc')\)|\1\2|" -i "${SCRIPT_DIR}/frameworks/servlet/build.gradle"
    sed "s|\(^[[:space:]]*\)/\{2,\}\(implementation project(':adapters:jdbc')\)|\1\2|" -i "${SCRIPT_DIR}/frameworks/batch/build.gradle"
}
config-db-jdbc-disable() {
    echo "db-jdbc-disable"
    # Comment
    sed "s|\(^[[:space:]]*\)\(include 'adapters:jdbc'\)|\1//\2|" -i "${SCRIPT_DIR}/settings.gradle"
    sed "s|\(^[[:space:]]*\)\(implementation project(':adapters:jdbc')\)|\1//\2|" -i "${SCRIPT_DIR}/frameworks/servlet/build.gradle"
    sed "s|\(^[[:space:]]*\)\(implementation project(':adapters:jdbc')\)|\1//\2|" -i "${SCRIPT_DIR}/frameworks/batch/build.gradle"
}
config-db-jdbc-h2-enable() {
    echo "db-jdbc-h2-enable"
    config-db-jdbc-enable
    # Uncomment
    sed "s|\(^[[:space:]]*\)/\{2,\}\(runtimeOnly 'com.h2database:h2'\)|\1\2|" -i "${SCRIPT_DIR}/adapters/jdbc/build.gradle"
}
config-db-jdbc-h2-disable() {
    echo "db-jdbc-h2-disable"
    # Comment
    sed "s|\(^[[:space:]]*\)\(runtimeOnly 'com.h2database:h2'\)|\1//\2|" -i "${SCRIPT_DIR}/adapters/jdbc/build.gradle"
}
config-db-jdbc-postgresql-enable() {
    echo "db-jdbc-postgresql-enable"
    config-db-jdbc-enable
    # Uncomment
    sed "s|\(^[[:space:]]*\)/\{2,\}\(runtimeOnly 'org.postgresql:postgresql'\)|\1\2|" -i "${SCRIPT_DIR}/adapters/jdbc/build.gradle"
    # prop file adapters/jdbc/src/main/resources/jdbc.properties
    sed "s|\(^[[:space:]]*\)#\(spring.datasource.\)|\1\2|" -i "${SCRIPT_DIR}/adapters/jdbc/src/main/resources/jdbc.properties"
}
config-db-jdbc-postgresql-disable() {
    echo "db-jdbc-postgresql-disable"
    # Comment
    sed "s|\(^[[:space:]]*\)\(runtimeOnly 'org.postgresql:postgresql'\)|\1//\2|" -i "${SCRIPT_DIR}/adapters/jdbc/build.gradle"
    # prop file adapters/jdbc/src/main/resources/jdbc.properties
    sed "s|\(^[[:space:]]*\)\(spring.datasource.\)|\1#\2|" -i "${SCRIPT_DIR}/adapters/jdbc/src/main/resources/jdbc.properties"
}
config-db-disable-all() {
    config-db-memory-disable
    config-db-jdbc-disable
    config-db-jdbc-postgresql-disable
    config-db-jdbc-h2-disable
}
Config() {
    echo "Opt args: $1"
    if [[ $(git status --porcelain) ]]; then
        echo "Git has changes, please commit before run!"
    fi
    case $1 in
    set-db-jdbc-postgresql)
        echo "set-db-jdbc-h2"
        config-db-disable-all
        config-db-jdbc-postgresql-enable
        #
        exit
        ;;
    set-db-jdbc-h2)
        echo "set-db-jdbc-h2"
        config-db-disable-all
        config-db-jdbc-h2-enable
        #
        exit
        ;;
    set-db-memory)
        echo "set-db-memory"
        config-db-disable-all
        config-db-memory-enable
        exit
        ;;
    set-framework-spring-batch)
        echo "set-framework-batch"
        # Comment
        sed "s|\(^[[:space:]]*\)\(include 'frameworks:servlet'\)|\1//\2|" -i "${SCRIPT_DIR}/settings.gradle"
        # Uncomment
        sed "s|\(^[[:space:]]*\)/\{2,\}\(include 'frameworks:batch'\)|\1\2|" -i "${SCRIPT_DIR}/settings.gradle"
        exit
        ;;
    set-framework-spring-servlet)
        echo "set-framework-spring-servlet"
        # Comment
        sed "s|\(^[[:space:]]*\)\(include 'frameworks:batch'\)|\1//\2|" -i "${SCRIPT_DIR}/settings.gradle"
        # Uncomment
        sed "s|\(^[[:space:]]*\)/\{2,\}\(include 'frameworks:servlet'\)|\1\2|" -i "${SCRIPT_DIR}/settings.gradle"
        exit
        ;;
    esac
    Help
}
__config_test() {
    echo " -------- TEST all possible configuration --------"
    echo " -------- TEST - servlet + db memory --------"
    "./${0}" config set-framework-spring-servlet
    "./${0}" config set-db-memory
    #"./${0}" clean;
    if ! "./${0}" build; then echo " ---> ERROR on build <----" && exit 1; fi
    echo " -------- TEST - servlet + db jdbc h2 --------"
    "./${0}" config set-db-jdbc-h2
    if ! "./${0}" build; then echo " ---> ERROR on build <----" && exit 1; fi
    echo " -------- TEST - servlet + db jdbc postgresql --------"
    "./${0}" config set-db-jdbc-postgresql
    if ! "./${0}" build; then echo " ---> ERROR on build <----" && exit 1; fi
}

################################################################################
################################################################################
# Main program                                                                 #
################################################################################
################################################################################

################################################################################
# Process the input options. Add options as needed.                            #
################################################################################
# Get the options
# Get the options
while [[ "$#" -gt 0 ]]; do
    declare KEY="$1"
    declare OPTARG="$2"
    case "${KEY}" in
    help|h|-h) # display Help
        Help
        exit
        ;;
    b | build)
        echo "Gradle build"
        ./gradlew build
        exit
        ;;
    r | run)
        echo "Gradle runBoot"
        ./gradlew bootRun
        exit
        ;;
    t | test)
        echo "Gradle test"
        ./gradlew test
        exit
        ;;
    config)
        echo "Config CLEAN modules..."
        Config "${OPTARG}"
        exit
        ;;
    du | docker-compose-up)
        echo "Start docker-compose with PostgreSQL"
        if ! command -v docker &>/dev/null; then
            echo "docker is required, but could not be found... Install it."
            exit 1
        fi
        docker compose -f "${SCRIPT_DIR}/scripts/docker-compose.yml" up
        exit
        ;;
    dd | docker-compose-down)
        echo "Stop docker-compose with PostgreSQL"
        if ! command -v docker &>/dev/null; then
            echo "docker is required, but could not be found... Install it."
            exit 1
        fi
        docker compose -f "${SCRIPT_DIR}/scripts/docker-compose.yml" down
        exit
        ;;
    dr | docker-compose-rm)
        echo "Remoe docker-compose containers"
        if ! command -v docker &>/dev/null; then
            echo "docker is required, but could not be found... Install it."
            exit 1
        fi
        docker compose -f "${SCRIPT_DIR}/scripts/docker-compose.yml" rm
        exit
        ;;
    _config_test)
        __config_test
        exit
        ;;
    *) # incorrect option
        echo "Error: Invalid option"
        Help
        exit
        ;;
    esac
done

Help
