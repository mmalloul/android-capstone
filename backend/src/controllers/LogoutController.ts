import express from "express";
import dotenv from "dotenv";
import User from "../models/User.js";
import { findUserByRefreshToken } from "../services/UserService.js";

dotenv.config();

export const handleLogout = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { refreshToken } = req.body;

    console.log("Logout refreshToken:", refreshToken);

    if (!refreshToken) {
      return res.status(401).json({ error: "Refresh token is missing" });
    }

    const user: User = await findUserByRefreshToken(refreshToken);

    console.log("Logout user:", user);

    if (!user) {
      res.clearCookie("jwt", { httpOnly: true });
      return res.sendStatus(403);
    }

    user.setDataValue("refreshToken", null);

    await user.save();

    res.sendStatus(204);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
