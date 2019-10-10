# https://www.owasp.org/index.php/Regular_expression_Denial_of_Service_-_ReDoS

import re
import time
# Java class name
start_time = time.time()
m = re.search("^(([a-z])+.)+[A-Z]([a-z])+$", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!")
# 33 seconds
print("--- %s seconds ---" % (time.time() - start_time))

# Email validation
start_time = time.time()
m = re.search("^([a-zA-Z0-9])(([\-.]|[_]+)?([a-zA-Z0-9]+))*(@){1}[a-z0-9]+[.]{1}(([a-z]{2,3})|([a-z]{2,3}[.]{1}[a-z]{2,3}))$", "aaaaaaaaaaaaaaaaaaaaaaaaaaaa!")
# 61 seconds
print("--- %s seconds ---" % (time.time() - start_time))
