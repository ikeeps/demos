import tarfile
import json
import datetime

tar = tarfile.open("/home/jfenghu/uploaded.tar.gz", "r:gz")
count = 0
datas = dict()
def parseFile(f):
    for line in f:
        if line:
            data = json.loads(line)
            date = datetime.datetime.strptime(data['LastTimeStamp'], '%Y-%m-%d %H:%M:%S.%f')
            day = date.strftime('%Y/%m/%d')
            datas[day] = datas.get(day, 0) + 1
            count = count + 1

for member in tar.getmembers():
    f = tar.extractfile(member)
    if f:
        parseFile(f)
print '{} \t {}'.format('day', 'count')
for k, v in datas.items():
    print '{} \t {}'.format(k, v)
print count
