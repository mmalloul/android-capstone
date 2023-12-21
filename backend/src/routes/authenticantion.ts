import express from "express";

import { handleLogin, validateToken } from "../controllers/AuthController.js";

export default (router: express.Router): express.Router => {
  router.post("/auth/login", handleLogin);
  router.get("/auth", validateToken);
};
