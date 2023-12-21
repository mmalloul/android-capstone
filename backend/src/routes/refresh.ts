import express from "express";

import { handleRefreshToken } from "../controllers/RefreshTokenController.js";

export default (router: express.Router): express.Router => {
  router.get("/auth/refresh", handleRefreshToken);
};
