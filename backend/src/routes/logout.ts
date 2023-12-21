import express from "express";

import { handleLogout } from "../controllers/LogoutController.js";

export default (router: express.Router): express.Router => {
  router.post("/auth/logout", handleLogout);
};
