import Project from "../models/Project.js";

// Find all projects
export const findAllProjects = () => Project.findAll();

// Find a project by its ID
export const findProjectById = (id: string) => Project.findByPk(id);

// Find projects by the user ID
export const findProjectsByUserId = (userId: string) =>
  Project.findAll({ where: { author_id: userId } });

// Create a new project
export const createProject = ({ title, description, repositoryUrl, url }) =>
  Project.create({ title, description, repositoryUrl, url });

// Update a project by its ID
export const updateProjectById = (
  id: string,
  { title, description, repositoryUrl, url },
) =>
  Project.update({ title, description, repositoryUrl, url }, { where: { id } });

// Delete a project by its ID
export const deleteProjectById = (id: string) =>
  Project.destroy({ where: { id } });
