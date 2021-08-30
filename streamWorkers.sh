#!/bin/bash

################################################################################
# Help                                                                         #
################################################################################
Help()
{
   # Display Help
   echo "Stream Workers, by Guido Marchini."
   echo
   echo "This application runs 10 workers in order to search for the word \"Lpfn\""
   echo "in randomly generated strings"
   echo
   echo "options:"
   echo "-t     system timeout, in millis."
   echo
}

################################################################################
# Main program                                                                 #
################################################################################

TIMEOUT=60000
while getopts ":h t:" option; do
   case $option in
      h) # display Help
         Help
         exit;;
      t)
        TIMEOUT=$OPTARG
   esac
done

java -jar target/streamers-1.0-SNAPSHOT-jar-with-dependencies.jar $TIMEOUT