#!/bin/bash
clear
WHOAMI=$1
echo 'MESSENGER'
echo "$WHOAMI"
# TODO: use the variables as the IP and port given by java program! 
python2.7 chatclient.py 127.0.0.1 9876 

