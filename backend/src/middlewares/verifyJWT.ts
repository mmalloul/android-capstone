import jwt from "jsonwebtoken";

export const verifyJWT = (req, res, next) => {
  const authHeader = req.headers.authorization;

  // Check if the Authorization header is present
  if (!authHeader) {
    return res.status(401).json({ error: "No token provided" });
  }

  // Extract the token from the Authorization header
  const token = authHeader.split(" ")[1];
  if (!token) {
    return res.status(401).json({ error: "Bearer token not found" });
  }

  // Verify the token
  jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, decoded) => {
    if (err) {
      // Provide more specific error messages based on the JWT error
      if (err instanceof jwt.TokenExpiredError) {
        return res.status(403).json({ error: "Token has expired" });
      } else if (err instanceof jwt.JsonWebTokenError) {
        return res.status(403).json({ error: "Token is invalid" });
      } else {
        // Log the unexpected error for debugging
        console.error("Unexpected JWT verification error:", err);
        return res.status(500).json({ error: "Internal server error" });
      }
    }

    // Attach the decoded user to the request object
    req.user = decoded.user;

    // Call next to pass control to the next middleware function
    next();
  });
};
