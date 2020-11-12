#!/bin/sh
agent_run_state=$(ssh-add -l | grep -q 'id_rsa'; echo $?)
if [ $agent_run_state = 1 ]; then
    ssh-add ~/.ssh/id_rsa
fi
