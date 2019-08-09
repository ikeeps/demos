#! python
wwwlog = open('access-log')
total = 0

for line in wwwlog:
  byte_str = line.rsplit(None, 1)[1]
  if byte_str != '-':
    total += int(byte_str)
    
print("Total:", total)