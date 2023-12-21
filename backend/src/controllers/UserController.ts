import express from "express";

import {
  findAllUsers,
  findUserById,
  updateUserById,
  deleteUserById,
} from "../services/UserService.js";

export const getAllUsers = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const users = await findAllUsers();

    return res.status(200).json({ users });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const updateUser = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { id } = req.params;
    const user = await findUserById(id);

    if (!user) {
      return res.status(400).json({ error: "User not found" });
    }

    const { name, email } = req.body;

    const updatedUser = await updateUserById(id, { name, email });

    return res.status(200).json({ user: updatedUser });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const deleteUser = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { id } = req.params;
    const user = await findUserById(id);

    if (!user) {
      return res.status(404).json({ error: "User not found" });
    }

    await deleteUserById(id);

    return res.status(200).json({ user });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
