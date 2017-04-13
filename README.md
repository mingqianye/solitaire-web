# solitaire-web

## Development Mode

### Run application:

```
lein garden auto
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Run in Production (docker)

```
docker-compose build
docker-compose up
```

Open browser and go to http://ip:3000
