import User from "../models/User.js";
import bcrypt from "bcrypt";
import dotenv from "dotenv";
import { createUser } from "../services/UserService.js";
import { generateRefreshToken } from "../lib/authenticate.js";

dotenv.config();

export const register = async (
  name: string,
  email: string,
  password: string,
) => {
  const existingUser = await User.findOne({ where: { email } });

  if (existingUser) {
    return { success: false, message: "User already exists" };
  }

  const hashedPassword = bcrypt.hashSync(password, 10);

  const refreshToken = generateRefreshToken(email);

  const user = await createUser({
    name,
    email,
    password: hashedPassword,
    refreshToken: refreshToken,
  });

  return { success: true, user };
};
