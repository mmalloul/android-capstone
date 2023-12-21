import http from "http";
import dotenv from "dotenv";
import app from "./app.js";
import "./config/database.js";
import router from "./routes/index.js";

dotenv.config();

const server = http.createServer(app);

server.listen(3000, () => {
  console.log("Server is running on port 3000");
  console.log(process.env.APP_URL);
});

app.use("/", router());
