#!/bin/bash

arrayNames=('java' 'nginx1' 'nginx2' )
arrayCommands=(`jps | grep 'ProdServerStart' | sed -n '1p' | awk '{ print $1 }' | xargs`
  `ps -ef | grep nginx | sed -n '1p' | awk '{ print $2 }' | xargs`
  `ps -ef | grep nginx | sed -n '2p' | awk '{ print $2 }' | xargs`
)

echo ${arrayNames[@]}
echo ${arrayCommands[@]}

for i in {0..100000}; do
  array2=()
  cntTimeWait=`netstat -antp | egrep 'TIME_WAIT' | wc -l | xargs`
  cntEstablish=`netstat -antp | egrep ESTABLISH | wc -l | xargs`
  cntSynSent=`netstat -antp | egrep SYN_SENT | wc -l | xargs`


  for ((i = 0; i < ${#arrayNames[@]}; i++)){
    pname=${arrayNames[$i]}
    pid=${arrayCommands[$i]}
    #echo $pname:$pid
    #netstat -antp | egrep "\s${pid}/"
    #lsof -nP -iTCP | egrep "\s${pid}\s.+ESTABLISH" #| wc -l | xargs
    array2+=( [$pname:$pid]est:`netstat -antp | egrep ".+ESTABLISH.+\s${pid}/" | wc -l | xargs` )
    array2+=( [$pname:$pid]syn:`netstat -antp | egrep ".+SYN_SENT.+\s${pid}/" | wc -l | xargs` )

  }

  ts=`date '+%F %T'`
  echo "${ts}[TWAIT]$cntTimeWait[EST:Total]$cntEstablishi[SYN]$cntSynSent[EST]${array2[@]}"
  sleep 2
done
