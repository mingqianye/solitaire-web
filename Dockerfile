FROM clojure
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY project.clj /usr/src/app/
RUN lein deps
COPY . /usr/src/app
RUN lein clean && lein uberjar
EXPOSE 3000
CMD ["java", "-jar", "/usr/src/app/target/solitaire-web.jar"]
