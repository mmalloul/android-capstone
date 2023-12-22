import express from "express";

import {
  getAllProjects,
  getProject,
  getProjectsByUser,
  createNewProject,
  updateProject,
  deleteProject,
} from "../controllers/ProjectController.js";
import { verifyJWT } from "../middlewares/index.js";

export default (router: express.Router): express.Router => {
  // Projects routes
  router.get("/projects", getAllProjects);
  router.get("/projects/:id", getProject);
  router.get("/projects/user/:userId", getProjectsByUser);
  router.post("/projects", verifyJWT, createNewProject);
  router.put("/projects/:id", verifyJWT, updateProject);
  router.delete("/projects/:id", verifyJWT, deleteProject);
  return router;
};
