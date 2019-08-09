import gzip, bz2

def gen_open(filenames):
  for name in filenames:
    if name.endswith(".gz"):
      yield gzip.open(name)
    elif name.endswith(".bz2"):
      yield bz2.BZ2File(name)
    else:
      yield open(name)

if __name__ == "__main__":
  from genfind import gen_find
  filenames = gen_find("access-log*", "/media/sf_share/generators-uk/www")
  for f in gen_open(filenames):
    print(f.read())