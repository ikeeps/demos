def gen_cat(sources):
  for source in sources:
    for l in source:
      yield l
      
if __name__ == "__main__":
  from genfind import gen_find
  from genopen import gen_open
  
  filenames = gen_find("access*", "/media/sf_share/generators-uk/www")
  logfiles = gen_open(filenames)
  lines = gen_cat(logfiles)
  for line in lines:
    print(line)