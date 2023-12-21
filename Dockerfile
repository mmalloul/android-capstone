FROM node:18.16.0-bullseye-slim AS base

WORKDIR /opt/app

RUN apt-get update && \
    apt-get install -y --no-install-recommends build-essential python dumb-init && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["dumb-init", "--"]

# Build image
FROM base as build 

ENV NODE_ENV dev

COPY ./backend .

RUN npm ci

RUN npm run build

# Tests
FROM base as lint

COPY --from=build /opt/app .

RUN npm run lint

# Prod ready image
FROM base as prod

ENV NODE_ENV production

COPY --from=build /opt/app/package.json .
COPY --from=build /opt/app/package-lock.json .
COPY --from=build /opt/app/build .

RUN npm ci --omit=dev

USER node

CMD ["node", "index.js"]