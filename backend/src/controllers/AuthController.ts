import express from "express";
import dotenv from "dotenv";
import jwt from "jsonwebtoken";
import { login } from "../services/AuthService.js";

dotenv.config();

export const handleLogin = async (
  req: express.Request,
  res: express.Response,
) => {
  try {
    const { email, password } = req.body;
    console.log("Login request:", req.body);
    if (!email || !password) {
      return res.status(400).json({ error: "Email and password are required" });
    }

    const result = await login(email, password);

    console.log("Login result:", result);

    if (!result.success) {
      return res.status(400).json({ error: result.message });
    }

    const { user, accessToken, refreshToken } = result;

    console.log("User:", user);
    res.status(200).json({ user, accessToken, refreshToken });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const validateToken = async (
  req: express.Request,
  res: express.Response,
) => {
  const authHeader = req.headers["authorization"];
  if (!authHeader) {
    return res.status(401).json({ error: "Authorization header is missing" });
  }

  const parts = authHeader.split(" ");
  if (parts.length !== 2 || parts[0] !== "Bearer") {
    return res.status(401).json({ error: "Token format is incorrect" });
  }

  const token = parts[1];
  console.log("Validating token:", token);

  try {
    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, decoded) => {
      console.log("Decoded token:", decoded);

      if (err) {
        return res.status(500).json({ error: "Internal server error" });
      }

      if (!decoded || !decoded.email) {
        return res.status(401).json({ error: "Invalid token structure" });
      }

      return res
        .status(200)
        .json({ message: "Token is valid", user: decoded.email });
    });
  } catch (error) {
    console.error("Token validation error:", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};
