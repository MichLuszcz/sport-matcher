import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ImageSlideshow from "../ImageSlideshow";

export default function Signup() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    // backend
    navigate("/login");
  };

  return (
    <div className="fullscreen">
      <ImageSlideshow />
      <div className="auth-box">
        <h2>Sign up to Sports Matcher</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button type="submit">Sign up</button>
        </form>
      </div>
    </div>
  );
}
