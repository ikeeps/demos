#! python
wwwlog = open('access-log')
byte_column = (line.rsplit(None, 1)[1] for line in wwwlog)
byte_mapper = (int(str) for str in byte_column if str != '-')
total = sum(byte_mapper)

print("Total:", total)