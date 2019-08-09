#! python
import os
import fnmatch

def gen_find(filepat, top):
  for path, dirlist, filelist in os.walk(top):
    for f in fnmatch.filter(filelist, filepat):
        yield os.path.join(path, f)

if __name__ == "__main__":
  lognames = gen_find("*log", "/media/sf_share/generators-uk/")
  for name in lognames:
    print(name)