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
################################################################################
#                                                                              #
#  MIT License                                                                 #
#                                                                              #
################################################################################
################################################################################
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
    echo "Syntax: devel.sh [-h|-b|-r] -c [set-db-jdbc-h2|set-db-memory|set-servlet|set-batch]"
    echo "options:"
    echo "-h     Print this Help."
    echo "-b     Build all projects."
    echo "-r     Run project with spring boot."
    echo "-c     Config multi module."
    echo
}

################################################################################
# Check for root.                                                              #
################################################################################
CheckRoot() {
    # If we are not running as root we exit the program
    if [ "$(id -u)" != 0 ]; then
        echo "ERROR: You must be root user to run this program"
        exit
    fi
}
################################################################################
# Check for root.                                                              #
################################################################################
Config() {
    echo "Opt args: $1"
    if [[ $(git status --porcelain) ]]; then
        echo "Git has changes, please commit before run!"
    fi
    case $1 in
    set-db-jdbc-h2)
        echo "set-db-jdbc-h2"
        # -- settings.gradle --
        # Comment
        sed "s|\(^[[:space:]]*\)\(include 'adapters:memory'\)|\1//\2|" -i "${SCRIPT_DIR}/settings.gradle"
        # Uncomment
        sed "s|\(^[[:space:]]*\)/\{2,\}\(include 'adapters:jdbc'\)|\1\2|" -i "${SCRIPT_DIR}/settings.gradle"
        # -- framework servlet --
        # Comment
        sed "s|\(^[[:space:]]*\)\(implementation project(':adapters:memory')\)|\1//\2|" -i "${SCRIPT_DIR}/frameworks/servlet/build.gradle"
        # Uncomment
        sed "s|\(^[[:space:]]*\)/\{2,\}\(implementation project(':adapters:jdbc')\)|\1\2|" -i "${SCRIPT_DIR}/frameworks/servlet/build.gradle"
        # -- framework batch --
        # Comment
        sed "s|\(^[[:space:]]*\)\(implementation project(':adapters:memory')\)|\1//\2|" -i "${SCRIPT_DIR}/frameworks/batch/build.gradle"
        # Uncomment
        sed "s|\(^[[:space:]]*\)/\{2,\}\(implementation project(':adapters:jdbc')\)|\1\2|" -i "${SCRIPT_DIR}/frameworks/batch/build.gradle"
        exit
        ;;
    set-db-memory)
        echo "set-db-memory"
        # -- settings.gradle --
        # Comment
        sed "s|\(^[[:space:]]*\)\(include 'adapters:jdbc'\)|\1//\2|" -i "${SCRIPT_DIR}/settings.gradle"
        # Uncomment
        sed "s|\(^[[:space:]]*\)/\{2,\}\(include 'adapters:memory'\)|\1\2|" -i "${SCRIPT_DIR}/settings.gradle"
        # -- framework servlet --
        # Comment
        sed "s|\(^[[:space:]]*\)\(implementation project(':adapters:jdbc')\)|\1//\2|" -i "${SCRIPT_DIR}/frameworks/servlet/build.gradle"
        # Uncomment
        sed "s|\(^[[:space:]]*\)/\{2,\}\(implementation project(':adapters:memory')\)|\1\2|" -i "${SCRIPT_DIR}/frameworks/servlet/build.gradle"
        # -- framework batch --
        # Comment
        sed "s|\(^[[:space:]]*\)\(implementation project(':adapters:jdbc')\)|\1//\2|" -i "${SCRIPT_DIR}/frameworks/batch/build.gradle"
        # Uncomment
        sed "s|\(^[[:space:]]*\)/\{2,\}\(implementation project(':adapters:memory')\)|\1\2|" -i "${SCRIPT_DIR}/frameworks/batch/build.gradle"
        exit
        ;;
    esac
    echo "Developer helper."
    echo
    echo "Syntax: devel.sh -c [set-db-jdbc-h2|set-db-memory|set-servlet|set-batch]"
    echo "options:"
    echo "-c set-db-jdbc-h2    Enable database jdbc h2 memory"
    echo "-c set-db-memory     Enable database in memory."
    echo "-r set-servlet       Set framework servlet."
    echo "-c set-batch         Set framework batch."
    echo
}

################################################################################
################################################################################
# Main program                                                                 #
################################################################################
################################################################################

################################################################################
# Sanity checks                                                                #
################################################################################
# Are we rnning as root?
# CheckRoot

# Initialize variables
option=""
Msg="Hello world!"
################################################################################
# Process the input options. Add options as needed.                            #
################################################################################
# Get the options
while getopts ":hbrc:" option; do
    #echo "Option: $option"
    case $option in
    h) # display Help
        Help
        exit
        ;;
    b)
        echo "Gradle build"
        ./gradlew build
        exit
        ;;
    r)
        echo "Gradle runBoot"
        ./gradlew bootRun
        exit
        ;;
    c)
        echo "Config CLEAN modules..."
        Config "${OPTARG}"
        exit
        ;;
    \?) # incorrect option
        echo "Error: Invalid option"
        exit
        ;;
    esac
done

Help
#echo "$Msg"
