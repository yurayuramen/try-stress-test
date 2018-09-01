
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

* gatling
```
$GATLING_HOME/bin/gatling.sh \
-sf ${STRESS_TEST_DIR}/gatling/src/test \
-s mygatling.MyFirstGatling \
-rf ${STRESS_TEST_DIR}/gatling/results
```

