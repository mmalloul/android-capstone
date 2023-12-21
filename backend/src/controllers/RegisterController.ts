import express from "express";
import dotenv from "dotenv";
dotenv.config();

import { register } from "../services/RegisterService.js";

export const handleRegister = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { name, email, password } = req.body;

    if (!email || !password) {
      return res.status(400).json({ error: "Email and password are required" });
    }

    const result = await register(name, email, password);

    if (!result.success) {
      return res.status(400).json({ error: result.message });
    }

    const { user } = result;

    return res.status(201).json({ user });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
