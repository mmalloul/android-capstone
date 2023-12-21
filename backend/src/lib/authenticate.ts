import jwt from "jsonwebtoken";
import dotenv from "dotenv";
import {
  ACCESS_TOKEN_EXPIRE_TIME,
  REFRESH_TOKEN_EXPIRE_TIME,
} from "../config/token.js";

dotenv.config();

export function generateRefreshToken(email) {
  const payload = {
    email: email,
  };
  const options = {
    expiresIn: REFRESH_TOKEN_EXPIRE_TIME,
  };
  const token = jwt.sign(payload, process.env.REFRESH_TOKEN_SECRET, options);

  return token;
}

export function generateAccessToken(email) {
  const payload = {
    email: email,
  };
  const options = {
    expiresIn: ACCESS_TOKEN_EXPIRE_TIME,
  };
  const token = jwt.sign(payload, process.env.ACCESS_TOKEN_SECRET, options);

  return token;
}
