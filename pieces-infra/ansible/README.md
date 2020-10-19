# run
docker run -dit --name cr-ebisumart.local centos:centos7
ansible-playbook test-docker/playbook.yml -i inv/dev

# result
```bash
# docker exec -it [container-id] bash
docker exec -it cr-ebisumart.local bash
cat /tmp/testfile.txt
```
