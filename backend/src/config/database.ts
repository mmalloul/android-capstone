import { Sequelize } from "sequelize";
import dotenv from "dotenv";
import { Dialect } from "sequelize";

dotenv.config();

const database = process.env.DB_NAME;
const username = process.env.DB_USER;
const password = process.env.DB_PASSWORD;
const host = process.env.DB_HOST;
const dialectString = process.env.DB_DIALECT;
const dialect: Dialect = dialectString as Dialect;

const sequelize = new Sequelize(database, username, password, {
  host,
  dialect,
  logging: false,
});

sequelize
  .authenticate()
  .then(() => {
    console.log("Database connection has been established successfully.");
  })
  .catch((err) => {
    console.error("Unable to connect to the database:", err);
  });

export default sequelize;
