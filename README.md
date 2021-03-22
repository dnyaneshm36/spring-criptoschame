mvn deploy:deploy-file -DgroupId=jpbc  -DardifactId=api -Dversion=2.0.0  -Durl=file:./libs -DrepositoryId=libs 
-DupdateReleaseInfo=true 
-Dfile=/home/dm/javaproject/crudjdbc/jpbc-api-2.0.0.jar


mvn install:install-file \
   -Dfile=/home/dm/javaproject/crudjdbc/jpbc-api-2.0.0.jar \
   -DgroupId=jpbc \
   -DartifactId=api \
   -Dversion=2.0.0 \
   -Dpackaging=jar \
   -DgeneratePom=true