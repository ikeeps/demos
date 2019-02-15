# aws command needed

import json
import re
import datetime
import mysql.connector
import subprocess
from math import trunc

epoch = datetime.datetime.fromtimestamp(0)

def millis(dt):
    return trunc((dt - epoch).total_seconds() * 1000)

def totalRecords(wrap):
    total = 0
    for event in wrap["events"] :
        records = re.search(r'RequestId:\s[a-fA-F0-9\-]*\s([0-9])+\srecords stored', event["message"])
        if records:
            recordsNum = int(records.group(1))
            total += recordsNum
    return total

def statics(file):
    with open(file, 'r') as log:
        data = log.read()

    wrap = json.loads(data)

    print totalRecords(wrap)

if __name__ == "__main__":
    logFile = '/home/jfenghu/log-9.json'
    statics(logFile)