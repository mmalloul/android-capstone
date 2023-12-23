import express from "express";
import {
  findAllProjects,
  findProjectById,
  findProjectsByUserId,
  createProject,
  updateProjectById,
  deleteProjectById,
} from "../services/ProjectService.js";
import { findUserByRefreshToken } from "../services/UserService.js";

// Get all projects
export const getAllProjects = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const projects = await findAllProjects();
    res.status(200).json(projects);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

// Get a project by ID
export const getProject = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { id } = req.params;
    const project = await findProjectById(id);

    if (!project) {
      return res.status(404).json({ error: "Project not found" });
    }

    res.status(200).json(project);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

// Get projects by user ID
export const getProjectsByUser = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { userId } = req.params;
    const projects = await findProjectsByUserId(userId);

    res.status(200).json({ projects });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

// Create a new project
export const createNewProject = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { refreshToken, title, description, repositoryUrl, url } = req.body;

    // Validation (as an example)
    if (!title || !description) {
      return res
        .status(400)
        .json({ error: "Title and description are required" });
    }

    const user = await findUserByRefreshToken(refreshToken);

    if (!user) {
      return res.status(401).json({ error: "Invalid session token" });
    }

    const newProject = await createProject({
      title,
      description,
      repositoryUrl,
      url,
    });
    res.status(201).json({ project: newProject });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

// Update a project
export const updateProject = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { id } = req.params;
    const { title, description, repositoryUrl, url } = req.body;

    const updatedProject = await updateProjectById(id, {
      title,
      description,
      repositoryUrl,
      url,
    });

    if (!updatedProject) {
      return res.status(404).json({ error: "Project not found" });
    }

    res.status(200).json({ message: "Project updated successfully" });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

// Delete a project
export const deleteProject = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { id } = req.params;
    const deletedProject = await deleteProjectById(id);

    if (!deletedProject) {
      return res.status(404).json({ error: "Project not found" });
    }

    res.status(200).json({ message: "Project deleted successfully" });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
