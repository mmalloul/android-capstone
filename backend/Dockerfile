FROM node:18.16.0-bullseye-slim AS base

WORKDIR /opt/app

# Install dumb-init using apt
RUN apt-get update && \
    apt-get install -y --no-install-recommends dumb-init && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["dumb-init", "--"]

# Development only (watch command)
FROM base as development

ENV NODE_ENV dev

CMD [ "npm", "run", "watch:start"]

# Compile Typescript
FROM base as ts-compile

COPY . /opt/app/

RUN npm install

RUN npm run build

# Build stage for production
FROM base as build

COPY --from=ts-compile /opt/app/package*.json /opt/app/

COPY --from=ts-compile /opt/app/dist  /opt/app/dist/

RUN npm install --omit=dev

# Tests
FROM base as lint

COPY --from=ts-compile /opt/app .

RUN npm run lint

# Production image
FROM base as prod

ENV NODE_ENV prod

COPY --from=build /opt/app/dist /opt/app/dist/

COPY --from=build /opt/app/node_modules /opt/app/node_modules/

COPY --from=build /opt/app/package.json /opt/app/

CMD ["node", "dist/index.js"]