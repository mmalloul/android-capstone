import express from "express";

import authenticantion from "./authenticantion.js";
import register from "./register.js";
import refresh from "./refresh.js";
import logout from "./logout.js";
import users from "./users.js";
import projects from "./projects.js";
import swagger from "./swagger.js";
const router = express.Router();

export default (): express.Router => {
  authenticantion(router);
  refresh(router);
  logout(router);
  register(router);

  users(router);

  projects(router);

  swagger(router);
  return router;
};
