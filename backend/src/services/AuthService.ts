import bcrypt from "bcrypt";
import dotenv from "dotenv";
import { findUserByEmail } from "../services/UserService.js";
import {
  generateRefreshToken,
  generateAccessToken,
} from "../lib/authenticate.js";

dotenv.config();

export const login = async (email: string, password: string) => {
  const user = await findUserByEmail(email);

  if (!user) {
    return { success: false, message: "Password or e-mail is incorrect" };
  }

  const match = await bcrypt.compare(password, user.getDataValue("password"));

  if (match) {
    const accessToken = generateAccessToken(email);
    const refreshToken = generateRefreshToken(email);

    user.setDataValue("refreshToken", refreshToken);

    await user.save();

    return {
      success: true,
      user,
      accessToken: accessToken,
      refreshToken: refreshToken,
    };
  } else {
    return { success: false, message: "Password or e-mail is incorrect" };
  }
};
