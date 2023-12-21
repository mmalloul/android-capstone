import express from "express";

import {
  getAllUsers,
  deleteUser,
  updateUser,
} from "../controllers/UserController.js";
import { verifyJWT } from "../middlewares/index.js";

export default (router: express.Router): express.Router => {
  router.get("/users", verifyJWT, getAllUsers);
  router.delete("/users/:id", verifyJWT, deleteUser);
  router.put("/users/:id", verifyJWT, updateUser);

  return router;
};
