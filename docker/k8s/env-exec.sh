#!/bin/bash
. $1
DATA="$(cat $2)"

i=0
for name in ${KEYS[@]}; do
  DATA="$(echo "$DATA" | sed -e "s/${name}/${VALUES[i]}/g")"
  let i++
done
echo "$DATA"



