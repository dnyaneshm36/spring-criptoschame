mvn deploy:deploy-file  -Dfile=/home/dm/javaproject/crudjdbc/src/libs/jpbc-api-2.0.0.jar \
-DgroupId=it.unisa.dia.gas -DartifactId=jpbc-api -Dversion=2.0.0  -Durl=file:./libs -DrepositoryId=libs \
-DupdateReleaseInfo=true  


mvn install:install-file \
   -Dfile=/home/dm/javaproject/crudjdbc/jpbc-api-2.0.0.jar \
   -DgroupId=jpbc \
   -DartifactId=api \
   -Dversion=2.0.0 \
   -Dpackaging=jar \
   -DgeneratePom=true

   git ls-files | xargs wc -l