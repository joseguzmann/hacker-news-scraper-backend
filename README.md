# Hacker News Scraper - Backend

## Overview
This is the backend service for the Hacker News Scraper project. It provides an API that scrapes and retrieves Hacker News posts based on optional filters. The service is built with Scala, Play Framework, and exposes Swagger documentation for easy API exploration.

## Features
- Scrape Hacker News front page posts
- Filter and sort posts based on criteria
- Health check endpoint
- Swagger documentation available at `/docs/index.html`
- Dockerized for easy deployment

## Tech Stack
- Scala 2.13.16
- Play Framework 3.0.7
- SBT 1.9.0
- Swagger UI
- Docker

## Endpoints
| Method | Path | Description |
| :----: | :--: | :---------: |
| GET | `/health` | Returns the health status of the service |
| GET | `/hacker-news/entries?filter=filterMode` | Fetches Hacker News entries with optional filter |

## Filter Modes
You can use the `filter` query parameter to customize the list of entries:

- `longTitles`: Entries with more than five words in the title, ordered by number of comments (descending).
- `shortTitles`: Entries with five or fewer words in the title, ordered by points (descending).

If no filter is provided, all entries are returned without special ordering.

## Setup (Local Development)

1. **Clone the repository**

```bash
git clone https://github.com/jg175415/hacker-news-scraper-backend.git
cd hacker-news-scraper-backend
```

2. **Run the project**

```bash
sbt run
```

The API will be available at `http://localhost:9000`.

Swagger UI can be accessed at `http://localhost:9000/docs/index.html`.

3. **Run tests**

```bash
sbt test
```

## Docker Deployment

The following code shows an example of how the service was hosted on the production VPS:

1. **Build and push the Docker image**

```bash
docker build -t jg175415/hacker-news-scraper-backend:latest .
docker push jg175415/hacker-news-scraper-backend:latest
```

2. **Run the container**

```bash
docker pull jg175415/hacker-news-scraper-backend:latest
docker run -d -p 8084:9000 jg175415/hacker-news-scraper-backend:latest
```

## Environment Variables
Currently no external environment variables are required.

## Production Deployment

The production version is available at:  
[https://api.hacker-news.jsguzman.space](https://api.hacker-news.jsguzman.space)

Swagger documentation is available at:  
[https://api.hacker-news.jsguzman.space/docs/index.html](https://api.hacker-news.jsguzman.space/docs/index.html)

## Contributing
Feel free to open issues and pull requests to improve the project.

## License
This project is licensed under the MIT License.

