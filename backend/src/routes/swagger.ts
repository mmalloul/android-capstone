import express from "express";
import swaggerUi from "swagger-ui-express";
import fs from "fs";

const swaggerDocument = JSON.parse(
  fs.readFileSync("./swagger/swaggerConfig.json", "utf8"),
);

export default (router: express.Router): express.Router => {
  router.use("/api-docs", swaggerUi.serve, swaggerUi.setup(swaggerDocument));
};
