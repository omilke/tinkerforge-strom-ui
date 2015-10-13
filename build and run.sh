#!/bin/bash

#when run from eclipse, the shell doesn't seem to evaluate this file by default
source ~/.bashrc

mvn -e clean install

#run it
sh run.sh
