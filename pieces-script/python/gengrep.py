import re

def gen_grep(pat, lines):
  patc = re.compile(pat)
  for line in lines:
    if patc.search(line):
      yield line
      
if __name__ == "__main__":
    from genfind import gen_find
    from genopen import gen_open
    from gencat import gen_cat
    
    lognames = gen_find("*log", "/media/sf_share/generators-uk/")
    logfiles = gen_open(lognames)
    loglines = gen_cat(logfiles)
    matchlines = gen_grep(r" 4\d\d ", loglines)
    for line in matchlines:
      print(line)