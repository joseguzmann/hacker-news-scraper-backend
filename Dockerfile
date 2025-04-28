FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/universal/stage /app

COPY public/ public/

EXPOSE 9000

# Secret key should change in a real scenario
CMD ["bin/hacker-news-scraper-backend", "-Dplay.http.secret.key=7d9f5a38882c4c7a4f0e72ea5f894d71a14f7f26371eac0c62e38f76c2b11d89", "-Dhttp.port=9000"]
