# ssh-copy-id
ssh-copy-id username@remote_host

# Manually
```bash
mkdir ~/.ssh
chmod 755 .ssh

echo public_key_string >> ~/.ssh/authorized_keys
chmod 600 authorized_keys
```
