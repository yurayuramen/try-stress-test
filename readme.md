# projectの元ネタ

* play

```
sbt new playframework/play-scala-seed.g8
```

* akka-http

```
sbt new akka/akka-http-quickstart-scala.g8
```

* finagle

```
git clone https://github.com/twitter/finagle.git
cp -Rf finagle/doc/src/sphinx/code/quickstart finagle-quickstart
```

* gatling

```
sbt new sbt/scala-seed.g8
```

# セットアップ

* gatling

* nginx

* redis

* git clone 

```bash
git clone 
```

* （環境）変数の設定

git cloneしたフォルダパスを **STRESS_TEST_DIR**
gatlingをインストールしたフォルダパスを **GATLING_HOME**


# ビルド->起動

* nginx

```bash
cd ${STRESS_TEST_DIR}/nginx
nginx -c ${STRESS_TEST_DIR}/nginx/nginx.conf -p ${STRESS_TEST_DIR}/nginx

sudo nginx -c ${STRESS_TEST_DIR}/nginx/nginx.conf -p ${STRESS_TEST_DIR}/nginx -g "user ec2-user;"

# 確認
curl -XGET -i http://127.0.0.1:8080/

# kill
cd ${STRESS_TEST_DIR}/nginx
kill `cat logs/nginx.pid` 
```

* finagle
```
cd ${STRESS_TEST_DIR}
sbt "project finagle-quickstart" stage
./finagle-quickstart/target/universal/stage/bin/server
```


* akka-http
```
cd ${STRESS_TEST_DIR}
sbt "project akka-http-quickstart" stage
./akka-http-quickstart/target/universal/stage/bin/akka-htt-quickstart
```

* play
チューニング
https://www.playframework.com/documentation/2.6.x/SettingsNetty#configuring-transport-socket
```
cd ${STRESS_TEST_DIR}
sbt "project play-quickstart" stage
JAVA_OPTS=-Dplay.server.netty.transport=native ./play-quickstart/target/universal/stage/bin/my-tiny-play
```


* gatling

```bash
cd ${STRESS_TEST_DIR}
sbt "project gatling" clean packageBin && \
cp ${STRESS_TEST_DIR}/gatling/target/scala-2.12/gatling_2.12-0.1.0.jar \
${STRESS_TEST_DIR}/play-quickstart/lib && \
cp ${STRESS_TEST_DIR}/gatling/target/scala-2.12/gatling_2.12-0.1.0.jar \
${STRESS_TEST_DIR}/akka-http-quickstart/lib && \
cp ${STRESS_TEST_DIR}/gatling/target/scala-2.12/gatling_2.12-0.1.0.jar \
${STRESS_TEST_DIR}/finagle-quickstart/lib


```

* gatling
-sでクラスを指定して実行できる

```
JAVA_OPTS=-Dmygatling.baseurl=http://127.0.0.1:8080 \
$GATLING_HOME/bin/gatling.sh \
-sf ${STRESS_TEST_DIR}/gatling/src/test \
-rf ${STRESS_TEST_DIR}/gatling/results


```

