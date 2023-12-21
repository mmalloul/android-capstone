import User from "../models/User.js";

export const findAllUsers = () => User.findAll();

export const findUserByEmail = (email: string) =>
  User.findOne({ where: { email } });

export const findUserByRefreshToken = (refreshToken: string) =>
  User.findOne({ where: { refreshToken } });

export const findUserById = (id: string) => User.findByPk(id);

export const createUser = (values) => User.create(values);

export const updateUserById = (id: string, values) =>
  User.update(values, { where: { id } });

export const deleteUserById = (id: string) => User.destroy({ where: { id } });
