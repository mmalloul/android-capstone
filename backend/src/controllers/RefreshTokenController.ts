import express from "express";
import dotenv from "dotenv";
import jwt from "jsonwebtoken";
import User from "../models/User.js";
import { findUserByRefreshToken } from "../services/UserService.js";
import { ACCESS_TOKEN_EXPIRE_TIME } from "../config/token.js";

dotenv.config();

export const handleRefreshToken = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { refreshToken } = req;

    if (!refreshToken) {
      return res.status(401).json({ error: "Refresh token is missing" });
    }

    const user: User = await findUserByRefreshToken(refreshToken);

    if (!user) {
      return res.status(403).json({ error: "Invalid refresh token" });
    }

    jwt.verify(
      refreshToken,
      process.env.REFRESH_TOKEN_SECRET,
      (err, decoded) => {
        if (err || user.email !== decoded.email) {
          return res.status(403).json({ error: "Token invalid" });
        }

        const accessToken = jwt.sign(
          { email: user.email },
          process.env.ACCESS_TOKEN_SECRET,
          {
            expiresIn: ACCESS_TOKEN_EXPIRE_TIME,
          },
        );

        return res.status(200).json({ accessToken });
      },
    );
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
