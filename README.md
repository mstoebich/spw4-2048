# 2048 Web Application

## GitHub

### Setup local CI/CD environment for deployment job in GitHub Actions:

1. Create and run a container for hosting the servlet using Tomcat:
   ```shell
   docker run -d --name github-runner --network runner-net -p 8081:8080 tomcat:9-jdk17-openjdk-slim
   ```

2. Open a shell in the container:
   ```shell
   docker exec -it github-runner /bin/bash
   ```

3. Update and install packages:
   ```shell
   apt update
   apt upgrade
   apt install maven curl
   ```

4. Change permissions on ```/usr/local/tomcat/webapps``` to world read-/writeable:
   ```shell
   chmod 777 /usr/local/tomcat/webapps
   ```

5. Create a new user for running the GitHub self-hosted runner and switch to that user:
   ```shell
   adduser github-runner
   su -l github-runner
   ```

6. Install and run self-hosted runner for Linux X64 as explained in the settings of your repository (Settings &rarr; Actions &rarr; Runners)

7. To stop the runner, press ```Ctrl+C```.

8. If the runner is not needed anymore, ```exit``` the container shell and then stop and remove the container:
   ```shell
   docker stop github-runner
   docker rm github-runner
   ```

----------------------------------------------------
