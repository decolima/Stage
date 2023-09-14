#!/bin/bash

cd /home/pi/IoT/BleScan
source .venv/bin/activate

if python3 -m pip show bleak &>/dev/null; then
    echo "'bleak' is present"
else
    echo "'bleak' is not present ... installing"
    python3 -m pip install bleak
fi

sudo python3 /home/pi/IoT/BleScan/main.py