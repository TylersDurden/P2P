#!/bin/bash
echo 'Loading music library to Node ###'
clear
sleep 3
mkdir MusicLibrary
cd MusicLibrary
touch lib.txt
cd ..
echo 'Searching for compatible audio files... '
grep -r -i --include=\*.mp3 ''
grep -r -i --include=\*.wav ''
grep -r -i --iclude=\*.flac '' 
echo 'Please enter the names of which files you would like to make available on your node.'
echo 'When you're finished, enter DONE'
echo '####################################################################################'

