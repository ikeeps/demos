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

def toSqlParam(message):
    messageStr = message["message"]
    match = re.search(r'RequestId:\s([a-fA-F0-9\-]*)\s', messageStr)
    timestamp = message['timestamp']

    return (datetime.datetime.utcfromtimestamp(timestamp / 1000.0), messageStr, match.group(1), message['logStreamName'])

def queryCloudWatchLog(file, group, pattern, start, end):
    with open(file, 'w') as log:
        cmd = "aws logs filter-log-events --log-group-name '{group}' --start-time {start:d} --end-time {end:d} --filter-pattern {pattern}".format(**locals())
        p = subprocess.Popen(cmd, stdout=log, shell=True)
        pStatus = p.wait()
        print(pStatus)
        log.flush()
    

def saveToDb(file, cnx):
    with open(file, 'r') as log:
        data = log.read()

    wrap = json.loads(data)

    events = [ toSqlParam(event) for event in wrap["events"] ]
    sql = "INSERT INTO events (`timestamp`, message, request_id, log_stream_name) VALUES(%s, %s, %s, %s)"
    cursor = cnx.cursor()

    count = 0
    for event in events:
        cursor.execute(sql, event)
        count = count + 1
        if count > 100:
            cnx.commit()
            count = 0
    if count > 0:
        cnx.commit()
    cursor.close()

if __name__ == "__main__":

    config = {
        'host': 'localhost',
        'port': 3306,
        'database': 'demo',
        'user': 'sammy',
        'password': '12345678',
        'charset': 'utf8',
        'use_unicode': True,
        'get_warnings': True,
    }

    cnx = mysql.connector.Connect(**config)
    print(cnx.get_server_version())
    logFile = '/home/jfenghu/log-9.json'

    dateFormat = '%Y-%m-%d %H:%M:%S'
    start = datetime.datetime.strptime("2018-12-27 16:15:00", dateFormat)
    endDate = datetime.datetime.strptime("2018-12-27 16:26:00", dateFormat)

#    start = datetime.datetime.strptime("2018-12-24 18:27:00", dateFormat)
#    endDate = datetime.datetime.strptime("2018-12-24 18:29:00", dateFormat)

    queryCloudWatchLog(logFile, 'group name', "RequestId", millis(start), millis(endDate))
    saveToDb(logFile, cnx)
    cnx.close()