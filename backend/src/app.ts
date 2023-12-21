import express from "express";
import compression from "compression";
import cookieParser from "cookie-parser";
import bodyparser from "body-parser";
import cors from "cors";

const app = express();

app.use(cors({ credentials: true }));
app.use(compression());
app.use(cookieParser());
app.use(bodyparser.json());

export default app;
