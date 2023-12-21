import { Model, DataTypes, CreationOptional, ForeignKey } from "sequelize";
import User from "./User.js";
import sequelize from "../config/database.js";

class Project extends Model {
  declare id: CreationOptional<number>;
  declare author_id: ForeignKey<User["id"]>;
  declare title: string;
  declare description: string;
  declare repository_url: string | null;
  declare url: string | null;
  declare createdAt: CreationOptional<Date>;
  declare updatedAt: CreationOptional<Date>;
}

Project.init(
  {
    id: {
      type: DataTypes.INTEGER.UNSIGNED,
      autoIncrement: true,
      primaryKey: true,
    },
    title: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    description: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    repositoryUrl: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    url: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    createdAt: DataTypes.DATE,
    updatedAt: DataTypes.DATE,
  },
  {
    tableName: "projects",
    sequelize,
  },
);

sequelize.sync();

export default Project;
