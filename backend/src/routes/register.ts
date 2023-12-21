import express from "express";

import { handleRegister } from "../controllers/RegisterController.js";

export default (router: express.Router): express.Router => {
  router.post("/register", handleRegister);
};
