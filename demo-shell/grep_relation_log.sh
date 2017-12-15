#!/bin/sh

inputDay=$1

dir=$2
keyword=$3

cd $dir

arr=($inputDay)

for i in ${arr[@]}
do
    p="server.2017-10-$i_*"
    for log in `ls $p`
    do
        if [ "${log##*.}" = "gz" ];then
            echo "zgrep $log"
            zgrep $keyword $log
        else
            echo "grep $log"
            grep $keyword $log
        fi
    done
done